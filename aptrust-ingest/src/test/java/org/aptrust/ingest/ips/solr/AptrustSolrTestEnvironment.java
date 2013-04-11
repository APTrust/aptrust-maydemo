package org.aptrust.ingest.ips.solr;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.core.CoreContainer;
import org.junit.After;

/**
 * Several unit test sets require deployment of a local embedded solr server.
 * This class may be extended to provide that support.
 */
public abstract class AptrustSolrTestEnvironment {

    private File solrHomeDir;

    private SolrServer server;

    public SolrServer getSolrServer() throws Exception {
        if (server == null) {
            // create a solr home directory and copy necessary files
            System.out.println("Creating embedded solr home directory " + solrHomeDir + "...");
            solrHomeDir = new File("target/solr-home");
            solrHomeDir.mkdirs();
    
            System.out.println("Copying configuration files...");
            FileUtils.copyDirectory(new File("src/test/solr/embedded-solr-home"), solrHomeDir);
    
            // create an embedded Solr Server
            System.out.println("Starting the embedded Solr Server...");
            System.setProperty("solr.solr.home", solrHomeDir.getAbsolutePath());
            CoreContainer.Initializer initializer = new CoreContainer.Initializer();
            CoreContainer coreContainer = initializer.initialize();
            server = new EmbeddedSolrServer(coreContainer, "");
        }
        return server;
    }

    public void removeSolrServer() throws Exception {
        if (server != null && solrHomeDir != null) {
            System.out.println("Deleting embedded solr home directory " + solrHomeDir.getAbsolutePath() + ".");
            FileUtils.deleteDirectory(solrHomeDir);
        }
    }

    @After
    public void tearDown() throws Exception {
        removeSolrServer();
    }
    
}
