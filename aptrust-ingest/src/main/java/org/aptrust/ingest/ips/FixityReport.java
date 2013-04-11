package org.aptrust.ingest.ips;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FixityReport {

    private String spaceId;
    
    private Date reportDate;

    private Date previousReportDate;

    private Map<String, FixityCheck> contentIdToCheckMap;

    public static final Pattern REPORT_CONTENT_ID_PATTERN = Pattern.compile("bit-integrity/fixity-report-fingerprints-([^-]+)-(\\d\\d\\d\\d-\\d\\d-\\d\\dT\\d\\d:\\d\\d)-vs-manifest-(\\d\\d\\d\\d-\\d\\d-\\d\\dT\\d\\d:\\d\\d)\\.tsv");

    public FixityReport(String contentId, InputStream is) throws IOException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
        Matcher m = REPORT_CONTENT_ID_PATTERN.matcher(contentId);
        if (m.matches()) {
            try {
                spaceId = m.group(1);
                reportDate = dateFormat.parse(m.group(2));
                previousReportDate = dateFormat.parse(m.group(3));

                contentIdToCheckMap = new HashMap<String, FixityCheck>();
                BufferedReader r = new BufferedReader(new InputStreamReader(is));
                String line = r.readLine(); // reads the header line
                while ((line = r.readLine()) != null) {
                    String[] cols = line.split("\t");
                    FixityCheck c = new FixityCheck();
                    c.checksum = cols[3];
                    c.previousChecksum = cols[2];
                    c.passed = "VALID".equals(cols[4]);
                    contentIdToCheckMap.put(cols[1], c);
                }
            } catch (ParseException ex) {
                throw new IllegalArgumentException("Provided contentId does not have parsible dates!", ex);
            }
        } else {
            throw new IllegalArgumentException("Provided contentId does not appear to be a fixity report!");
        }
    }

    public Set<String> getContentIds() {
        return contentIdToCheckMap.keySet();
    }

    public FixityCheck getFixityCheck(String contentId) {
        return contentIdToCheckMap.get(contentId);
    }

    public String getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(String spaceId) {
        this.spaceId = spaceId;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public Date getPreviousReportDate() {
        return previousReportDate;
    }

    public void setPreviousReportDate(Date previousReportDate) {
        this.previousReportDate = previousReportDate;
    }

    public Map<String, FixityCheck> getContentIdToCheckMap() {
        return contentIdToCheckMap;
    }

    public void setContentIdToCheckMap(Map<String, FixityCheck> contentIdToCheckMap) {
        this.contentIdToCheckMap = contentIdToCheckMap;
    }

    public class FixityCheck {

        public String checksum;

        public String previousChecksum;

        public boolean passed;

    }

    public static String getSpaceIdFromReportContentId(String contentId) {
        Matcher m = REPORT_CONTENT_ID_PATTERN.matcher(contentId);
        if (m.matches()) {
            return m.group(1);
        } else {
            return null;
        }
        
    }
}
