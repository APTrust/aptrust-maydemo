package org.aptrust.ingest.ips.solr;

import java.util.Date;

import org.aptrust.common.solr.AptrustSolrDocument;
import org.aptrust.common.solr.SolrField;
import org.aptrust.ingest.api.IngestPackage;

/**
 * A simple class that wraps an IngestPackage and exposes annotated methods
 * that allow for easy creation of Solr Documents for "package" records.
 *
 * @see org.aptrust.common.solr.AptrustSolrDocument#createValidSolrDocument(Object)
 */
public class PackageSolrDocument {

    private IngestPackage p;

    private Date ingestDate;

    public PackageSolrDocument(IngestPackage p) {
        this.p = p;
        ingestDate = new Date();
    }

    @SolrField(name=AptrustSolrDocument.RECORD_TYPE)
    public String getRecordType() {
        return "package";
    }

    @SolrField(name=AptrustSolrDocument.INSTITUTION_ID)
    public String getInstitutionId() {
        return p.getMetadata().getInstitution();
    }

    @SolrField(name=AptrustSolrDocument.ID)
    public String getId() {
        return p.getMetadata().getId();
    }

    @SolrField(name=AptrustSolrDocument.DPN_BOUND)
    public boolean isDPNBound() {
        return p.getMetadata().isDPNBound();
    }

    @SolrField(name=AptrustSolrDocument.ACCESS_CONTROL_POLICY)
    public String getAccessControlPolicy() {
        return p.getMetadata().getAccessConditions();
    }

    @SolrField(name=AptrustSolrDocument.OBJECT_COUNT)
    public int getObjectCount() {
        return p.getDigitalObjects().length;
    }

    @SolrField(name=AptrustSolrDocument.TITLE)
    public String getTitle() {
        return p.getMetadata().getTitle();
    }

    @SolrField(name=AptrustSolrDocument.INGEST_DATE)
    public Date getIngestDate() {
        return ingestDate;
    }
}
