package org.aptrust.admin.controller;

import java.util.LinkedList;

import org.aptrust.client.api.AptrustClient;
import org.aptrust.client.api.InstitutionInfo;
import org.easymock.EasyMock;
import org.junit.Test;
import org.springframework.ui.ExtendedModelMap;

/**
 * 
 * @author Daniel Bernstein
 * 
 */
public class HomeControllerTest {


    @Test
    public void testGetDashboards() throws Exception {
        AptrustClient client = EasyMock.createMock(AptrustClient.class);
        
        EasyMock.expect(client.getInstitutions())
                .andReturn(new LinkedList<InstitutionInfo>());
        EasyMock.replay(client);
        HomeController c = new HomeController(client);
        c.getHome(new ExtendedModelMap());
        EasyMock.verify(client);
    }
}
