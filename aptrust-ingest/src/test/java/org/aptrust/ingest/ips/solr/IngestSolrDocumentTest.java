package org.aptrust.ingest.ips.solr;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import junit.framework.Assert;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.aptrust.client.impl.SolrQueryClause;
import org.aptrust.common.solr.AptrustSolrDocument;
import org.aptrust.common.solr.ContentSolrDocument;
import org.aptrust.ingest.api.DigitalObject;
import org.aptrust.ingest.api.IngestManifest;
import org.aptrust.ingest.api.IngestPackage;
import org.aptrust.ingest.ips.FixityReport;
import org.junit.Before;
import org.junit.Test;

public class IngestSolrDocumentTest extends AptrustSolrTestEnvironment {

    private SolrServer server;

    @Before
    public void setUp() throws Exception {
        server = getSolrServer();

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
            server.commit();
        }

        InputStream is = getClass().getClassLoader().getResourceAsStream("fixity-report-sample.tsv");
        String contentId = "bit-integrity/fixity-report-fingerprints-uva-2013-02-09T02:51-vs-manifest-2013-02-09T02:52.tsv";
        FixityReport r = new FixityReport(contentId, is);
        for (String cId : r.getContentIds()) {
            Pattern p = Pattern.compile("([^\\+]+)\\+.*");
            Matcher matcher = p.matcher(cId);
            if (matcher.matches()) {
                String objectId = matcher.group(1);
                for (IngestPackage pac : m.getPackagesToSubmit()) {
                    for (DigitalObject o : pac.getDigitalObjects()) {
                        if (o.getId().equals(objectId)) {
                            ContentSolrDocument file = new ContentSolrDocument(r.getSpaceId(), pac.getMetadata().getId(), "uva-lib:602179", cId, r.getFixityCheck(cId).passed, r.getReportDate());
                            doc = AptrustSolrDocument.createValidSolrDocument(file);
                            server.add(doc);
                            server.commit();
                        }
                    }
                }
            }
        }
    }

    @Test
    public void testManifestQueries() throws Exception {
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

    @Test
    public void testHealthCheckQueries() throws Exception {
        ModifiableSolrParams params = new ModifiableSolrParams();
        params.set("q", AptrustSolrDocument.RECORD_TYPE + ":package");
        SolrDocumentList results = server.query(params).getResults();
        
        SolrQueryClause fileRecords = new SolrQueryClause(AptrustSolrDocument.RECORD_TYPE, "file");
        SolrQueryClause currentInstitution = new SolrQueryClause(AptrustSolrDocument.INSTITUTION_ID, "uva");
        SolrQueryClause fromPackage = new SolrQueryClause(AptrustSolrDocument.PACKAGE_ID, (String) results.get(0).getFirstValue(AptrustSolrDocument.ID));

        SolrQueryClause query = fileRecords.and(currentInstitution).and(fromPackage);

        params = new ModifiableSolrParams();
        params.set("q", query.toString());
        params.set("facet", "true");
        params.set("facet.field", AptrustSolrDocument.LAST_HEALTH_CHECK_DATE, AptrustSolrDocument.FAILED_HEALTH_CHECK);

        QueryResponse response = server.query(params);
        Assert.assertEquals("Check to ensure there is exactly one health check date.", 1, response.getFacetField(AptrustSolrDocument.LAST_HEALTH_CHECK_DATE).getValueCount());
        Assert.assertEquals("Check to ensure there are no failures.", response.getFacetField(AptrustSolrDocument.FAILED_HEALTH_CHECK).getValueCount() == 1 && response.getFacetField(AptrustSolrDocument.FAILED_HEALTH_CHECK).getValues().get(0).getName().equals("false"), true);

    }
}
