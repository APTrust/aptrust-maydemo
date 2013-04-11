package org.aptrust.common.solr;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;

/**
 * <h3>Solr Records</h3>
 * <p>
 *   In the current implementation all fields are both indexed
 *   and stored (except the included_pid field which is just indexed).
 * </p>
 * <ul>
 *   <li>
 *     ingest records
 *     <ul>
 *       <li>id</li>
 *       <li>record_type="ingest"</li>
 *       <li>institution_id</li>
 *       <li>title</li>
 *       <li>submitting_user</li>
 *       <li>operation_status</li>
 *       <li>object_count</li>
 *       <li>completed_object_count</li>
 *       <li>operation_start_date</li>
 *       <li>operation_end_date</li>
 *       <li>message</li>
 *       <li>included_pid (indexed only)</li>
 *     </ul>
 *   </li>
 *   <li>
 *     package records
 *     <ul>
 *       <li>id<li>
 *       <li>record_type="package"</li>
 *       <li>institution_id</li>
 *       <li>title</li>
 *       <li>dpn_bound</li>
 *       <li>access_control_policy</li>
 *       <li>ingest_date</li>
 *       <li>object_count</li>
 *       <li>included_pid (indexed only)</li>
 *     </ul>
 *   </li>
 *   <li>
 *     object records
 *     <ul>
 *       <li>id</li>
 *       <li>record_type="object"</li>
 *       <li>institution_id</li>
 *       <li>title</li>
 *       <li>package_id</li>
 *     </ul>
 *   </li>
 *   <li>
 *     content records
 *     <ul>
 *       <li>id</li>
 *       <li>record_type="content"</li>
 *       <li>institution_id</li>
 *       <li>package_id</li>
 *       <li>object_id</li>
 *       <li>last_health_check_date</li>
 *       <li>failed_health_check</li>
 *     </ul>
 *   </li>
 * </ul>
 */
public class AptrustSolrDocument {

    /**
     * The field name within Solr for the field containing the unique ID for
     * each Solr document.  These id values must be unique across all 
     * institutions and record types.
     */
    public static final String ID = "id";

    /**
     * The field name within Solr for the field containing the record type
     * values.  Possible values include "content", "object", "package" and
     * "ingest".  This field is stored and indexed for every Solr document.
     */
    public static final String RECORD_TYPE = "record_type";

    /**
     * The field name within Solr for the field containing the institution id.
     * This field is stored and indexed for every Solr document.
     */
    public static final String INSTITUTION_ID = "institution_id";

    /**
     * The field name within Solr for the field containing the title or name
     * for a given object, package or ingest.  This field is stored and indexed
     * for every record type, but may be blank or missing for untitled 
     * resources.
     */
    public static final String TITLE = "title";

    /**
     * The field name within Solr for the field containing pid values that are
     * expected to be part of the completed package or ingest operation.  This
     * can be queried to quickly determine the package or ingest to which an
     * an incoming digital object belongs.  If one wishes to query the package
     * or ingest to which an ingested pid belongs, he/she should find the 
     * "object" record for that pid and inspect the "package_id" field.
     */
    public static final String INCLUDED_PID = "included_pid";

    /**
     * The field name within Solr for the field containing the username of the
     * user who submitted/initiated an ingest operation.  This field is only
     * expected to appear in Solr documents with the "record_type" of "ingest".
     */
    public static final String SUBMITTING_USER = "submitting_user";

    /**
     * The field name within Solr for the field containing the status of an 
     * ingest operation.  This may have the value "IN_PROGRESS", "COMPLETED" or
     * "FAILED" and obviously is only present in Solr documents with the 
     * "record_type" of "ingest".
     */
    public static final String OPERATION_STATUS = "operation_status";

    /**
     * The field name within Solr for the field containing the progress (in
     * number of objects ingested) of the ingest operation.  This field  is
     * only present in Solr documents with the "record_type" of "ingest" and is
     * likely only useful for those with the "operation_status" value of
     * "IN_PROGRESS".
     */
    public static final String COMPLETED_OBJECT_COUNT = "completed_object_count";

