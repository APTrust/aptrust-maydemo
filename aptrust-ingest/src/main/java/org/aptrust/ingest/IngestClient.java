package org.aptrust.ingest;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.aptrust.common.metadata.APTrustMetadata;
import org.aptrust.ingest.api.DigitalObject;
import org.aptrust.ingest.api.IngestClientConfiguration;
import org.aptrust.ingest.api.IngestManifest;
import org.aptrust.ingest.api.IngestPackage;
import org.aptrust.ingest.fedora.LocalFedoraRepository;
import org.aptrust.ingest.impl.PropertiesIngestClientConfiguration;
import org.duracloud.client.ContentStore;
import org.duracloud.client.ContentStoreImpl;
import org.duracloud.common.model.Credential;
import org.duracloud.common.web.RestHttpHelper;
import org.duracloud.common.web.RestHttpHelper.HttpResponse;
import org.duracloud.error.ContentStoreException;
import org.duracloud.storage.domain.StorageProviderType;
import org.w3c.dom.Document;

import com.yourmediashelf.fedora.client.FedoraClientException;

/**
 * This is a command line program that allows users to initiate ingest 
 * operations into AP Trust.
 */
public class IngestClient {

    /**
     * There are several ways to ingest materials from several sources.
     * 1.  Ingest prepared content from a Fedora repository
     * 2.  Ingest dspace packages described in a spreadsheet/csv
     * 3.  others, eventually
     */
    public static void main(String args[]) {
        // parse the arguments
        CommandLineArguments a = new CommandLineArguments(args);
        if (!a.isValid()) {
            System.out.println(a.getArgumentUsage());
            System.exit(-1);
        } else {
            Properties p = new Properties();
            try {
                p.load(new FileInputStream((a.getConfigurationFile() != null ? a.getConfigurationFile() : new File("ingest-client-config.properties"))));
            } catch (FileNotFoundException ex) {
                System.err.println("Unable to locate configuration file: " + ex.getMessage());
            } catch (IOException ex) {
                System.err.println("Error reading configuration file: " + ex.getMessage());
            }
            IngestClientConfiguration c = new PropertiesIngestClientConfiguration(p);

            if (a.isPrepackagedFedora()) {
                System.out.println("Beginning ingest of packages defined in fedora." + (a.isDryRun() ? " (dry-run)" : ""));
                
                try {
                    LocalFedoraRepository r = new LocalFedoraRepository(a.getFedoraUrl(), a.getFedoraUsername(), a.getFedoraPassword());
                    IngestManifest m = r.generateManifest(a.getOperationName(), c.getDuraCloudUsername());
                    System.out.println("  " + m.getPackagesToSubmit().length + " packages detected.");
                    
                    IngestClient client = new IngestClient(c, a);
                    System.out.println("  initiating ingest");
                    client.ingest(m);
                } catch (MalformedURLException ex) {
                    System.err.println("Invalid fedora repository URL: " + a.getFedoraUrl());
                    System.exit(-1);
                } catch (FedoraClientException ex) {
                    System.err.println("Exception while querying for packages in local fedora repository.");
                    ex.printStackTrace();
                    System.exit(-1);
                } catch (IOException ex) {
                    System.err.println("IOException while accessing content in local fedora repository.");
                    System.exit(-1);
                } catch (ContentStoreException ex) {
                    System.err.println("Unable to transfer manifest!");
                    ex.printStackTrace();
                    System.exit(-1);
                } catch (Exception ex) {
                    System.err.println("Exception!");
                    ex.printStackTrace();
                    System.exit(-1);
                }
            
            } else {
                System.out.println("Unsupported ingest type.");
            }
        }
    }

    private IngestClientConfiguration configuration;

    private CommandLineArguments arguments;
    
    public IngestClient(IngestClientConfiguration c, CommandLineArguments a) {
        configuration = c;
        arguments = a;
    }

