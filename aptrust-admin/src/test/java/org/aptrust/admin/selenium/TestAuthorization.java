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
public class TestAuthorization extends BaseSeleniumTest {

    @Test
    public void testAccessDenied() {
        loginAsUva();
        sc.open(getInstitutionRoot("ncsu"));
        Assert.assertTrue(isTextPresent("Access Denied"));
    }
    
    @Test
    public void testRootAccess(){
        loginAsRoot();
        sc.open(getInstitutionRoot("ncsu"));
        new TestDashboard().verifyDashboard(sc);
    }
}
