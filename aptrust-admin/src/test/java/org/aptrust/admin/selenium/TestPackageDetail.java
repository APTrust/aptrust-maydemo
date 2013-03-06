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
public class TestPackageDetail extends BaseSeleniumTest {

    @Test
    public void testDiscovery() {
        loginAsRoot();
        sc.open(getInstitutionRoot("uva")+"/package/package1");
        Assert.assertTrue(isElementPresent("id=package"));
        sc.select("id=objectId", "value=object1");
        sc.waitForPageToLoad("2000");
        Assert.assertTrue(isElementPresent("id=object1"));
    }


}
