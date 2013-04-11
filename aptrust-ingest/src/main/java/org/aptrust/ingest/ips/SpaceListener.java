package org.aptrust.ingest.ips;


public interface SpaceListener {

    public void notifyUpdate(String contentId) throws Exception;
    
    public void notifyDelete(String contentId) throws Exception;
    
}
