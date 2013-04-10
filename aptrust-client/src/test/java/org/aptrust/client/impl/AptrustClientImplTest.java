package org.aptrust.client.impl;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
/**
 * 
 * @author Daniel Bernstein
 *
 */
public class AptrustClientImplTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetInstitutionInfo() throws Exception {
        ClientConfig config = EasyMock.createMock(ClientConfig.class);
        EasyMock.expect(config.getSolrUrl()).andReturn(("http://aptrust.org/solr"));
        EasyMock.replay(config);
        AptrustClientImpl client = new AptrustClientImpl(config);
        Assert.assertEquals("University of Virginia", client.getInstitutionInfo("uva").getFullName());
        Assert.assertEquals("xxx", client.getInstitutionInfo("xxx").getFullName());

        EasyMock.verify(config);
    }

}
