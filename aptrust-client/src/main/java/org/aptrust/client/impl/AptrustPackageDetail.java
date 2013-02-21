package org.aptrust.client.impl;

import java.util.Date;
import java.util.List;

import org.aptrust.client.api.HealthCheckInfo;
import org.aptrust.client.api.PackageSummary;
/**
 * 
 * @author Daniel Bernstein
 *
 */
public class AptrustPackageDetail extends PackageSummary{
    private  List<ObjectDescriptor> objectDescriptors;
    
    public AptrustPackageDetail() {
    }

    public AptrustPackageDetail(
        String id, String name, Date ingestDate, int objectCount,
        String institutionName, HealthCheckInfo healthCheckInfo, List<ObjectDescriptor> objectDescriptors) {
        super(id, name, ingestDate, objectDescriptors.size(), institutionName, healthCheckInfo);
        this.objectDescriptors = objectDescriptors;
    }

    public List<ObjectDescriptor> getObjectDescriptors() {
        return objectDescriptors;
    }

    public void setObjectDescriptors(List<ObjectDescriptor> o) {
        objectDescriptors = o;
    }
}
