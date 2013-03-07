package org.aptrust.client.api;

import java.util.Date;

import org.aptrust.common.solr.AptrustSolrDocument;
import org.aptrust.common.solr.SolrField;

/**
 * 
 * @author Daniel Bernstein Date: Jan 2, 2013
 * 
 */
public class IngestProcessSummary {

    private String institutionId;
    private String name;
    private String initiatingUser;
    private IngestStatus status;
    private int totalObjectCount;
    private int ingestedObjectCount;
    private Date startDate;
    private Date endDate;
    private String message;

    public IngestProcessSummary(
            String institutionId, String name, String initiatingUser,
            IngestStatus status, int ingestedObjectCount, int totalObjectCount, Date startDate, Date endDate, String message) {
        super();
        this.institutionId = institutionId;
        this.name = name;
        this.initiatingUser = initiatingUser;
        this.status = status;
        this.ingestedObjectCount = ingestedObjectCount;
        this.totalObjectCount = totalObjectCount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.message = message;
    }

    public IngestProcessSummary() {
    }

    public String getInstitutionId() {
        return institutionId;
    }

    @SolrField(name = AptrustSolrDocument.INSTITUTION_ID)
    public void setInstitutionId(String id) {
        institutionId = id;
    }

    public String getName() {
        return name;
    }

    @SolrField(name=AptrustSolrDocument.TITLE)
    public void setName(String name) {
        this.name = name;
    }

    public String getInitiatingUser() {
        return initiatingUser;
    }

    @SolrField(name=AptrustSolrDocument.SUBMITTING_USER)
    public void setInitiatingUser(String username) {
        initiatingUser = username;
    }

    public IngestStatus getStatus() {
        return status;
    }

    public void setStatus(IngestStatus s) {
        status = s;
    }

    @SolrField(name=AptrustSolrDocument.OPERATION_STATUS)
    public void setStatus(String s) {
        status = IngestStatus.valueOf(s);
    }

    public int getTotalObjectCount() {
        return totalObjectCount;
    }

    @SolrField(name=AptrustSolrDocument.OBJECT_COUNT)
    public void setTotalObjectCount(int count) {
        totalObjectCount = count;
    }

    public int getIngestedObjectCount() {
        return ingestedObjectCount;
    }

    @SolrField(name=AptrustSolrDocument.COMPLETED_OBJECT_COUNT)
    public void setIngestedObjectCount(int count) {
        ingestedObjectCount = count;
    }

    public int getProgress() {
        return Math.round((100 * (float) ingestedObjectCount) / (float) totalObjectCount);
    }

    public Date getStartDate() {
        return startDate;
    }

    @SolrField(name=AptrustSolrDocument.OPERATION_START_DATE)
    public void setStartDate(Date date) {
        startDate = date;
    }

    public Date getEndDate() {
        return endDate;
    }

    @SolrField(name=AptrustSolrDocument.OPERATION_END_DATE)
    public void setEndDate(Date date) {
        endDate = date;
    }

    public String getMessage() {
        return message;
    }

    @SolrField(name=AptrustSolrDocument.MESSAGE)
    public void setMessage(String message) {
        this.message = message;
    }
}
