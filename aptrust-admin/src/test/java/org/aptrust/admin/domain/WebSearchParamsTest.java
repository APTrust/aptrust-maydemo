package org.aptrust.admin.domain;

import java.util.LinkedList;
import java.util.List;

import junit.framework.Assert;

import org.aptrust.client.api.SearchConstraint;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author Daniel Bernstein
 * 
 */
public class WebSearchParamsTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test() throws Exception {
        List<SearchConstraint> constraints = new LinkedList<SearchConstraint>();
        SearchConstraint sc = new SearchConstraint();
        sc.setName("name");
        sc.setValue("value");
        constraints.add(sc);
        WebSearchParams wsp = new WebSearchParams("test", constraints);
        String output = "query=test&constraints[0].name=name&constraints[0].value=value";
        Assert.assertEquals(output, wsp.toQueryStringWithout(null));
        Assert.assertEquals("query=test", wsp.toQueryStringWithout(sc));
        Assert.assertEquals(output+"&constraints[1].name=name1&constraints[1].value=value1", wsp.toQueryStringWithConstraint("name1","value1"));
    }
}
