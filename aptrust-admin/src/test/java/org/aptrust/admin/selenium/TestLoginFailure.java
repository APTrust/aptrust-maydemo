/*
 * Copyright (c) 2009-2013 AP Trust. All rights reserved.
 */
package org.aptrust.admin.selenium;

import org.junit.Assert;
import org.junit.Test;

/**
 * 
 * @author Daniel Bernstein
 * 
 */
public class TestLoginFailure extends BaseSeleniumTest {

    @Test
    public void testLoginFailure() {
        login("bad", "password");
        Assert.assertTrue(isElementPresent("css=.error"));
    }
}
