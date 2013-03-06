/*
 * Copyright (c) 2009-2013 AP Trust. All rights reserved.
 */
package org.aptrust.admin.selenium;

import junit.framework.Assert;

import org.junit.Test;

import com.thoughtworks.selenium.Selenium;

/**
 * 
 * @author Daniel Bernstein
 * 
 */
public class TestDashboard extends BaseSeleniumTest {

    @Test
    public void testDashboard() {
        loginAsUva();
        sc.open(getInstitutionRoot("uva"));
        
        verifyDashboard(sc);

    }

    public void verifyDashboard(Selenium sc) {
        Assert.assertTrue(isTextPresent(sc,"Welcome"));
        Assert.assertTrue(isElementPresent(sc,"id=institutional-activity"));
        Assert.assertTrue(isElementPresent(sc,"id=recent-ingests"));
    }

}
