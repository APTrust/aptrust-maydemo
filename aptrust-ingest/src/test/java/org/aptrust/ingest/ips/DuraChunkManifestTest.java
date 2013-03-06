package org.aptrust.ingest.ips;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import junit.framework.Assert;

import org.aptrust.common.exception.AptrustException;
import org.junit.Test;

public class DuraChunkManifestTest {
    
    @Test
    public void testBasicManifestParsing() throws AptrustException, JAXBException {
        JAXBContext jc = JAXBContext.newInstance(DuraChunkManifest.class);
        Unmarshaller u = jc.createUnmarshaller();
        DuraChunkManifest m = (DuraChunkManifest) u.unmarshal(getClass().getClassLoader().getResourceAsStream("dura-chunk-example-manifest.xml"));
        Assert.assertEquals("0.2", m.header.schemaVersion);
        Assert.assertEquals("test:1+content+content.0", m.header.sourceContent.contentId);
        Assert.assertEquals(19, m.chunks.length);
    }

}
