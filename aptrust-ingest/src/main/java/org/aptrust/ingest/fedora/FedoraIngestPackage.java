package org.aptrust.ingest.fedora;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.aptrust.common.fedora.APTrustFedoraConstants;
import org.aptrust.ingest.api.DigitalObject;
import org.aptrust.ingest.api.IngestPackage;

import com.yourmediashelf.fedora.client.FedoraClient;
import com.yourmediashelf.fedora.client.FedoraClientException;
import com.yourmediashelf.fedora.client.response.RiSearchResponse;

/**
 * An IngestPackage that exists in a local fedora repository.  This class just
 * exposes access to that object in Fedora.  As such, the methods may return
 * different results in subsequent calls and may take a while to return.
 */
public class FedoraIngestPackage extends IngestPackage {

    private FedoraClient fc;

    private String pid;

    private String version;

    /**
     * Fetches and parses the AP Trust metadata for the package object this
     * object represents and then queries fedora to determine all of the 
     * objects that make up the ingest package.
     * @throws JAXBException 
     * @throws FedoraClientException 
     */
    public FedoraIngestPackage(FedoraClient client, String packagePid, String fedoraVersion) throws JAXBException, FedoraClientException {
        fc = client;
        pid = packagePid;
        version = fedoraVersion;
        
        // Fetch and parse the metadata
        JAXBContext jc = JAXBContext.newInstance(APTrustRelsExt.class);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        APTrustRelsExt relsExt = (APTrustRelsExt) unmarshaller.unmarshal(FedoraClient.getDatastreamDissemination(pid, "RELS-EXT").execute(fc).getEntityInputStream());
        setMetadata(relsExt.getDescription());

        // query the digital objects
        List<DigitalObject> objects = new ArrayList<DigitalObject>();
        try {
            String packageMembersItqlQuery = "select $member from <#ri> "
                    + "where <info:fedora/" + pid + "> <" + APTrustFedoraConstants.INCLUDES_RESOURCE_PREDICATE + "> $member";
            RiSearchResponse response = FedoraClient.riSearch(packageMembersItqlQuery).lang("itql").format("csv").execute(fc);
            BufferedReader r = new BufferedReader(new InputStreamReader(response.getEntityInputStream()));
            try {
                r.readLine(); // this gets the "member" line
                String objectUri = null;
                while ((objectUri = r.readLine()) != null) {
                    String pid = objectUri.substring("info:fedora/".length());
                    objects.add(new DigitalObject(pid, DigitalObject.Type.FEDORA, version, -1));
                }
            } finally {
                r.close();
            }
        } catch (FedoraClientException ex) {
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        setDigitalObjects(objects.toArray(new DigitalObject[0]));
    }
}
