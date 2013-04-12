package org.aptrust.client.api;

import java.util.List;

import org.aptrust.common.solr.AptrustSolrDocument;
import org.aptrust.common.solr.SolrField;

/**
 * 
 * @author Daniel Bernstein
 *
 */
public class AptrustObjectDetail {
    private String objectId;
    private List<ContentSummary> content;
    
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

    public String getLocalId() {
        return objectId.substring(objectId.indexOf('-') + 1);
    }

    public String getTitle(){
        return getObjectId();
    }

    public List<ContentSummary> getContentSummaries() {
        return content;
    }

    public void setContentSummaries(List<ContentSummary> content) {
        this.content = content;
    }
    
}
