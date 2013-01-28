package org.aptrust.client.api;

import java.util.List;

import org.apache.solr.client.solrj.response.FacetField;

public class PackageSummaryQueryResponse {
    private List<PackageSummary> packageSummaries;
    private List<FacetField> facetFields;
    
    public PackageSummaryQueryResponse(
        List<PackageSummary> packageSummaries, List<FacetField> facetFields) {
        this.packageSummaries = packageSummaries;
        this.facetFields = facetFields;
    }
    
    public List<FacetField> getFacetFields() {
        return facetFields;
    }
    
    public List<PackageSummary> getPackageSummaries() {
        return packageSummaries;
    }
    
}
