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
    public void testGet() {
        sc.open(getAppRoot() + "/html/1");
        Assert.assertTrue(isTextPresent("Welcome"));
        Assert.assertTrue(isElementPresent("id=institutional-activity"));
        Assert.assertTrue(isElementPresent("id=recent-ingests"));

    }
}
