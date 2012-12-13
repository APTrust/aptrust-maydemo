package org.aptrust.client.impl;

/**
 * A class that encapsualtes the mapping from field purposes to the Solr field
 * name.  This level of indirection isolates the changes necessarily resulting 
 * from a solr schema change to this single class.
 */
public class SolrFieldConfig {

    String institutionIdField;

    String recordTypeField;

    String dpnBoundField;

    String accessControlPolicyField;

    String failedHealthCheckField;

    String operationStatusField;

    /**
     * Gets the value for the field name within Solr for the field containing
     * the institution id.
     */
    public String getInstitutionIdField() {
        return institutionIdField;
    }

    /**
     * Gets the field name within Solr for the field containing the record type
     * values.
     */
    public String getRecordTypeField() {
        return recordTypeField;
    }

    /**
     * Gets the field name within Solr for the field containing the "true" or 
     * "false" value indicating whether that record is flagged for inclusion in
     * the Digital Preservation Network.  A value of "true" is interpreted as an
     * indicator that the record is bound for DPN, any other value or no value
     * is interpreted as false.
     */
    public String getDpnBoundField() {
        return dpnBoundField;
    }

    /**
     * Gets the field name within Solr for the field containing the access 
     * control policy name.
     */
    public String getAccessControlPolicyField() {
        return accessControlPolicyField;
    }

    /**
     * Gets the field name within Solr for the field containing the value of 
     * "true" for all records with a failed health check.  All other values are
     * considered sufficient to indicate that no health checks have failed.
     */
    public String getFailedHealthCheckField() {
        return failedHealthCheckField;
    }

    /**
     * Gets the field name within Solr for the field containing the operation 
     * status for records with a field_type of "ingest".
     */
    public String getOperationStatusField() {
        return operationStatusField;
    }

}
