package org.aptrust.common.solr;

import static org.junit.Assert.*;

import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class AptrustSolrDocumentTest {

    @Test
    public void testInvalidDocument() {
        try {
            SolrInputDocument doc
                = AptrustSolrDocument.createValidSolrDocument("just a string, no annotations");
            assertFalse(true);
        } catch (IllegalArgumentException ex) {
            assertTrue(true);
        }
    }
}
