package org.aptrust.admin.controller;

import java.util.ArrayList;
import java.util.Date;

import org.aptrust.client.api.AptrustClient;
import org.aptrust.client.api.IngestProcessSummary;
import org.aptrust.client.api.IngestStatus;
import org.aptrust.client.api.Summary;
import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.ExtendedModelMap;

/**
 * 
 * @author Daniel Bernstein
 * 
 */
public class HomeControllerTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetHome() throws Exception {
        AptrustClient client = EasyMock.createMock(AptrustClient.class);
        Summary summary = EasyMock.createMock(Summary.class);
        EasyMock.expect(client.getSummary(EasyMock.isA(String.class)))
                .andReturn(summary);
        EasyMock.expect(client.findIngestProcesses(EasyMock.isA(String.class),
                                                   EasyMock.isA(Date.class),
                                                   EasyMock.anyObject(String.class),
                                                   EasyMock.anyObject(IngestStatus.class)))
                .andReturn(new ArrayList<IngestProcessSummary>());

        EasyMock.replay(client);
        HomeController c = new HomeController(client);
        Assert.assertNotNull(c.getHome("1", new ExtendedModelMap()));
        EasyMock.verify(client);
    }
}
