package org.aptrust.client.impl;

import java.util.Properties;

/**
 * An extension of ClientConfig that loads all required and optional properties
 * from a provided Properties object.  The following properties must be 
 * specified:
 * <ul>
 *   <li>solr-url</li>
 *   <li>institution_id</li>
 *    <li>record_type</li>
 *    <li>dpn_bound</li>
 *    <li>access_control_policy</li>
 *    <li>failed_health_check</li>
 *    <li>operation_status</li>
 * </ul>
 */
public class PropertiesClientConfig extends ClientConfig {

    public PropertiesClientConfig(Properties p) {
        solrUrl = getRequiredProperty(p, "solr-url");
        solrFieldConfig = new SolrFieldConfig();
        solrFieldConfig.institutionIdField = getRequiredProperty(p, "institution_id");
        solrFieldConfig.recordTypeField = getRequiredProperty(p, "record_type");
        solrFieldConfig.dpnBoundField = getRequiredProperty(p, "dpn_bound");
        solrFieldConfig.accessControlPolicyField = getRequiredProperty(p, "access_control_policy");
        solrFieldConfig.failedHealthCheckField = getRequiredProperty(p, "failed_health_check");
        solrFieldConfig.operationStatusField = getRequiredProperty(p, "operation_status");
    }

    /**
     * Gets the property value from the Properties object and returns it.  If 
     * no value is set or that value is an empty String this method throws an
     * IllegalArgumentException expressing the requirement that that property
     * be set.
     * @param p the Properties object containing the various key-value pairs
     * @param key the name of the queried property
     * @return the String value of that property (never null or an empty String)
     */
    private String getRequiredProperty(Properties p, String key) {
        String value = p.getProperty(key);
        if (value == null || value.trim().length() == 0) {
            throw new IllegalArgumentException("The required property \"" 
                    + key + "\" must be set!");
        } else {
            return value;
        }
    }

}
