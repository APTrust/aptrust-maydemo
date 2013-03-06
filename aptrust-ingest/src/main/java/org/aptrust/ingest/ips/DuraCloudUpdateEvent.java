package org.aptrust.ingest.ips;

public class DuraCloudUpdateEvent {

    private String contentId;

    public DuraCloudUpdateEvent(String contentId) {
        this.contentId = contentId;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }
}
