package org.aptrust.client.api;

/**
 * 
 * @author Daniel Bernstein
 *
 */
public class AptrustObjectDetail {
    private String objectId;

    public AptrustObjectDetail(String objectId) {
        this.objectId = objectId;
    }
    
    public String getObjectId() {
        return objectId;
    }
    
    public String getTitle(){
        return getObjectId();
    }
}
