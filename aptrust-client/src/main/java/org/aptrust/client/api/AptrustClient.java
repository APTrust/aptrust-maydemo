package org.aptrust.client.api;

import org.aptrust.common.exception.AptrustException;
/**
 * @author Daniel Bernstein
 * @created Dec 11, 2012
 *
 */
public interface AptrustClient {
    /**
     * Returns a summary for the specified institution.
     * @param id
     * @return
     * @throws AptrustException
     */
    Summary getSummary(InstitutionId id) throws AptrustException;
}
