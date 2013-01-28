package org.aptrust.client.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.aptrust.client.api.AptrustClient;
import org.aptrust.client.api.AptrustObjectDetail;
import org.aptrust.client.api.IngestProcessSummary;
import org.aptrust.client.api.IngestStatus;
import org.aptrust.client.api.InstitutionInfo;
import org.aptrust.client.api.PackageSummaryQueryResponse;
import org.aptrust.client.api.SearchParams;
import org.aptrust.client.api.Summary;
import org.aptrust.common.exception.AptrustException;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * An AptrustClient implementation that gets all available information from Solr
 * queries.
 */
public class AptrustClientImpl implements AptrustClient {

    private ClientConfig config;

    private SolrServer solr;

    public AptrustClientImpl(ClientConfig config) {
        if (config == null) {
            throw new IllegalArgumentException("client config must be non-null");
        }

        this.config = config;
        solr = new HttpSolrServer(config.getSolrUrl());
    }

    @Override
    public Summary getSummary(String institutionId) throws AptrustException {
        Summary s = new Summary();
        s.setInstitutionId(institutionId);

        SolrQueryClause packageRecords =
            new SolrQueryClause(config.getSolrFieldConfig().recordTypeField,
                                "package");
        SolrQueryClause objectRecords =
            new SolrQueryClause(config.getSolrFieldConfig()
                                      .getRecordTypeField(), "object");
        SolrQueryClause currentInstitution =
            new SolrQueryClause(config.getSolrFieldConfig()
                                      .getInstitutionIdField(), institutionId);
        SolrQueryClause dpnBound =
            new SolrQueryClause(config.getSolrFieldConfig().getDpnBoundField(),
                                "true");
        SolrQueryClause isPublic =
            new SolrQueryClause(config.getSolrFieldConfig()
                                      .getAccessControlPolicyField(), "world");
        SolrQueryClause isPrivate =
            new SolrQueryClause(config.getSolrFieldConfig()
                                      .getAccessControlPolicyField(), "private");
        SolrQueryClause isInstitutionOnly =
            new SolrQueryClause(config.getSolrFieldConfig()
                                      .getAccessControlPolicyField(),
                                "institution");
        SolrQueryClause failedHealthCheck =
            new SolrQueryClause(config.getSolrFieldConfig().failedHealthCheckField,
                                "true");

        try {
            s.setPackageCount(getResponseCount(packageRecords.and(currentInstitution)
                                                             .getQueryString()));
            s.setObjectCount(getResponseCount(objectRecords.and(currentInstitution)
                                                           .getQueryString()));
            s.setDpnBoundPackageCount(getResponseCount(packageRecords.and(dpnBound)
                                                                     .and(currentInstitution)
                                                                     .getQueryString()));
            s.setPublicPackageCount(getResponseCount(packageRecords.and(isPublic)
                                                                   .and(currentInstitution)
                                                                   .getQueryString()));
            s.setInstitutionPackageCount(getResponseCount(packageRecords.and(isInstitutionOnly)
                                                                        .and(currentInstitution)
                                                                        .getQueryString()));
            s.setPrivatePackageCount(getResponseCount(packageRecords.and(isPrivate)
                                                                    .and(currentInstitution)
                                                                    .getQueryString()));
            s.setFailedPackageCount(getResponseCount(packageRecords.and(failedHealthCheck)
                                                                   .and(currentInstitution)
                                                                   .getQueryString()));
        } catch (SolrServerException ex) {
            throw new AptrustException("Error generating summary from Solr!",
                                       ex);
        }

        // TODO: get the size used from the DuraCloud API and include it in the
        // summary

        return s;
    }

    /**
     * Gets identifiers for all institutions for which any data is present in
     * the solr index. This implementation may not result in a comprehensive
     * list of institutions in cases where no content has been ingested. TODO:
     * possibly fetch this information from another source
     */
    public Collection<String> getInstitutionIds() throws AptrustException {
        int count = 0;
        List<String> institutionIds = new ArrayList<String>();
        do {
            try {
                QueryResponse r =
                    fetchFacetPage(config.getSolrFieldConfig()
                                         .getInstitutionIdField(),
                                   institutionIds.size(),
                                   100);
                FacetField f =
                    r.getFacetField(config.getSolrFieldConfig()
                                          .getInstitutionIdField());
                for (Count c : f.getValues()) {
                    institutionIds.add(c.getName());
                }
            } catch (SolrServerException ex) {
                throw new AptrustException(ex);
            }
        } while (institutionIds.size() < count);
        return institutionIds;
    }

    @Override
    public InstitutionInfo getInstitutionInfo(String insitutionId)
        throws AptrustException {
        throw new UnsupportedOperationException("not yet implemented");
    }

    private long getResponseCount(String query) throws SolrServerException {
        ModifiableSolrParams params = new ModifiableSolrParams();
        params.set("q", query);
        return solr.query(params).getResults().getNumFound();
    }

    private QueryResponse fetchFacetPage(String field, int offset, int max)
        throws SolrServerException {
        ModifiableSolrParams params = new ModifiableSolrParams();
        params.set("facet.field", "institution_id");
        params.set("facet", "true");
        params.set("facet.limit", max);
        params.set("facet.offset", offset);
        return solr.query(params);
    }

    @Override
    public List<IngestProcessSummary> findIngestProcesses(String institutionId,
                                                          Date startDate,
                                                          String name,
                                                          IngestStatus status)
        throws AptrustException {
        throw new NotImplementedException();
    }

    @Override
    public PackageSummaryQueryResponse
        findPackageSummaries(String institutionId, SearchParams searchParams)
            throws AptrustException {

        throw new NotImplementedException();
    }
    
    @Override
    public AptrustPackageDetail getPackageDetail(String institutionId, String packageId)
        throws AptrustException {
        throw new NotImplementedException();
    }
    
    @Override
    public AptrustObjectDetail getObjectDetail(String institutionId,
                                               String packageId,
                                               String objectId)
        throws AptrustException {
        throw new NotImplementedException();

    }
}
