package org.aptrust.ingest.fedora;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.aptrust.ingest.api.IngestManifest;
import org.aptrust.ingest.api.IngestPackage;

import com.yourmediashelf.fedora.client.FedoraClient;
import com.yourmediashelf.fedora.client.FedoraClientException;
import com.yourmediashelf.fedora.client.FedoraCredentials;
import com.yourmediashelf.fedora.client.response.RiSearchResponse;

/**
 * A class to expose access to content in a Fedora Repository.  This class has
 * methods to do more complex operations than the FedoraClient it wraps, like 
 * performing particular resource index searches and parsing the results.
 */
public class LocalFedoraRepository {

    private FedoraClient fc;

    public LocalFedoraRepository(String url, String username, String password) throws MalformedURLException {
        fc = new FedoraClient(new FedoraCredentials(url, username, password));
    }
    
    public LocalFedoraRepository(FedoraClient fc) {
        this.fc = fc;
    }

    /**
     * Makes resource index queries against the fedora repository to build a 
     * manifest that contains all of the packages that are explicitly defined
     * using the aptrust:package content model.
     * @return an IngestManifest of all the materials in the repository.
     * @throws FedoraClientException 
     * @throws IOException 
     */
    public IngestManifest generateManifest(String label) throws FedoraClientException, IOException {
        // get the fedora version
        String version = FedoraClient.describeRepository().execute(fc).getRepositoryVersion();
        
        // identify packages
        List<IngestPackage> packages = new ArrayList<IngestPackage>();
        String packageItqlQuery = "select $object from <#ri> " 
                + "where $object <info:fedora/fedora-system:def/model#hasModel>"
                + " <info:fedora/aptrust:package>";
        RiSearchResponse response = FedoraClient.riSearch(packageItqlQuery).lang("itql").format("csv").execute(fc);
        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntityInputStream()));
        try {
            reader.readLine(); // this gets the "object" line
            String packageObjectUri = null;
            while ((packageObjectUri = reader.readLine()) != null) {
                String packagePid = packageObjectUri.substring("info:fedora/".length());
                packages.add(new FedoraIngestPackage(fc, packagePid, version));
            }
        } finally {
            reader.close();
        }
        return new IngestManifest(label, packages);
    }

}
