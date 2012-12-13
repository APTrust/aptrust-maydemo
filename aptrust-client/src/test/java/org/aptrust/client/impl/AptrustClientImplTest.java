package org.aptrust.client.impl;

import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.Properties;

import junit.framework.Assert;

import org.aptrust.client.api.Summary;
import org.aptrust.common.exception.AptrustException;
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
        //Properties p = new Properties();
        //p.load(getClass().getClassLoader().getResourceAsStream("client-config.properties"));
        //client = new AptrustClientImpl(new PropertiesClientConfig(p));
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

    /*
    @Test
    public void testListInstitutions() throws AptrustException {
        Collection<String> institutionIds = client.getInstitutionIds();
        Assert.assertNotNull(institutionIds);
        for (String id : institutionIds) {
            System.out.println(id);
        }
    }

    @Test
    public void testGetSummary() throws AptrustException{
        Summary summary = client.getSummary("uva");
        Assert.assertNotNull(summary);

        Assert.assertEquals(summary.getInstitutionId(), "uva");
        Assert.assertNotNull(summary.getPackageCount());
        Assert.assertNotNull(summary.getObjectCount());
        //Assert.assertNotNull(summary.getBytesUsed());
        Assert.assertNotNull(summary.getDpnBoundPackageCount());
        Assert.assertNotNull(summary.getPublicPackageCount());
        Assert.assertNotNull(summary.getPrivatePackageCount());
        Assert.assertNotNull(summary.getInstitutionPackageCount());
        Assert.assertNotNull(summary.getFailedPackageCount());

        Assert.assertEquals(summary.getPackageCount().intValue(), summary.getPrivatePackageCount().intValue() + summary.getInstitutionPackageCount().intValue() + summary.getPublicPackageCount().intValue());
    }
    */

}
