package org.aptrust.ingest.ips;

import org.aptrust.ingest.api.IngestManifest;

/**
 * A application written to respond to updates to the "staging" stores in a 
 * DuraCloud instance.  As recognized files are detected by this service,
 * ingestion is advanced and the status is stored in the Solr index that backs
 * the Administrative Interface.
 */
public class IngestProcessingService {


    public void notifyUpdate(DuraCloudUpdateEvent e) {
        
    }
    
    private boolean isManifest(DuraCloudUpdateEvent e) { 
        return false;
    }
    
    private void processNewManifest(IngestManifest m) {
        // 1. create a fedora object for the ingest operation
        // 2. create any new package objects required by the manifest
        // 3. update the in-memory manifest with the PID of that object and
        //    any created package objects
        // 4. write that manifest to Solr (which will be an "in-progress" 
        //    operation
        // 5. see if any of the referenced objects have already arrived and 
        //    process them as if they had just arrived
    }

    private void processRecognizedFile() {
        // 1.  determine the PID represented by the file
        // 2.  determine if all of that object is present (especially 
        //     relevant for fedora objects)
        // 3.  query SOLR to determine if there's a manifest waiting for this
        //     object
        // 3a. if not, make a note of this object's id somehwere so that it 
        //     may be easily queried when the next manifest arrives
        // 3b. if so, process this object and update the "ingest" record as
        //     well as the "package" and "object" records
        // 4.  if any sort of error occurs while processing the file, update
        //     the solr "ingest" record for the manifest to indicate the failure
        //     (also this should be noted somewhere in the provenance record
        //     for safekeeping)
    }
}