    /**
     * The field name within Solr for the field containing the date at which
     * an ingest operation began.  This field  is only present in Solr
     * documents with the "record_type" of "ingest".
     */
    public static final String OPERATION_START_DATE = "operation_start_date";

    /**
     * The field name within Solr for the field containing the date at which
     * an ingest operation completed.  This field  is only present in Solr
     * documents with the "record_type" of "ingest".
     */
    public static final String OPERATION_END_DATE = "operation_end_date";
    
    /**
     * The field name within Solr for the field containing any error message
     * associated with an ingest operation.  This field is only present in
     * Solr documents with the "record_type" of "ingest" and an 
     * "operation_status" value of "FAILED".
     */
    public static final String MESSAGE = "message";

    /**
     * The field name within Solr for the field containing the "true" or
     * "false" value indicating whether that record is flagged for inclusion in
     * the Digital Preservation Network.  A value of "true" is interpreted as
     * an indicator that the record is bound for DPN, any other value or no
     * value is interpreted as false.  This field is only present in Solr
     * documents with the "record_type" of "package".
     */
    public static final String DPN_BOUND = "dpn_bound";

    
    /**
     * The field name within Solr for the field containing the access control 
     * policy name.  This field is only present in Solr documetns with the 
     * "record_type" of "package".
     */
    public static final String ACCESS_CONTROL_POLICY = "access_control_policy";

    /**
     * The field name within Solr for the field containing the date 
     * a package was ingested.  This field is only present in Solr documents
     * with the "record_type" of "package".
     */
    public static final String INGEST_DATE = "ingest_date";


    /**
     * The field name within Solr for the field containing the number of 
     * objects contained within a package or in all the packages of a given 
     * ingest operation.  This field is only present in Solr documents with the
     * "record_type" of "package" or "ingest".
     */
    public static final String OBJECT_COUNT = "object_count";

    /**
     * The field name within Solr for the field containing the date of the last
     * health check for some (or all) of the contents of this package.  This 
     * field is only present in Solr documents with the "record_type" of
     * "content".
     */
    public static final String LAST_HEALTH_CHECK_DATE = "last_health_check_date";

    /**
     * The field name within Solr for the field containing the value of "true"
     * for all records with a failed health check.  All other values are
     * considered sufficient to indicate that no health checks have failed.  
     * This field is only present in Solr documents with the "record_type" of
     * "content".
     */
    public static final String FAILED_HEALTH_CHECK = "failed_health_check";

    /**
     * The field name within Solr for the field containing the id of the 
     * package to which an object belongs.  This field is only present in Solr
     * documents with the "record_type" of "object" or "content".
     */
    public static final String PACKAGE_ID = "package_id";

    /**
     * The field name within Solr for the field containing the id of the 
     * object to which content belongs.  This field is only present in Solr
     * documents with the "record_type" of "content".
     */
    public static final String OBJECT_ID = "object_id";

