package org.aptrust.ingest.dspace;

import java.io.File;
import java.net.URISyntaxException;

import junit.framework.Assert;

import org.aptrust.common.exception.AptrustException;
import org.junit.Test;

public class DSpaceAIPPackageTest {

    @Test
    public void testStreamAIPParsing() throws AptrustException {
        DSpaceAIPPackage p = new DSpaceAIPPackage(getClass().getClassLoader().getResourceAsStream("22675554.zip"));
        Assert.assertEquals("hdl:123456789/8", p.getId());
        Assert.assertEquals("1.7.0-SNAPSHOT", p.getDspaceVersion());
        Assert.assertEquals("Test PDF Document", p.getTitle());
    }

    @Test
    public void testFileAIPParsing() throws AptrustException, URISyntaxException {
        DSpaceAIPPackage p = new DSpaceAIPPackage(new File(getClass().getClassLoader().getResource("22675554.zip").toURI()));
        Assert.assertEquals("hdl:123456789/8", p.getId());
        Assert.assertEquals("1.7.0-SNAPSHOT", p.getDspaceVersion());
        Assert.assertEquals("Test PDF Document", p.getTitle());
    }

}
