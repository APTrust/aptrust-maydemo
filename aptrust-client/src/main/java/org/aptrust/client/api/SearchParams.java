package org.aptrust.client.api;

import java.util.List;

import org.springframework.util.AutoPopulatingList;

/**
 * 
 * @author Daniel Bernstein
 *
 */
public class SearchParams {
    private String query;
    
    private List<SearchConstraint> constraints;
    
    public SearchParams(){
        
    }
    
    public SearchParams(
        String query, List<SearchConstraint> constraints) {
        this.query = query;
        this.constraints = constraints;
    }

    public String getQuery() {
        return query;
    }
    
    public void setQuery(String query) {
        this.query = query;
    }

    public List<SearchConstraint> getConstraints() {
        return constraints;
    }
    
    public void setConstraints(List<SearchConstraint> constraints) {
        this.constraints = constraints;
    }
}