    /**
     * A method that uses reflection to build a SolrInputDocument.  For each 
     * method that is annotated with a SolrField annotation, a field is added
     * with the value that is the result of the method invocation.  This method
     * ensures that the SolrInputDocument is complete and valid according to 
     * the rules specified in this class.  All required fields will be present
     * or an Exception will be thrown.  Additional fields specified through
     * SolrField annotations will be included if present.
     * @param o an object with methods annotated with the SolrField annotation
     * @return a SolrInputDocument with fields from the passed object
     */
    public static SolrInputDocument createValidSolrDocument(Object o) {
        // Step One: Add all fields based on SolrField annotations
        Method[] methods = o.getClass().getMethods();
        SolrInputDocument doc = new SolrInputDocument();
        for (Method method : methods) {
            if (method.isAnnotationPresent(SolrField.class) && method.getParameterTypes().length == 0) {
                try {
                    Object value = method.invoke(o);
                    if (value != null) {
                        doc.addField(method.getAnnotation(SolrField.class).name(), value);
                    }
                } catch (IllegalAccessException ex) {
                    throw new RuntimeException("Method annotated with SolrField must be public! (" + method.getName() + ")", ex);
                } catch (IllegalArgumentException ex) {
                    throw new RuntimeException("Method annotated with SolrField must have no parameters. (" + method.getName() + ")", ex);
                } catch (InvocationTargetException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        
        // Step Two: Validate the document
        
        // the following fields are required regardless of record_type
        assertRequiredField(RECORD_TYPE, doc, o, String.class);
        assertRequiredField(INSTITUTION_ID, doc, o, String.class);
        assertRequiredField(ID, doc, o, String.class);

        String recordType = (String) doc.getField(RECORD_TYPE).getValue();
        if (recordType.equals("ingest")) {
            assertRequiredField(SUBMITTING_USER, doc, o, String.class);
            assertRequiredField(OPERATION_STATUS, doc, o, String.class);
            assertRequiredField(OPERATION_START_DATE, doc, o, Date.class);
            String status = (String) doc.getField(OPERATION_STATUS).getValue();
            if (status.equals("IN_PROGRESS")) {
                assertRequiredField(OBJECT_COUNT, doc, o, Integer.class);
                assertRequiredField(COMPLETED_OBJECT_COUNT, doc, o, Integer.class);
            } else if (status.equals("FAILED")) {
                assertRequiredField(MESSAGE, doc, o, String.class);
            } else if (status.equals("COMPLETED")) {
                assertRequiredField(OPERATION_END_DATE, doc, o, Date.class);
            }
            // all other fields are optional
        } else if (recordType.equals("package")) {
            assertRequiredField(DPN_BOUND, doc, o, Boolean.class);
            assertRequiredField(ACCESS_CONTROL_POLICY, doc, o, String.class);
            assertRequiredField(OBJECT_COUNT, doc, o, Integer.class);
            assertRequiredField(TITLE, doc, o, String.class);
            assertRequiredField(INGEST_DATE, doc, o, Date.class);
            // all other fields are optional
        } else if (recordType.equals("object")) {
            assertRequiredField(PACKAGE_ID, doc, o, String.class);
            // all other fields are optional
        } else if (recordType.equals("content")) {
            assertRequiredField(PACKAGE_ID, doc, o, String.class);
            assertRequiredField(OBJECT_ID, doc, o, String.class);
            assertRequiredField(LAST_HEALTH_CHECK_DATE, doc, o, Date.class);
            assertRequiredField(FAILED_HEALTH_CHECK, doc, o, String.class);
        }

        return doc;
    }

    private static void assertRequiredField(String field, SolrInputDocument doc, Object o, Class<?> c) {
        if (doc.getField(field) == null) {
            throw new IllegalArgumentException("Object " + o.getClass().getName() + " is not annotated with the required \"" + field + "\" field!");
        } else if (!(doc.getField(field).getValue().getClass().isAssignableFrom(c))) {
            throw new IllegalArgumentException("The method annotated with the SolrField named \"" + field + "\" on object " + o.getClass().getName() + " must have a return type of \"" + c.getName() + "\". (has \"" + doc.getField(field).getValue().getClass() + "\" instead)");
        }
    }

    /**
     * A method that uses reflection to populate the provided object with
     * values from a Solr document.  For each method that is annotated with a
     * AptrustSolrDocument annotation and accepts a single parameter that method is 
     * invoked with the value from the Solr document.
     * @param o an object with methods annotated with the AptrustSolrDocument annotation
     * @return a SolrInputDocument with fields from the passed object
     */
    public static void populateFromSolrDocument(Object o, SolrDocument d) {
        Method[] methods = o.getClass().getMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(SolrField.class) && method.getParameterTypes().length == 1) {
                try {
                    Object value = d.getFirstValue(method.getAnnotation(SolrField.class).name());
                    if (value != null) {
                        method.invoke(o, value);
                    }
                } catch (IllegalAccessException ex) {
                    throw new RuntimeException("Method annotated with AptrustSolrDocument must be public! (" + method.getName() + ")", ex);
                } catch (IllegalArgumentException ex) {
                    throw new RuntimeException("Method annotated with AptrustSolrDocument must have exactly one parameter. (" + method.getName() + ")", ex);
                } catch (InvocationTargetException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }
}
