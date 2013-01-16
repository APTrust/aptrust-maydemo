package org.aptrust.client.utilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import com.yourmediashelf.fedora.client.FedoraClient;
import com.yourmediashelf.fedora.client.FedoraClientException;
import com.yourmediashelf.fedora.client.FedoraCredentials;

/**
 * A command line utlity to validate or prepare a fedora repository for use as
 * an AP Trust aggregation repository.
 * 
 * The current implementation only involves the presence of the 
 * aptrust:metadata content model and supporting service definitions and
 * deployments.
 */
public class FedoraPreparer {

    public static void main(String [] args) throws MalformedURLException, FedoraClientException, URISyntaxException {
        if (args.length < 3) {
            System.out.println("Usage: FedoraPreparer [fedora-url] [username] [password]");
            return;
        }

        FedoraClient fc = new FedoraClient(new FedoraCredentials(args[0], args[1], args[2]));

        FedoraPreparer f = new FedoraPreparer(fc);
        f.prepare();
    }

    private FedoraClient fc;

    public FedoraPreparer(FedoraClient fc) {
        this.fc = fc;
    }

    public void prepare() throws FedoraClientException, URISyntaxException {
        if (!doesPidExist("aptrust:package")) {
            ingestResource("aptrust_package.xml");
            System.out.println("Ingested aptrust:package.");
        } else {
            System.out.println("Found aptrust:package.");
        }
        
        if (!doesPidExist("aptrust:indexableSDef")) {
            ingestResource("aptrust_indexableSDef.xml");
            System.out.println("Ingested aptrust:indexableSDef.");
        } else {
            System.out.println("Found aptrust:indexableSDef.");
        }
        if (!doesPidExist("aptrust:indexableSDep")) {
            ingestResource("aptrust_indexableSDep.xml");
            System.out.println("Ingested aptrust:indexableSDep.");
        } else {
            System.out.println("Found aptrust:indexableSDep.");
        }
    }

    private boolean doesPidExist(String pid) throws FedoraClientException {
        try {
            FedoraClient.getObjectProfile(pid).execute(fc);
            return true;
        } catch (FedoraClientException ex) {
            if (ex.getMessage().contains("404")) {
                return false;
            } else {
                throw ex;
            }
        }
    }
    
    private void ingestResource(String path) throws FedoraClientException {
        URL resource = getClass().getClassLoader().getResource(path);
        try {
            File file = new File(resource.toURI());
            FedoraClient.ingest().content(file).execute(fc);
        } catch (URISyntaxException ex) {
            // shouldn't happen
            throw new RuntimeException(ex);
        }
    }
}
