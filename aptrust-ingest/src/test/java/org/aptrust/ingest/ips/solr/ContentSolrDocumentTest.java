package org.aptrust.ingest.ips.solr;

import java.util.Date;

import junit.framework.Assert;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.aptrust.common.solr.AptrustSolrDocument;
import org.junit.Before;
import org.junit.Test;

public class ContentSolrDocumentTest extends AptrustSolrTestEnvironment {

    SolrServer server;

    @Before
    public void setUp() throws Exception {
        server = getSolrServer();

    }

    @Test
    public void testQueries() throws Exception {
        ContentSolrDocument d  = new ContentSolrDocument("1", "2", "3", "4", true, new Date());
        server.add(AptrustSolrDocument.createValidSolrDocument(d));
        server.commit();
        
        ModifiableSolrParams params = new ModifiableSolrParams();
        params.set("q", AptrustSolrDocument.RECORD_TYPE + ":" + d.getRecordType());
        SolrDocumentList results = server.query(params).getResults();
        Assert.assertEquals("The " + d.getRecordType() + " record should be found in a search for such records (found " + results.getNumFound() + ").", (long) 1, results.getNumFound());

        params.set("q", AptrustSolrDocument.PACKAGE_ID + ":\"" + d.getPackageId() + "\"");
        results = server.query(params).getResults();
        Assert.assertEquals("The " + d.getRecordType() + " record should be found in a search for all such records with a given packageId. (found " + results.getNumFound() + ").", (long) 1, results.getNumFound());
    }

    @Test
    public void testRoundtripThroughSolr() throws Exception {
        ContentSolrDocument d  = new ContentSolrDocument("1", "2", "3", "4", true, new Date());
        server.add(AptrustSolrDocument.createValidSolrDocument(d));
        server.commit();
        
        ModifiableSolrParams params = new ModifiableSolrParams();
        params.set("q", AptrustSolrDocument.RECORD_TYPE + ":" + d.getRecordType());
        SolrDocumentList results = server.query(params).getResults();
        ContentSolrDocument result = new ContentSolrDocument();
        AptrustSolrDocument.populateFromSolrDocument(result, results.get(0));

        Assert.assertEquals("Institution Id must be preserved through Solr roundtrips.", d.getInstitutionId(), result.getInstitutionId());
        Assert.assertEquals("Package Id must be preserved through Solr roundtrips.", d.getPackageId(), result.getPackageId());
        Assert.assertEquals("Object Id must be preserved through Solr roundtrips.", d.getObjectId(), result.getObjectId());
        Assert.assertEquals("Content Id must be preserved through Solr roundtrips.", d.getContentId(), result.getContentId());
        Assert.assertEquals("Health check status must be preserved through Solr roundtrips.", d.getFailedHealthCheck(), result.getFailedHealthCheck());
        Assert.assertEquals("Health check date must be preserved through Solr roundtrips.", d.getHealthCheckDate(), result.getHealthCheckDate());

        
    }
}