    /**
     * Gets a file handle to which a local manifest could be written.  This
     * consults the configuration to either create a temporary file or to place
     * the file in a configured location.
     */
    private File getLocalManifestFile(String id) throws IOException {
        if (configuration.getOutputDir() != null) {
            File outputDir = new File(configuration.getOutputDir());
            if (!outputDir.exists()) {
                outputDir.mkdirs();
            }
            return new File(outputDir, "manifest-" + id + ".xml");
        } else {
            File file = File.createTempFile("manifest-" + id, ".xml");
            file.deleteOnExit();
            return file;
        }
    }

    /**
     * Kicks off the process of ingesting the materials described in the 
     * supplied manifest.  The current implementation of this class only 
     * supports ingest from Fedora repositories.
     * @param m a manifest of materials for ingest/update/removal
     * @throws Exception 
     */
    public void ingest(IngestManifest m) throws Exception {
        // TODO: add some code to ensure that two operations aren't running 
        // at once.
        validateOperation(m);
        m.setId(transferManifest(m));
        queueDataTransfer(m);
    }

    /**
     * Validates a manifest.
     * 
     * The current implementation is just a placeholder that performs no 
     * validation.
     * @throws InvalidManifestException if the manifest describes an operation
     * that is valid and complete.
     */
    private void validateOperation(IngestManifest m) {
    }

    /**
     * Initiates a new ingest operation by transferring the manifest to the 
     * AP Trust staging area for the given institution.  This method will fail
     * if any error occurs during transfer.  Upon successful completion of the
     * transfer, this method returns the identifier assigned to this ingest 
     * operation. 
     * @return the identifier for the ingest process.  Right now, this is only
     * useful for identifying the manifest file on the DuraCloud storage.
     * Eventually it may be able to be used to query progress using this client
     * or to cancel an incomplete operation.  This identifier is only unique
     * for the duration of the ingest operation.  Once complete, the provenance
     * record may include the original manifest.
     * @throws ContentStoreException if an error occurs while storing the
     * manifest file
     * @throws IOException if any error occurs while reading/writing data
     * @throws JAXBException if the manifest serialization fails
     * @throws JAXBException 
     */
    private String  transferManifest(IngestManifest m) throws ContentStoreException, IOException, JAXBException {
        // Step one, generate an ID
        String id = getRandomString(10);
        String contentId = "ingest-manifest-" + id + ".xml";
        
        // Step two, generate the manifest and compute its size and hash
        File manifestFile = getLocalManifestFile(contentId);
        HashOutputStream hos = new HashOutputStream(new FileOutputStream(manifestFile));
        JAXBContext jc = JAXBContext.newInstance(IngestManifest.class, IngestPackage.class, DigitalObject.class, APTrustMetadata.class);
        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty("jaxb.formatted.output", Boolean.TRUE);
        marshaller.marshal(m, hos);
        hos.close();
        
        // Step two, transfer the manifest
        if (!arguments.isDryRun()) {
            InputStream is = new FileInputStream(manifestFile);
            try {
                ContentStore cs = new ContentStoreImpl(configuration.getDuraCloudUrl(), StorageProviderType.valueOf(configuration.getDuraCloudProviderName()), configuration.getDuraCloudProviderId(), new RestHttpHelper(new Credential(configuration.getDuraCloudUsername(), configuration.getDuraCloudPassword())));
                // TODO: ensure uniqueness of content id, or at least prevent overwrites
                Map<String, String> properties = new HashMap<String, String>();
                properties.put("tags", "aptrust_manifest");
                cs.addContent(configuration.getDuraCloudSpaceId(), contentId, is, manifestFile.length(), "text/xml", hos.getMD5Hash(), properties);
            } finally {
                is.close();
            }
        } else {
            System.out.println("Skipping manifest transfer (dry-run).");
        }

        // Step three, return the ID
        return id;
    }

