package org.aptrust.ingest.ips;

import java.util.Map;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class IngestProcessingService implements MessageListener {

    public static void main(String[] args) throws JMSException {
        // TODO: connect to the Message Queue, create some 
        //       DropboxProcessor objects and start listening
    }

    private Map<String, DropboxProcessor> institutionToDropboxProcessorMap;

    public void onMessage(Message m) {
        // 1.  determine which space was updated and if it was a staging space
        // 2.  queue notification for the relevant DropboxProcessor
    }
}
