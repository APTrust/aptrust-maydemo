package org.aptrust.client.api;

import java.util.Collection;

import org.aptrust.common.exception.AptrustException;
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
     * Returns a Collection containing all known 
     * institution identifiers.  This may return an
     * empty list, but should not return null.
     * @return A Collection of institution ids
     */
    public Collection<String> getInstitutionIds() throws AptrustException;

    /**
     * Returns information about the institution with the given id.
     * @param institutionId
     * @return
     * @throws AptrustException if no institution is found
     * with the given id, or if some other error prevents 
     */
    public InstitutionInfo getInstitutionInfo(String institutionId) throws AptrustException;
    
    /**
     * Returns a summary for the specified institution.
     * @param id the unique identifier for the institution
     * @return
     * @throws AptrustException
     */
    public Summary getSummary(String institutionId) throws AptrustException;
}