    /**
     * Inovkes the REST API on a configured copy of Fedora CloudSync to copy
     * the content specified in the Manifest.  Because that copy of CloudSync
     * may be used for other operations, this method attempts to use it in a
     * friendly and clear manner.  It does this by using well-known or
     * reproducable names for the object stores and tasks.  The object stores
     * are given the name "APTRUST - " followed by a hash of the defining 
     * characteristics.  Therefore, subsequent runs will not require the 
     * creation of duplicate object stores.  The object set and taks are named
     * after the Manifest id.  So if subsequent attempts are made to ingest a 
     * manifest, no task or object set records will be created in CloudSync.
     */
    private void queueDataTransfer(IngestManifest m) throws Exception {
        if (m.getId() == null) {
            throw new IllegalStateException("Manifeset must have an id before it may be transferred.");
        }
        if (!arguments.isDryRun()) {
            RestHttpHelper rest = new RestHttpHelper(new Credential(configuration.getCloudSyncUsername(), configuration.getCloudSyncPassword()));
            DocumentBuilder parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            XPath xpath = XPathFactory.newInstance().newXPath();
    
            // Determine if there's an Object Set defined for this ingest manifest
            // based on the id, and creates one if not
            String name = "APTRUST - " + m.getId();
            String url = configuration.getCloudSyncURL() + "objectSets.xml";
            HttpResponse response = rest.get(url);
            Document setsDoc = parser.parse(response.getResponseStream());
            String setUri = (String) xpath.evaluate("objectSets/objectSet[name='" + name + "']/uri", setsDoc);
            if ("".equals(setUri)) {
                StringBuffer pidList = new StringBuffer();
                for (IngestPackage p : m.getPackagesToSubmit()) {
                    for (DigitalObject o : p.getDigitalObjects()) {
                        if (o.getType().equals(DigitalObject.Type.FEDORA)) { 
                            if (pidList.length() > 0) {
                                pidList.append("\\n");
                            }
                            pidList.append(o.getId());
                        }
                    }
                }
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/vnd.fcrepo-cloudsync.objectset+json");
                headers.put("Content-Type", "application/vnd.fcrepo-cloudsync.objectset+json; charset=UTF-8");
                String json = "{\"objectSet\":{\"name\":\"" + name + "\",\"type\":\"pidList\",\"data\":\"" + pidList.toString() + "\"}}";
                HttpResponse r = rest.post(configuration.getCloudSyncURL() + "objectSets.xml", json, headers);
                if (r.getStatusCode() / 100 != 2) {
                    throw new RuntimeException("HTTP Status code " + r.getStatusCode() + " returned from POST to " + configuration.getCloudSyncURL() + "objectSets.xml (" + json + ")");
                }
                setUri = xpath.evaluate("objectSet/uri", parser.parse(r.getResponseStream()));
            }
    
            // Determine if there's an object store defined for the local fedora
            // repository and creates one if there is not.
            name = "APTRUST (local fedora)- " + HashOutputStream.getMD5Hash(arguments.getFedoraUrl() + arguments.getFedoraUsername() + arguments.getFedoraPassword());
            url = configuration.getCloudSyncURL() + "objectStores.xml";
            response = rest.get(url);
            Document storesDoc = parser.parse(response.getResponseStream());
            String fedoraStoreUri = (String) xpath.evaluate("objectStores/objectStore[name='" + name + "']/uri", storesDoc);
            if ("".equals(fedoraStoreUri)) {
                String fedoraObjectStoreJSON = "{\"objectStore\":{\"name\":\"" + name + "\",\"type\":\"fedora\",\"data\":\"{\\\"url\\\":\\\"" + arguments.getFedoraUrl() + "\\\",\\\"username\\\":\\\"" + arguments.getFedoraUsername() + "\\\",\\\"password\\\":\\\"" + arguments.getFedoraPassword() + "\\\"}\"}}";
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/vnd.fcrepo-cloudsync.objectstore+json");
                headers.put("Content-Type", "application/vnd.fcrepo-cloudsync.objectstore+json; charset=UTF-8");
                HttpResponse r = rest.post(configuration.getCloudSyncURL() + "objectStores.xml", fedoraObjectStoreJSON, headers);
                if (r.getStatusCode() / 100 != 2) {
                    throw new RuntimeException("HTTP Status code " + r.getStatusCode() + " returned from POST to " + configuration.getCloudSyncURL() + "objectStores.xml (" + fedoraObjectStoreJSON + ")");
                }
                fedoraStoreUri = xpath.evaluate("objectStore/uri", parser.parse(r.getResponseStream()));
            }
    
            // Determine if there's an object store defined for the staging area 
            // and creates one if there is not
            name = "APTRUST (staging)- " + HashOutputStream.getMD5Hash(configuration.getDuraCloudUrl() + configuration.getDuraCloudSpaceId() + configuration.getDuraCloudProviderId() + configuration.getDuraCloudUsername() + configuration.getDuraCloudPassword());
            String stagingStoreUri = (String) xpath.evaluate("objectStores/objectStore[name='" + name + "']/uri", storesDoc);
            if ("".equals(stagingStoreUri)) {
                String fedoraObjectStoreJSON = "{\"objectStore\":{\"name\":\"" + name + "\",\"type\":\"duracloud\",\"data\":\"{\\\"url\\\":\\\"" + configuration.getDuraCloudUrl() + "\\\",\\\"username\\\":\\\"" + configuration.getDuraCloudUsername() + "\\\",\\\"password\\\":\\\"" + configuration.getDuraCloudPassword() + "\\\",\\\"providerId\\\":\\\"" + configuration.getDuraCloudProviderId() + "\\\",\\\"providerName\\\":\\\"" + configuration.getDuraCloudProviderName() + "\\\",\\\"space\\\":\\\"" + configuration.getDuraCloudSpaceId() + "\\\",\\\"prefix\\\":\\\"\\\"}\"}}";
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/vnd.fcrepo-cloudsync.objectstore+json");
                headers.put("Content-Type", "application/vnd.fcrepo-cloudsync.objectstore+json; charset=UTF-8");
                HttpResponse r = rest.post(configuration.getCloudSyncURL() + "objectStores.xml", fedoraObjectStoreJSON, headers);
                if (r.getStatusCode() / 100 != 2) {
                    throw new RuntimeException("HTTP Status code " + r.getStatusCode() + " returned from POST to " + configuration.getCloudSyncURL() + "objectStores.xml (" + fedoraObjectStoreJSON + ")");
                }
                stagingStoreUri = xpath.evaluate("objectStore/uri", parser.parse(r.getResponseStream()));
            }
    
            // Determines if there's a task resource defined for this ingest 
            // manifest based on the id, and creates one if not.
            name = "APTRUST - " + m.getId();
            url = configuration.getCloudSyncURL() + "tasks.xml";
            response = rest.get(url);
            Document tasksDoc = parser.parse(response.getResponseStream());
            String taskUri = (String) xpath.evaluate("tasks/task[name='" + name + "']/uri", tasksDoc);
            if ("".equals(taskUri)) {
                String fedoraTaskJSON = "{\"task\":{\"name\":\"" + name + "\",\"type\":\"copy\",\"state\":\"Idle\",\"data\":\"{\\\"setUri\\\":\\\"" + setUri.replace(".xml", "") + "\\\",\\\"queryStoreUri\\\":\\\"" + fedoraStoreUri.replace(".xml", "") + "\\\",\\\"sourceStoreUri\\\":\\\"" + fedoraStoreUri.replace(".xml", "") + "\\\",\\\"destStoreUri\\\":\\\"" + stagingStoreUri.replace(".xml", "") + "\\\",\\\"overwrite\\\":\\\"false\\\",\\\"includeManaged\\\":\\\"true\\\",\\\"copyExternal\\\":\\\"true\\\",\\\"copyRedirect\\\":\\\"true\\\"}\"}}";
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/vnd.fcrepo-cloudsync.task+json");
                headers.put("Content-Type", "application/vnd.fcrepo-cloudsync.task+json; charset=UTF-8");
                HttpResponse r = rest.post(configuration.getCloudSyncURL() + "tasks.xml", fedoraTaskJSON, headers);
                if (r.getStatusCode() / 100 != 2) {
                    throw new RuntimeException("HTTP Status code " + r.getStatusCode() + " returned from POST to " + configuration.getCloudSyncURL() + "tasks.xml (" + fedoraTaskJSON + ")");
                }
                taskUri = xpath.evaluate("task/uri", parser.parse(r.getResponseStream()));
            }
    
            // Determine if the task is running, start it if not
            response = rest.get(taskUri);
            Document taskDoc = parser.parse(response.getResponseStream());
            String state = (String) xpath.evaluate("task/state", taskDoc);
            if ("Idle".equals(state)) {
                // Start the task
                String fedoraTaskJSON = "{\"task\":{\"name\":\"" + name + "\",\"type\":\"copy\",\"state\":\"Starting\",\"data\":\"{\\\"setUri\\\":\\\"" + setUri.replace(".xml", "") + "\\\",\\\"queryStoreUri\\\":\\\"" + fedoraStoreUri.replace(".xml", "") + "\\\",\\\"sourceStoreUri\\\":\\\"" + fedoraStoreUri.replace(".xml", "") + "\\\",\\\"destStoreUri\\\":\\\"" + stagingStoreUri.replace(".xml", "") + "\\\",\\\"overwrite\\\":\\\"false\\\",\\\"includeManaged\\\":\\\"true\\\",\\\"copyExternal\\\":\\\"true\\\",\\\"copyRedirect\\\":\\\"true\\\"}\"}}";
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/vnd.fcrepo-cloudsync.task+json");
                headers.put("Content-Type", "application/vnd.fcrepo-cloudsync.task+json; charset=UTF-8");
                //headers.put(" X-HTTP-Method-Override", "PATCH"); // this didn't seem to work...
                HttpResponse r = rest.post(taskUri.replace(".xml", "") + "?_method=PATCH", fedoraTaskJSON, headers);
                if (r.getStatusCode() / 100 != 2) {
                    throw new RuntimeException("HTTP Status code " + r.getStatusCode() + " returned from PATCH to " + taskUri.replace(".xml", "") + ". (" + fedoraTaskJSON + ")");
                }
                System.out.println("Initiated ingest of content...");
            } else {
                System.out.println("Ingest is already in progress... (" + state + ")");
            }
        } else {
            System.out.println("Skipping ingest (dry-run).");
        }
    }

