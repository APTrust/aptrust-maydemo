package org.aptrust.client.impl;

import java.util.Properties;

import org.junit.Test;
import junit.framework.Assert;

public class AptrustPropertiesClientConfigTest {

    @Test
    public void testRequireSolrUrl() {
        Properties p = new Properties();
        try {
            PropertiesClientConfig c = new PropertiesClientConfig(p);
            Assert.fail("Missing required properties should result in an exception!");
        } catch (IllegalArgumentException ex) {
            Assert.assertEquals("The required property \"solr-url\" must be set!", ex.getMessage());
        }
        
        p.setProperty("solr-url", "   ");
        try {
            PropertiesClientConfig c = new PropertiesClientConfig(p);
            Assert.fail("Missing required properties should result in an exception!");
        } catch (IllegalArgumentException ex) {
            Assert.assertEquals("The required property \"solr-url\" must be set!", ex.getMessage());
        }
    }

    @Test
    public void testRequireDPNBound() {
        Properties p = new Properties();
        p.setProperty("solr-url", "value");
        p.setProperty("institution_id", "value");
        p.setProperty("record_type", "value");
        p.setProperty("access_control_policy", "value");
        p.setProperty("failed_health_check", "value");
        p.setProperty("operation_status", "value");
        p.setProperty("dpn_bound", "");
        try {
            PropertiesClientConfig c = new PropertiesClientConfig(p);
            Assert.fail("Missing required properties should result in an exception!");
        } catch (IllegalArgumentException ex) {
            Assert.assertEquals("The required property \"dpn_bound\" must be set!", ex.getMessage());
        }
    }
}
