package org.aptrust.ingest.ips;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import junit.framework.Assert;

import org.junit.Test;

public class FixityReportTest {
    
    @Test
    public void testBasicManifestParsing() throws Exception {
        InputStream is = getClass().getClassLoader().getResourceAsStream("fixity-report-sample.tsv");
        Assert.assertTrue("Verifying presence of test file", is != null);
        String contentId = "bit-integrity/fixity-report-fingerprints-uvastaging-2013-02-09T02:51-vs-manifest-2013-02-09T02:52.tsv";
        FixityReport r = new FixityReport(contentId, is);
        Assert.assertEquals("Verify that the space Id was properly parsed.", "uvastaging", r.getSpaceId());
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        Assert.assertEquals("Verify that the report date was parsed.", format.format(format.parse("2013-02-09 2:51")), format.format(r.getReportDate()));
        Assert.assertEquals("Verify that a particular entry is present", true, r.getFixityCheck("uva-lib:602179+technicalMetadata+technicalMetadata.0").passed);
        Assert.assertEquals("Verify that a checksum is present", "987237020811cdf3d7b980bed896522a", r.getFixityCheck("uva-lib:602179+preservationMaster+preservationMaster.0").checksum);
    }

}
