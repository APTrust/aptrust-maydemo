package org.aptrust.client.api;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 
 * @author Daniel Bernstein
 *
 */
public class SearchParams {
    private String query;
    
    private List<SearchConstraint> constraints;
    public static final String DATE_TIME_FORMAT = "MM/dd/yyyy";
    private long startIndex = 0;
    private int pageSize = 20;
    
    @DateTimeFormat(pattern=DATE_TIME_FORMAT)
    private Date startDate;
    @DateTimeFormat(pattern=DATE_TIME_FORMAT)
    private Date endDate;
    
    public SearchParams(){
        
    }
    
    public SearchParams(
        String query, List<SearchConstraint> constraints) {
        this.query = query;
        this.constraints = constraints;
    }
    
    public long getStartIndex() {
        return startIndex;
    }
    
    public void setStartIndex(long startIndex) {
        this.startIndex = startIndex;
    }

    public int getPageSize() {
        return pageSize;
    }
    
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
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
    
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    
    public Date getStartDate() {
        return startDate;
    }
    
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    
    public Date getEndDate() {
        return endDate;
    }
}
