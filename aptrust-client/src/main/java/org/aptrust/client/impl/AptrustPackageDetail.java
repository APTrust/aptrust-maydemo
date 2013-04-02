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
    private String ingestedBy;
    private String modifiedBy;
    private Date modifiedDate;
    
    public AptrustPackageDetail() {
    }

    public AptrustPackageDetail(
        String id, String name, Date ingestDate, int objectCount,
        String institutionName, HealthCheckInfo healthCheckInfo,
        List<ObjectDescriptor> objectDescriptors, String ingestedBy,
        String modifiedBy, Date modifiedDate) {

        super(id,
              name,
              ingestDate,
              objectDescriptors.size(),
              institutionName,
              healthCheckInfo);
        this.objectDescriptors = objectDescriptors;
        this.ingestedBy = ingestedBy;
        this.modifiedBy = modifiedBy;
        this.modifiedDate = modifiedDate;
    }

    public List<ObjectDescriptor> getObjectDescriptors() {
        return objectDescriptors;
    }

    public void setObjectDescriptors(List<ObjectDescriptor> o) {
        objectDescriptors = o;
    }
    
    public void setIngestedBy(String ingestedBy) {
        this.ingestedBy = ingestedBy;
    }
    
    public String getIngestedBy() {
        return ingestedBy;
    }
    
    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }
    
    public String getModifiedBy() {
        return modifiedBy;
    }
    
    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
    
    public Date getModifiedDate() {
        return modifiedDate;
    }
}
