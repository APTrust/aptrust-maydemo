package org.aptrust.ingest.ips;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.aptrust.common.exception.AptrustException;
import org.duracloud.client.ContentStore;
import org.duracloud.client.ContentStoreImpl;
import org.duracloud.common.model.Credential;
import org.duracloud.common.web.RestHttpHelper;
import org.duracloud.error.ContentStoreException;
import org.duracloud.storage.domain.StorageProviderType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yourmediashelf.fedora.client.FedoraClient;
import com.yourmediashelf.fedora.client.FedoraCredentials;

public class IngestProcessingService implements MessageListener, ExceptionListener {

    final Logger logger = LoggerFactory.getLogger(IngestProcessingService.class);

    public static void main(String[] args) throws Exception {
        Properties p = new Properties();
        System.out.println(new File("ingest-client-config.properties").getAbsolutePath());
        p.load(new FileInputStream("ingest-client-config.properties"));
        ContentStore cs = new ContentStoreImpl(
                p.getProperty("duracloud-url"),
                StorageProviderType.valueOf(p.getProperty("duracloud-providername")),
                p.getProperty("duracloud-providerid"), 
                new RestHttpHelper(
                        new Credential(p.getProperty("duracloud-username"), 
                                       p.getProperty("duracloud-password"))));

        SolrServer s = new HttpSolrServer(p.getProperty("solr-url"));
        FedoraClient fc = new FedoraClient(new FedoraCredentials(p.getProperty("fedora-url"), p.getProperty("fedora-username"), p.getProperty("fedora-password")));

        new IngestProcessingService(fc, s, cs, p.getProperty("duracloud-jms-broker-url"));
    }

    /**
     * A map to allows one SpaceLIstener to be assigned to each space.
     * Messages originating from that space will be routed to the appropriate
     * listener.
     */
    private Map<String, SpaceListener> spaceToListenerMap;

    public IngestProcessingService(FedoraClient fc, SolrServer solr, ContentStore cs, String jmsUrl) throws Exception {
        initializeDropboxProcessors(fc, solr, cs);
        
        // Create a ConnectionFactory
        logger.trace("creating connection factory");
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(jmsUrl);

        // Create a Connection
        logger.trace("Creating connection...");
        Connection connection = connectionFactory.createConnection();
        connection.setExceptionListener(this);
        logger.trace("Starting connection...");
        connection.start();
        logger.trace("Started");


        // Create a Session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // Create the destination (Topic or Queue)
        Destination destination = session.createTopic("org.duracloud.topic.change.content.*");

        // Create a MessageConsumer from the Session to the Topic or Queue
        MessageConsumer consumer = session.createConsumer(destination);
        consumer.setMessageListener(this);
    }

    private void initializeDropboxProcessors(FedoraClient fc, SolrServer solr, ContentStore cs) throws Exception {
        spaceToListenerMap = new HashMap<String, SpaceListener>();
        List<String> spaceIds = cs.getSpaces();
        for (String spaceId : spaceIds) {
            String stagingSpaceId = spaceId + "staging";
            if (spaceIds.contains(stagingSpaceId)) {
                spaceToListenerMap.put(stagingSpaceId, new DropboxProcessor(stagingSpaceId, fc, solr, cs));
                logger.info("Registered processor for space \"" + stagingSpaceId + "\".");
            }
        }
        spaceToListenerMap.put("x-service-out", new FixityReportSpaceListener("x-service-out", solr, cs));
    }

    public void onMessage(Message message) {
        if (message instanceof MapMessage) {
            try {
                MapMessage m = (MapMessage) message;
                
                String spaceId = m.getString("spaceId");
                if (spaceToListenerMap.containsKey(spaceId)) {
                    String contentId = m.getString("contentId");
                    String storeId = m.getString("storeId");
                    String destination = m.getJMSDestination().toString();
                    if (destination.endsWith("delete")) {
                        logger.debug("Passing delete message on to process for space \"" + spaceId +  "\". (" + m.getJMSMessageID() + ")");
                        spaceToListenerMap.get(spaceId).notifyDelete(contentId);
                    } else { // ingest or copy
                        logger.debug("Passing update message on to process for space \"" + spaceId +  "\". (" + m.getJMSMessageID() + ")");
                        spaceToListenerMap.get(spaceId).notifyUpdate(contentId);
                    }
                } else {
                    logger.debug("Skipping update to content in space \"" + spaceId +  "\". (" + m.getJMSMessageID() + ")");
                }
            } catch (JMSException ex) {
                // TODO: handle this
                throw new RuntimeException(ex);
            } catch (ContentStoreException ex) {
                // TODO: handle this
                throw new RuntimeException(ex);
            } catch (AptrustException ex) {
                // TODO: handle this
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                // TODO: handle this
                throw new RuntimeException(ex);
            } catch (Throwable t) {
                logger.error("Unexpected error while processing message\"" + message + "\"", t);
            }
        } else {
            logger.warn("Unknown message type " + message.getClass().getName());
        }
    }

    @Override
    public void onException(JMSException ex) {
        logger.error("JMS Exception", ex);
    }
}
