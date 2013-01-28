package org.aptrust.client.api;

import java.util.Date;

public class PackageSummary {
    private String id;
    private String name;
    private Date ingestDate;
    private int objectCount;
    private String institutionName;
    private HealthCheckInfo healthCheckInfo;

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

    public String getName() {
        return name;
    }

    public Date getIngestDate() {
        return ingestDate;
    }

    public int getObjectCount() {
        return objectCount;
    }

    public String getInstitutionName() {
        return institutionName;
    }

    public HealthCheckInfo getHealthCheckInfo() {
        return healthCheckInfo;
    }
}
