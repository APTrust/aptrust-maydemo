package org.aptrust.client.impl;

import java.util.Properties;

/**
 * An extension of ClientConfig that loads all required and optional properties
 * from a provided Properties object.
 * <ul>
 *   <li>duracloud-url</li>
 *   <li>duracloud-username</li>
 *   <li>duracloud-password</li>
 *   <li>duracloud-providerid</li>
 *   <li>duracloud-providername</li>
 *   <li>solr-url</li>
 * </ul>
 */
public class PropertiesClientConfig extends ClientConfig {

    public PropertiesClientConfig(Properties p) {
        duracloudUrl = getRequiredProperty(p, "duracloud-url");
        duracloudUsername = getRequiredProperty(p, "duracloud-username");
        duracloudPassword = getRequiredProperty(p, "duracloud-password");
        duracloudProviderId = getRequiredProperty(p, "duracloud-providerid");
        duracloudProviderName = getRequiredProperty(p, "duracloud-providername");
        solrUrl = getRequiredProperty(p, "solr-url");
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
