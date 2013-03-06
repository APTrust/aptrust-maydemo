package org.aptrust.ingest.ips;

import java.util.regex.Matcher;

import junit.framework.Assert;

import org.junit.Test;

public class DropboxProcessorTest {

    @Test
    public void testSimplePidRecognitionPattern() {
        Matcher m = DropboxProcessor.FEDORA_CLOUDSYNC_CONTENTID_PATTERN.matcher("uva-lib:602146");
        Assert.assertTrue(m.matches());
        Assert.assertEquals("uva-lib:602146", m.group(1));
    }

}
