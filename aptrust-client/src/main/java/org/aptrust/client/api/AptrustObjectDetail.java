package org.aptrust.client.api;

import org.aptrust.common.solr.AptrustSolrDocument;
import org.aptrust.common.solr.SolrField;

/**
 * 
 * @author Daniel Bernstein
 *
 */
public class AptrustObjectDetail {
    private String objectId;

    public AptrustObjectDetail() {
    }

    public AptrustObjectDetail(String objectId) {
        this.objectId = objectId;
    }

    public String getObjectId() {
        return objectId;
    }

    @SolrField(name=AptrustSolrDocument.ID)
    public void setObjectId(String id) {
        objectId = id;
    }

    public String getTitle(){
        return getObjectId();
    }
}
