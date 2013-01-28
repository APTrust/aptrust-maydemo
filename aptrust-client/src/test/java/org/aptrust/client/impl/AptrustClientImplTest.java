package org.aptrust.client.impl;

import static org.junit.Assert.fail;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import junit.framework.Assert;

import org.aptrust.client.api.IngestProcessSummary;
import org.aptrust.client.api.IngestStatus;
import org.aptrust.client.api.SearchParams;
import org.aptrust.common.exception.AptrustException;
import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

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
        Properties p = new Properties();
        p.load(getClass().getClassLoader()
                         .getResourceAsStream("client-config-example.properties"));
        client = new AptrustClientImpl(new PropertiesClientConfig(p));
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testServiceClientConstructorFailed() {
        try {
            new AptrustClientImpl(null);
            fail();
        } catch (Exception ex) {
            Assert.assertTrue(true);
        }
    }

    /*
     * @Test public void testListInstitutions() throws AptrustException {
     * Collection<String> institutionIds = client.getInstitutionIds();
     * Assert.assertNotNull(institutionIds); for (String id : institutionIds) {
     * System.out.println(id); } }
     * 
     * @Test public void testGetInsitution() throws AptrustException {
     * InstitutionInfo institution = client.getInstitutionInfo("uva");
     * Assert.assertNotNull(institution); }
     * 
     * @Test public void testGetSummary() throws AptrustException{ Summary
     * summary = client.getSummary("uva"); Assert.assertNotNull(summary);
     * 
     * Assert.assertEquals(summary.getInstitutionId(), "uva");
     * Assert.assertNotNull(summary.getPackageCount());
     * Assert.assertNotNull(summary.getObjectCount());
     * //Assert.assertNotNull(summary.getBytesUsed());
     * Assert.assertNotNull(summary.getDpnBoundPackageCount());
     * Assert.assertNotNull(summary.getPublicPackageCount());
     * Assert.assertNotNull(summary.getPrivatePackageCount());
     * Assert.assertNotNull(summary.getInstitutionPackageCount());
     * Assert.assertNotNull(summary.getFailedPackageCount());
     * 
     * Assert.assertEquals(summary.getPackageCount().intValue(),
     * summary.getPrivatePackageCount().intValue() +
     * summary.getInstitutionPackageCount().intValue() +
     * summary.getPublicPackageCount().intValue()); }
     */
    @Test
    public void testFindIngestProcess() throws AptrustException {
        String institutionId = "uva";
        Date startDate = new Date();
        String name = "test name";
        IngestStatus status = IngestStatus.COMPLETED;

        try {
            List<IngestProcessSummary> details =
                client.findIngestProcesses(institutionId,
                                           startDate,
                                           name,
                                           status);
            Assert.assertFalse(true);
        } catch (NotImplementedException ex) {
            Assert.assertTrue(true);
        }

    }

    @Test
    public void testFindPackageSummaries() {
        SearchParams searchParams = EasyMock.createMock(SearchParams.class);
        EasyMock.replay(searchParams);

        try {
            Assert.assertNotNull(this.client.findPackageSummaries("id",
                                                                  searchParams));
            Assert.fail();
        } catch (Exception ex) {
            Assert.assertTrue(true);
        }

        EasyMock.verify(searchParams);
    }

    @Test
    public void testGetPackageDetail() {
        try {
            Assert.assertNotNull(this.client.getPackageDetail("institutionId",
                                                              "packageId"));
            Assert.fail();
        } catch (Exception ex) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void testGetObjectDetail() {
        try {
            Assert.assertNotNull(this.client.getObjectDetail("institutionId",
                                                             "packageId",
                                                             "objectId"));
            Assert.fail();
        } catch (Exception ex) {
            Assert.assertTrue(true);
        }
    }

}
