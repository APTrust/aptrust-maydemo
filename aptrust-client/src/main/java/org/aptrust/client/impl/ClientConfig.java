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

    String solrUrl;

    SolrFieldConfig solrFieldConfig;

    public String getSolrUrl() {
        return solrUrl;
    }

    public void setSolrUrl(String value) {
        solrUrl = value;
    }

    public SolrFieldConfig getSolrFieldConfig() {
        return solrFieldConfig;
    }

    public void setSolrFieldConfig(SolrFieldConfig solrFieldConfig) {
        this.solrFieldConfig = solrFieldConfig;
    }

}
