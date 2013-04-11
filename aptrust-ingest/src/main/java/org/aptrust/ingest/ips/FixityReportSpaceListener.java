package org.aptrust.ingest.ips;

import java.util.Iterator;
import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.aptrust.common.solr.AptrustSolrDocument;
import org.aptrust.ingest.ips.FixityReport.FixityCheck;
import org.aptrust.ingest.ips.solr.ContentSolrDocument;
import org.duracloud.client.ContentStore;
import org.duracloud.error.ContentStoreException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FixityReportSpaceListener implements SpaceListener {

    final Logger logger = LoggerFactory.getLogger(DropboxProcessor.class);

    private SolrServer solr;

    private ContentStore cs;

    private String reportingSpaceId;
    
    public FixityReportSpaceListener(String spaceId, SolrServer solr, ContentStore cs) throws Exception {
        this.solr = solr;
        this.cs = cs;
        reportingSpaceId = spaceId;

        // walk through all present content
        Iterator<String> contentIdIt = cs.getSpaceContents(spaceId);
        while (contentIdIt.hasNext()) {
           notifyUpdate(contentIdIt.next());
        }
    }

    public void notifyUpdate(String reportId) throws Exception {
        try {
            if (reportId.startsWith("bit-integrity/fixity-report")) {
                String spaceId = FixityReport.getSpaceIdFromReportContentId(reportId);
                if (spaceId != null && isInstitutionId(spaceId, cs)) {
                    FixityReport r = new FixityReport(reportId, cs.getContent(reportingSpaceId, reportId).getStream());
                    for (String cId : r.getContentIds()) {
                        try {
                            FixityCheck c = r.getFixityCheck(cId);
                            ModifiableSolrParams params = new ModifiableSolrParams();
                            String id = ContentSolrDocument.getId(r.getSpaceId(), cId);
                            params.set("q", AptrustSolrDocument.ID + ":\"" + id + "\"");
                            SolrDocumentList results = solr.query(params).getResults();
                            if (results.getNumFound() == 0) {
                                logger.info("Skipping fixity report for un-registered content: " + cId);
                            } else if (results.getNumFound() > 1) {
                                logger.error("Solr returned multiple records for a search for the unique id: " + id);
                            } else {
                                ContentSolrDocument updatedRecord = new ContentSolrDocument();
                                AptrustSolrDocument.populateFromSolrDocument(updatedRecord, results.get(0));
                                updatedRecord.setFailedHealthCheck(String.valueOf(!c.passed));
                                updatedRecord.setHealthCheckDate(r.getReportDate());
                                solr.add(AptrustSolrDocument.createValidSolrDocument(updatedRecord));
                                logger.debug("Added health check report for " + cId + ". (part of object " + updatedRecord.getObjectId() + " and package " + updatedRecord.getPackageId() + ")");
                            }
                        } catch (Throwable t) {
                            logger.warn("Error processing fixity check for " + cId + " in space " + r.getSpaceId() + "!", t);
                        }
                    }
                } else {
                    logger.debug("Skipping " + reportId + ": " + spaceId + " is not a known institution.");
                }
            } else {
                logger.trace("Skipping " + reportId + ": not a fixity report");
            }
        } finally {
            solr.commit();
        }
    }

    /**
     * Does nothing, deleting a report has no effect.
     */
    public void notifyDelete(String contentId) throws Exception {
        // do nothing
    }

    private boolean isInstitutionId(String id, ContentStore cs) throws ContentStoreException {
        List<String> spaceIds = cs.getSpaces();
        return spaceIds.contains(id) && spaceIds.contains(id + "staging");
    }

}
