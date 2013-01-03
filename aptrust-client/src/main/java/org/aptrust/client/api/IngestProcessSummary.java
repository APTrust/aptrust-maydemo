package org.aptrust.client.api;

import java.util.Date;

/**
 * 
 * @author Daniel Bernstein Date: Jan 2, 2013
 * 
 */
public class IngestProcessSummary {

    private String institutionId;
    private String name;
    private String initiatingUser;
    private IngestStatus status;
    private int progress;
    private Date startDate;
    private Date endDate;
    private String message;
    
    public IngestProcessSummary(
        String institutionId, String name, String initiatingUser,
        IngestStatus status, int progress, Date startDate, Date endDate, String message) {
        super();
        this.institutionId = institutionId;
        this.name = name;
        this.initiatingUser = initiatingUser;
        this.status = status;
        this.progress = progress;
        this.startDate = startDate;
        this.endDate = endDate;
        this.message = message;
    }

    public String getInstitutionId() {
        return institutionId;
    }

    public String getName() {
        return name;
    }
    
    public String getInitiatingUser() {
        return initiatingUser;
    }

    public IngestStatus getStatus() {
        return status;
    }

    public int getProgress() {
        return progress;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getMessage() {
        return message;
    }
}
