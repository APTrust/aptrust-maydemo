package org.aptrust.client.api;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.aptrust.client.impl.AptrustPackageDetail;
import org.aptrust.common.exception.AptrustException;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * Defines a client interface that exposes methods to get information about
 * content in the AP Trust system.
 * 
 * @author Daniel Bernstein
 * @created Dec 11, 2012
 * 
 */
public interface AptrustClient {

    /**
     * Returns a Collection containing all known institution identifiers. This
     * may return an empty list, but should not return null.
     * 
     * @return A Collection of institution ids
     */
    public Collection<String> getInstitutionIds() throws AptrustException;

    /**
     * 
     * @return
     * @throws AptrustException
     */
    List<InstitutionInfo> getInstitutions() throws AptrustException;

    /**
     * Returns information about the institution with the given id.
     * 
     * @param institutionId
     * @return
     * @throws AptrustException
     *             if no institution is found with the given id, or if some
     *             other error prevents
     */
    public InstitutionInfo getInstitutionInfo(String institutionId)
        throws AptrustException;

    /**
     * Returns a summary for the specified institution.
     * 
     * @param id
     *            the unique identifier for the institution
     * @return
     * @throws AptrustException
     */
    @PreAuthorize("hasPermission(#institutionId, 'institutionId', 'admin')")
    public Summary getSummary(String institutionId) throws AptrustException;

    /**
     * Returns a list of ingest process summaries.
     * 
     * @param institutionId
     * @param startDate
     *            optional
     * @param name
     *            optional
     * @param status
     *            optional
     * @return
     * @throws AptrustException
     */
    @PreAuthorize("hasPermission(#institutionId, 'institutionId', 'admin')")
    List<IngestProcessSummary> findIngestProcesses(String institutionId,
                                                   Date startDate,
                                                   String name,
                                                   IngestStatus status)
        throws AptrustException;

    /**
     * 
     * @param institutionId
     * @param searchParams
     * @return
     * @throws AptrustException
     */
    @PreAuthorize("hasPermission(#institutionId, 'institutionId', 'admin')")
    public PackageSummaryQueryResponse findPackageSummaries(String institutionId,
                                                     SearchParams searchParams)
        throws AptrustException;

    /**
     * 
     * @param institutionId
     * @param packageId
     * @return
     * @throws AptrustException
     */
    @PreAuthorize("hasPermission(#institutionId, 'institutionId', 'admin')")
    public AptrustPackageDetail getPackageDetail(String institutionId, String packageId)
        throws AptrustException;

    /**
     * 
     * @param institutionId
     * @param packageId
     * @param objectId
     * @return
     * @throws AptrustException
     */
    @PreAuthorize("hasPermission(#institutionId, 'institutionId', 'admin')")
    public AptrustObjectDetail getObjectDetail(String institutionId, String packageId, String objectId)
        throws AptrustException;


}
