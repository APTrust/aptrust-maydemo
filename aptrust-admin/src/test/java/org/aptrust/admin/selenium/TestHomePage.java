/*
 * Copyright (c) 2009-2012 AP Trust. All rights reserved.
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
        sc.open(getAppRoot() + "/");
        Assert.assertTrue(isTextPresent("Welcome"));
    }
}
