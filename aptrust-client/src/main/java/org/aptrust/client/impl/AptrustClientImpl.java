package org.aptrust.client.impl;

import org.aptrust.client.api.AptrustClient;
import org.aptrust.client.api.InstitutionId;
import org.aptrust.client.api.AptrustException;
import org.aptrust.client.api.Summary;

/**
 * @author Daniel Bernstein
 * @created Dec 11, 2012
 * 
 */
public class AptrustClientImpl implements AptrustClient {
    private ClientConfig config;
    public AptrustClientImpl(ClientConfig config) {
        if(config == null){
            throw new IllegalArgumentException("client config must be non-null");
        }
        
        this.config = config;
    }
    
    @Override
    public Summary getSummary(InstitutionId id) throws AptrustException {
        return new Summary();
    }
}
