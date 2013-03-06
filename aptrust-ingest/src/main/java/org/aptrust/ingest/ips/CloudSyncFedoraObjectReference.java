package org.aptrust.ingest.ips;

import java.util.List;

import org.duracloud.client.ContentStore;

public class CloudSyncFedoraObjectReference {

    private ContentStore cs;

    private String spaceId;

    private String pid;
    
    public CloudSyncFedoraObjectReference(String pid) {
        this.pid = pid;
    }

    public boolean isObjectComplete() {
        return false;
    }

    public List<String> getObjectContentIds() {
        return null;
    }
}
