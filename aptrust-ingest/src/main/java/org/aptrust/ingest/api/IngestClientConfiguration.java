package org.aptrust.ingest.api;

/**
 *  An interface that defines methods for everything that may be configured 
 *  for an installation of the Ingest Client.  Other parameters are required
 *  for the IngestClient, but this configuration contains values that shouldn't
 *  change between runs and may need to be obfuscated or encrypted.
 *
 */
public interface IngestClientConfiguration {

    public String getDuraCloudUrl();
    
    public String getDuraCloudUsername();
    
    public String getDuraCloudPassword();
    
    public String getDuraCloudSpaceId();
    
    public String getDuraCloudProviderId();
    
    public String getDuraCloudProviderName();
    
    public String getCloudSyncURL();
    
    public String getCloudSyncUsername();
    
    public String getCloudSyncPassword();
    
    public String getOutputDir();
    
}
