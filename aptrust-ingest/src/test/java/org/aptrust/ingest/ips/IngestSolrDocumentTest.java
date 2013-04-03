package org.aptrust.ingest.ips;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import junit.framework.Assert;

import org.apache.commons.io.FileUtils;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.apache.solr.core.CoreContainer;
import org.aptrust.common.solr.AptrustSolrDocument;
import org.aptrust.ingest.api.DigitalObject;
import org.aptrust.ingest.api.IngestManifest;
import org.aptrust.ingest.api.IngestPackage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class IngestSolrDocumentTest {

    private File solrHomeDir;
    
    private SolrServer server;

    @Before
    public void setUp() throws Exception {
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

    @After
    public void tearDown() throws Exception {
        System.out.println("Deleting embedded solr home directory " + solrHomeDir.getAbsolutePath() + ".");
        FileUtils.deleteDirectory(solrHomeDir);
    }

    @Test
    public void testValidRecordIngest() throws Exception {
        JAXBContext jc = JAXBContext.newInstance(IngestManifest.class);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        IngestManifest m = (IngestManifest) unmarshaller.unmarshal(this.getClass().getClassLoader().getResourceAsStream("manifest-fedora-sample.xml"));

        m.setId("test:1");

        IngestSolrDocument ingest = IngestSolrDocument.newIngest("test", m);
        SolrInputDocument doc = AptrustSolrDocument.createValidSolrDocument(ingest);

        server.add(doc);
        server.commit();

        for (IngestPackage p : m.getPackagesToSubmit()) {
            PackageSolrDocument pDoc = new PackageSolrDocument(p);
            server.add(AptrustSolrDocument.createValidSolrDocument(pDoc));
        }
        server.commit();

        ModifiableSolrParams params = new ModifiableSolrParams();
        params.set("q", AptrustSolrDocument.RECORD_TYPE + ":ingest");
        SolrDocumentList results = server.query(params).getResults();
        Assert.assertEquals("There must be exactly one ingest record in solr (found " + results.getNumFound() + ").", (long) 1, results.getNumFound());
        
        params.set("q", AptrustSolrDocument.INCLUDED_PID + ":\"uva-lib:602138\"");
        results = server.query(params).getResults();
        assertEquals("Expected to find a single record for uva-lib:602138.", (long) 1, results.getNumFound());
        
        params.set("q", AptrustSolrDocument.RECORD_TYPE + ":package");
        results = server.query(params).getResults();
        assertEquals("Expected to find 3 package records but found " + results.getNumFound() + ".", (long) 3, results.getNumFound());
        
        params.set("q", AptrustSolrDocument.INCLUDED_PID + ":\"test:fake\"");
        results = server.query(params).getResults();
        assertEquals("Expected not to find fake record.", (long) 0, results.getNumFound());
        
        params.set("q", "Novvelles");
        results = server.query(params).getResults();
        assertEquals("Expected to find one result for the full-text search for \"Novvelles\" but found " + results.getNumFound()  + ".", (long) 1, results.getNumFound());
    }
}
