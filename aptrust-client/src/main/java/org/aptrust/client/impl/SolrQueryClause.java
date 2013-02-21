package org.aptrust.client.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * An implementation of a query-building DSL for use in generating simple 
 * query clauses made up of ANDing other clauses.
 */
public class SolrQueryClause {

    private String value;

    private SolrQueryClause(String clause) {
        value = clause;
    }

    public SolrQueryClause(String field, String value) {
        this.value = field.replaceAll(":", "\\:") + ":\""
                + value.replaceAll("\"", "\\\"") + "\"";
    }

    public SolrQueryClause not(SolrQueryClause ... notClauses) {
        StringBuffer sb = new StringBuffer();
        for (SolrQueryClause clause : notClauses) {
            if (sb.length() > 0) {
                sb.append(" ");
            }
            sb.append("-" + clause);
        }
        return new SolrQueryClause((value.startsWith("+") ? "" : "+") + value + " " + sb.toString());
    }

    public SolrQueryClause and(SolrQueryClause ... andClauses) {
        StringBuffer sb = new StringBuffer();
        for (SolrQueryClause clause : andClauses) {
            if (sb.length() > 0) {
                sb.append(" ");
            }
            sb.append("+" + clause);
        }
        return new SolrQueryClause((value.startsWith("+") ? "" : "+") + value + " " + sb.toString());
    }

    public SolrQueryClause and(String field, String value) {
        return and(new SolrQueryClause(field, value));
    }

    public String getQueryString() {
        return value;
    }

    public String toString() {
        return value;
    }

    /**
     * Creates a date range query.  This may be bounded at both ends or just 
     * one.  Values of null indicate an open range.  Both start and end must
     * not be null, as that would indicate a SolrQueryClause that includes all
     * values.
     * @param fieldName the name of the Date field to search 
     * @param start the Date for which only records with higher values will be
     * included.
     * @param end the Date for which only records with lower values will be
     * included.
     * @return the SolrQueryClause representing the range query specified with
     * the parametsr.
     */
    public static SolrQueryClause dateRange(String fieldName, Date start, Date end) {
        if (start == null && end == null) {
            throw new IllegalArgumentException("The date range query must be bounded at at least one end!");
        }
        //Syntax like this example
        // createdate:[1976-03-06T23:59:59.999Z TO *]
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        f.setTimeZone(TimeZone.getTimeZone("UTC"));
        return new SolrQueryClause(fieldName.replaceAll(":", "\\:") + ":[" + (start == null ? "*" : f.format(start)) + " TO " + (end == null ? "*" : f.format(end)) + "]");
    }

    /**
     * Creates a query clause formatted appropriately for Solr based on a user
     * entered query.  The current implementation assumes that the user-entered
     * query is already appropriately formatted for Solr.
     * @param query a user-entered query
     * @return a SolrQueryClause representing the intent of the user-entered query
     */
    public static SolrQueryClause parseUserQuery(String query) {
        return new SolrQueryClause(query);
    }
}
