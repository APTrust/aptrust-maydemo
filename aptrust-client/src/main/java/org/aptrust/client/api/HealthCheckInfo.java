package org.aptrust.client.api;

import java.util.Date;

public class HealthCheckInfo {
    private Date date;
    private boolean good;

    public HealthCheckInfo(Date date, boolean good) {
        this.date = date;
        this.good = good;
    }

    public Date getDate() {
        return date;
    }

    public boolean isGood() {
        return good;
    }

}
