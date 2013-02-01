package org.aptrust.ingest.impl;

import java.util.Properties;

import org.aptrust.ingest.api.IngestClientConfiguration;

/**
 * An implementation of IngestClientConfiguration that gets its values from 
 * well-named properties in a Properties object.
 */
public class PropertiesIngestClientConfiguration implements IngestClientConfiguration {

    private Properties p;
    
    public PropertiesIngestClientConfiguration(Properties properties) {
        p = properties;
    }

    public String getDuraCloudUrl() {
        return p.getProperty("duracloud-url");
    }

    public String getDuraCloudUsername() {
        return p.getProperty("duracloud-username");
    }

    public String getDuraCloudPassword() {
        return p.getProperty("duracloud-password");
    }

    public String getDuraCloudSpaceId() {
        return p.getProperty("duracloud-spaceid");
    }

    public String getDuraCloudProviderId() {
        return p.getProperty("duracloud-providerid");
    }

    public String getDuraCloudProviderName() {
        return p.getProperty("duracloud-providername");
    }

    public String getCloudSyncURL() {
        return p.getProperty("cloudsync-url");
    }

    public String getCloudSyncUsername() {
        return p.getProperty("cloudsync-username");
    }

    public String getCloudSyncPassword() {
        return p.getProperty("cloudsync-password");
    }

    public String getOutputDir() {
        return p.getProperty("output-dir");
    }
}
