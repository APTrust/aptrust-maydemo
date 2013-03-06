package org.aptrust.ingest.ips;

import org.aptrust.ingest.dspace.DSpaceAIPPackage;

public class RecognizedContentReference {

    private String contentId;

    private DSpaceAIPPackage dspaceAip;

    private String fedoraPid;

    private long size;

    public RecognizedContentReference(String contentId, DSpaceAIPPackage p, long size) {
        this.contentId = contentId;
        dspaceAip = p;
        this.size = size;
    }

    public RecognizedContentReference(String contentId, String fedoraPid, long size) {
        this.contentId = contentId;
        this.fedoraPid = fedoraPid;
        this.size = size;
    }

    public boolean isFedoraObject() {
        return fedoraPid != null;
    }

    public boolean isDSpaceAIP() {
        return dspaceAip != null;
    }

    public String getId() {
        return fedoraPid == null ? dspaceAip.getId() : fedoraPid;
    }

    public String getContentId() {
        return contentId;
    }

    public long getSize() {
        return size;
    }
}
