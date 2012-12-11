package org.aptrust.client.api;
/**
 * @author Daniel Bernstein
 * @created Dec 11, 2012
 *
 */
public interface AptrustClient {
    Summary getSummary(InstitutionId id) throws AptrustException;
}
