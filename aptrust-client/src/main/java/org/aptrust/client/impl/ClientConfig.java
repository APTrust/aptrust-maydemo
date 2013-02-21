package org.aptrust.client.impl;

/**
 * This class holds configuration information such external resource locators
 * and static credentials for accessing them.
 * 
 * @author Daniel Bernstein
 * @created Dec 11, 2012
 * 
 */
public class ClientConfig {

    String duracloudUrl;

    String duracloudUsername;

    String duracloudPassword;

    String duracloudProviderId;

    String duracloudProviderName;

    String solrUrl;

    public String getDuracloudUrl() {
        return duracloudUrl;
    }

    public String getDuracloudUsername() {
        return duracloudUsername;
    }

    public String getDuracloudPassword() {
        return duracloudPassword;
    }

    public String getDuraCloudProviderId() {
        return duracloudProviderId;
    }

    public String getDuraCloudProviderName() {
        return duracloudProviderName;
    }

    public String getSolrUrl() {
        return solrUrl;
    }

    public void setSolrUrl(String value) {
        solrUrl = value;
    }
}
