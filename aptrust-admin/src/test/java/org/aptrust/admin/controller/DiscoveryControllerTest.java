package org.aptrust.admin.controller;

import org.aptrust.admin.domain.WebSearchParams;
import org.aptrust.client.api.AptrustClient;
import org.aptrust.client.api.PackageSummaryQueryResponse;
import org.aptrust.client.api.SearchParams;
import org.easymock.EasyMock;
import org.junit.Test;
import org.springframework.ui.Model;

/**
 * 
 * @author Daniel Bernstein
 * 
 */
public class DiscoveryControllerTest {


    @Test
    public void testDiscovery() throws Exception {
        AptrustClient client = EasyMock.createMock(AptrustClient.class);
        PackageSummaryQueryResponse psqr = EasyMock.createMock(PackageSummaryQueryResponse.class);
        EasyMock.expect(client.findPackageSummaries(EasyMock.isA(String.class), EasyMock.isA(SearchParams.class), EasyMock.isA(String.class), EasyMock.isA(String.class), EasyMock.isA(String.class)))
                .andReturn(psqr);

        WebSearchParams sp = new WebSearchParams();
        Model map = EasyMock.createMock(Model.class);
        EasyMock.expect(map.addAttribute(DiscoveryController.QUERY_RESPONSE_KEY, psqr)).andReturn(map);
        EasyMock.expect(map.addAttribute(DiscoveryController.SEARCH_PARAMS_KEY, sp)).andReturn(map);

        EasyMock.replay(client,map,psqr);
        DiscoveryController c = new DiscoveryController(client);
        c.get("institutionId", sp, map);
        EasyMock.verify(client,map,psqr);
    }
}
