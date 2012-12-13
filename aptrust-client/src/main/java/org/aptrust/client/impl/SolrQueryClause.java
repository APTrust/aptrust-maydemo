package org.aptrust.client.impl;
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

}
