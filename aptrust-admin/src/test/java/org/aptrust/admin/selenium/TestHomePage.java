/*
 * Copyright (c) 2009-2013 AP Trust. All rights reserved.
 */
package org.aptrust.admin.selenium;

import junit.framework.Assert;

import org.junit.Test;

/**
 * 
 * @author Daniel Bernstein
 * 
 */
public class TestHomePage extends BaseSeleniumTest {

    @Test
    public void testHomePage() {
        sc.open(getAppRoot() + "/");
        Assert.assertTrue(isElementPresent("css=#institution-list a"));
        clickAndWait("css=#institution-list a");
        Assert.assertTrue(isElementPresent("id=loginForm"));
        
        
    }
}
