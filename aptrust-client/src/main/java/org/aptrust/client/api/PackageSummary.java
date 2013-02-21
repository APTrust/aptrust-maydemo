package org.aptrust.client.api;

import java.util.Date;

import org.aptrust.common.solr.AptrustSolrDocument;
import org.aptrust.common.solr.SolrField;

public class PackageSummary {
    private String id;
    private String name;
    private Date ingestDate;
    private int objectCount;
    private String institutionName;
    private HealthCheckInfo healthCheckInfo;

    public PackageSummary() {
    }
    
    public PackageSummary(
        String id, String name, Date ingestDate, int objectCount,
        String institutionName, HealthCheckInfo healthCheckInfo) {
        super();
        this.id = id;
        this.name = name;
        this.ingestDate = ingestDate;
        this.objectCount = objectCount;
        this.institutionName = institutionName;
        this.healthCheckInfo = healthCheckInfo;
    }

    public String getId() {
        return id;
    }

    @SolrField(name=AptrustSolrDocument.ID)
    public void setId(String id) {
        this.id = id;
    }
 
    public String getName() {
        return name;
    }

    @SolrField(name=AptrustSolrDocument.TITLE)
    public void setName(String value) {
        name = value;
    }

    public Date getIngestDate() {
        return ingestDate;
    }

    @SolrField(name=AptrustSolrDocument.INGEST_DATE)
    public void setIngestDate(Date date) {
        ingestDate = date;
    }

    public int getObjectCount() {
        return objectCount;
    }

    @SolrField(name=AptrustSolrDocument.OBJECT_COUNT)
    public void setObjectCount(int count) {
        objectCount = count;
    }

    public String getInstitutionName() {
        return institutionName;
    }

    public void setInstitutionName(String name) {
        institutionName = name;
    }

    public HealthCheckInfo getHealthCheckInfo() {
        return healthCheckInfo;
    }

    public void setHealthCheckInfo(HealthCheckInfo hci) {
        healthCheckInfo = hci;
    }
}
