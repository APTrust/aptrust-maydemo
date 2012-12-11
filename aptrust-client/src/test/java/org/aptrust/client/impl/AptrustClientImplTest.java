package org.aptrust.client.impl;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.aptrust.client.api.InstitutionId;
import org.aptrust.client.api.AptrustException;
import org.aptrust.client.api.Summary;
import org.aptrust.client.impl.ClientConfig;
import org.aptrust.client.impl.AptrustClientImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
/**
 * 
 * @author Daniel Bernstein
 * @created Dec 11, 2012
 *
 */
public class AptrustClientImplTest {

    private AptrustClientImpl client = null;

    @Before
    public void setUp() throws Exception {
        client = new AptrustClientImpl(new ClientConfig());
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testServiceClientConstructorFailed() {
        try{
            new AptrustClientImpl(null);
            fail();
        }catch(Exception ex){
            Assert.assertTrue(true);
        }
    }

    @Test
    public void testGetSummary() throws AptrustException{
        Summary summary = client.getSummary(new InstitutionId());
        Assert.assertNotNull(summary);
    }

}
