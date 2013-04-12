package org.aptrust.client.api;

import java.util.Date;

public class ContentSummary {

    private String name;

    private boolean passed;

    private Date lastFixityCheck;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    public Date getLastFixityCheck() {
        return lastFixityCheck;
    }

    public void setLastFixityCheck(Date lastFixityCheck) {
        this.lastFixityCheck = lastFixityCheck;
    }
}
