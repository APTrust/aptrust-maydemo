package org.aptrust.ingest.ips;

import java.util.Date;

import org.aptrust.client.api.IngestStatus;
import org.aptrust.common.solr.AptrustSolrDocument;
import org.aptrust.common.solr.SolrField;
import org.aptrust.ingest.api.IngestManifest;

/**
 * A simple class that wraps an IngestManfiest and other member variables and
 * exposes annotated methods that allow for easy creation of Solr Documents for
 * "ingest" operations.
 * 
 * @see org.aptrust.common.solr.AptrustSolrDocument#createValidIngestDocument(Object)
 */
public class IngestSolrDocument {

    private String institutionId;

    private IngestManifest m;

    private IngestStatus operationStatus;

    private int progress;

    private Date endDate;

    private String errorMessage;

    private IngestSolrDocument() {
    }

    /**
     * Gets a IngestSolrDocument that describes a brand new ingest operation
     * for the given institution with the given manifest.
     */
    public static IngestSolrDocument newIngest(String institutionId, IngestManifest m) {
        IngestSolrDocument d = new IngestSolrDocument();
        d.institutionId = institutionId;
        d.m = m;
        d.progress = 0;
        d.operationStatus = IngestStatus.IN_PROGRESS;
        return d;
    }

    /**
     * Gets a IngestSolrDocument that describes an in-progress ingest operation
     * for the given institution with the given manifest.
     */
    public static IngestSolrDocument updateIngest(String institutionId, IngestManifest m, int progress) {
        IngestSolrDocument d = new IngestSolrDocument();
        d.institutionId = institutionId;
        d.m = m;
        d.progress = progress;
        d.operationStatus = IngestStatus.IN_PROGRESS;
        return d;
    }

    /**
     * Gets a IngestSolrDocument that describes a failed ingest operation for
     * the given institution with the given manifest and error message.
     */
    public static IngestSolrDocument failedIngest(String institutionId, IngestManifest m, String errorMessage) {
        IngestSolrDocument d = new IngestSolrDocument();
        d.institutionId = institutionId;
        d.m = m;
        d.operationStatus = IngestStatus.FAILED;
        d.errorMessage = errorMessage;
        return d;
    }

    /**
     * Gets a IngestSolrDocument that describes a completed ingest operation
     * for the given institution.
     * @param completionDate the date when the operation was completed
     */
    public static IngestSolrDocument completedIngest(String institutionId, IngestManifest m, Date completionDate) {
        IngestSolrDocument d = new IngestSolrDocument();
        d.institutionId = institutionId;
        d.m = m;
        d.operationStatus = IngestStatus.COMPLETED;
        d.endDate = completionDate;
        return d;
    }

    @SolrField(name=AptrustSolrDocument.ID)
    public String getId() {
        return m.getId();
    }

    @SolrField(name=AptrustSolrDocument.RECORD_TYPE)
    public String getRecordType() {
        return "ingest";
    }

    @SolrField(name=AptrustSolrDocument.INSTITUTION_ID)
    public String getInstitutionId() {
        return institutionId;
    }

    @SolrField(name=AptrustSolrDocument.TITLE)
    public String getTitle() {
        return m.getDescription().getName();
    }

    @SolrField(name=AptrustSolrDocument.SUBMITTING_USER)
    public String getUser() {
        return m.getDescription().getSuppliedUsername();
    }

    @SolrField(name=AptrustSolrDocument.OPERATION_STATUS)
    public String getStatus() {
        return operationStatus.name();
    }

    @SolrField(name=AptrustSolrDocument.PROGRESS)
    public int getProgress() {
        return progress;
    }
 
    @SolrField(name=AptrustSolrDocument.OPERATION_START_DATE)
    public Date getStartDate() {
        return m.getDescription().getIngestInitiated();
    }

    @SolrField(name=AptrustSolrDocument.OPERATION_END_DATE)
    public Date getEndDate() {
        return endDate;
    }

    @SolrField(name=AptrustSolrDocument.MESSAGE)
    public String getErrorMessage() {
        return errorMessage;
    }
}
