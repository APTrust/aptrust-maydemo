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
public class TestDiscovery extends BaseSeleniumTest {

    @Test
    public void testDiscovery() {
        loginAsRoot();
        sc.open(getInstitutionRoot("uva")+"/discovery");
        Assert.assertFalse(isElementPresent("id=filters"));
        Assert.assertTrue(isElementPresent("id=results"));
        sc.open(getInstitutionRoot("uva")+"/discovery?query=test");
        Assert.assertEquals("test", sc.getValue("id=search-text-field"));
    }


}
