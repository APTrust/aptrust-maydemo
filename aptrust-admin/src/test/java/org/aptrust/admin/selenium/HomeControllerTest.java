package org.aptrust.admin.selenium;

import org.aptrust.admin.controller.HomeController;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
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
    public void testGetHome() {
        HomeController c = new HomeController();
        Assert.assertNotNull(c.getHome());
    }

}