    /**
     * A method to generate a random String that may be used as an ID.  
     * Uniqueness is extremely likely but should be tested for.
     * The current implementation returns a String of random uppercase 
     * alphanumeric characters equal to the specified length;
     */
    private String getRandomString(int length) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i ++) {
            int num = (int) Math.round(Math.random() * 35);
            if (num < 10) {
                sb.append((char)('0' + num));
            } else {
                sb.append((char)('A' + num - 10));
            }
        }
        return sb.toString();
    }
    
    /**
     * A class that encapsulates the parsing and specification for command line
     * arguments to the IngestClient program.
     */
    private static class CommandLineArguments { 

        private boolean valid;

        private String fedoraUrl;

        private String fedoraUsername;

        private String fedoraPassword;

        private String errorMessage;

        private String operationName = "Fedora Ingest Operation";

        private boolean dryRun;

        public CommandLineArguments(String [] originalArgs) {
            dryRun = false;
            List<String> args = processFlags(originalArgs);
            if (args.size() > 0 && args.get(0).equals("--fedora-packages") && args.size() == 4) {
                fedoraUrl = args.get(1);
                fedoraUsername = args.get(2);
                fedoraPassword = args.get(3);
                valid = true;
            } else {
                valid = false;
            }
        }

        /**
         * Walks through the arguments and pulls out any flags that may have
         * be included.  The current implementation only identifies "--dry-run"
         * and "--name".
         * @param args the command line arguments
         * @return an updated list of command line arguments that excludes any
         * flags that were identified/processed.
         */
        private List<String> processFlags(String [] args) {
            ArrayList<String> newArgs = new ArrayList<String>();
            boolean nextIsName = false;
            for (String arg : args) {
                if (nextIsName) {
                    operationName = arg;
                    nextIsName = false;
                } else if (arg.equals("--dry-run")) {
                    dryRun = true;
                } else if (arg.equals("--name")) {
                    nextIsName = true;
                } else {
                    newArgs.add(arg);
                }
            }
            return newArgs;
        }

        public boolean isDryRun() {
            return dryRun;
        }

        public boolean isValid() {
            return valid;
        }
        
        public boolean isPrepackagedFedora() {
            return fedoraUrl != null;
        }
        
        /**
         * Returns the provided fedora URL.  If the provided URL included a 
         * trailing slash, that slash is omitted by this response.  For example
         * The user-provided value of "http://localhost:8080/fedora/" would
         * return "http://localhost:8080/fedora".  This behavior is to allow 
         * for more robust handling of essentially equivalent user-provided
         * values.
         */
        public String getFedoraUrl() {
            if (fedoraUrl == null) {
                return null;
            }
            if (fedoraUrl.endsWith("/")) {
                return fedoraUrl.substring(0, fedoraUrl.length() - 1);
            } else {
                return fedoraUrl;
            }
        }

        public String getFedoraUsername() {
            return fedoraUsername;
        }

        public String getFedoraPassword() {
            return fedoraPassword;
        }

        public String getOperationName() {
            return operationName;
        }

        public String getArgumentUsage() {
            return " --fedora-packages [fedora-url] [fedora-username] [fedora-password]\n" 
                    + "  Optional Flags:\n    --dry-run (performs all local operations but writes nothing to remote systems)\n"
                    + "Currently this AP Trust ingest client only supports ingest of pacakged materials in a local fedora repository."
                    + (errorMessage != null ? "\n" + errorMessage : "");
        }

        /**
         * If the command line arguments included the specification of an alternate
         * configuration file, this method returns that file.  Otherwise, this 
         * method returns null and the configuration should be pulled from a 
         * default location.
         * @return
         */
        public File getConfigurationFile() {
            return null;
        }
    }
    
    /**
     * An OutputStream that computes a hash of the content passed through it.
     */
    public static class HashOutputStream extends OutputStream {

        private MessageDigest digest;
        
        private OutputStream pipe;
        
        public HashOutputStream(OutputStream os) {
            pipe = os;
            try {
                digest = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException ex) {
                // can't happen because MD5 is supported by all JVMs
                assert false;
            }            
        }
        
        public String getMD5Hash() {
            byte[] inn = digest.digest();
            byte ch = 0x00;
            int i = 0;
            String pseudo[] = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};
            StringBuffer out = new StringBuffer(inn.length * 2);
            while (i < inn.length) {
                ch = (byte) (inn[i] & 0xF0);
                ch = (byte) (ch >>> 4);
                ch = (byte) (ch & 0x0F);
                out.append(pseudo[ (int) ch]);
                ch = (byte) (inn[i] & 0x0F);
                out.append(pseudo[ (int) ch]);
                i++;
            }
            return new String(out);
        }
        
        public void write(int b) throws IOException {
            digest.update(new byte[] { (byte) b });
            pipe.write(b);
        }
        
        public void write(byte[] b, int off, int len) throws IOException {
            digest.update(b, off, len);
            pipe.write(b, off, len);
        }
        
        public void write(byte[] b) throws IOException {
            digest.update(b);
            pipe.write(b);
        }
        
        public static String getMD5Hash(String value) throws IOException {
            HashOutputStream os = new HashOutputStream(new ByteArrayOutputStream());
            os.write(value.getBytes("UTF-8"));
            os.close();
            return os.getMD5Hash();
        }
    }
    
}
