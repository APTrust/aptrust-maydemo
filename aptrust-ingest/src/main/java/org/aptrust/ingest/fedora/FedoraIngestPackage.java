package org.aptrust.ingest.fedora;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.aptrust.common.fedora.APTrustFedoraConstants;
import org.aptrust.common.fedora.APTrustFedoraNamespaceContext;
import org.aptrust.common.metadata.APTrustMetadata;
import org.aptrust.ingest.api.DigitalObject;
import org.aptrust.ingest.api.IngestPackage;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.yourmediashelf.fedora.client.FedoraClient;
import com.yourmediashelf.fedora.client.FedoraClientException;
import com.yourmediashelf.fedora.client.response.RiSearchResponse;

/**
 * An IngestPackage that exists in a local fedora repository.  This class just
 * exposes access to that object in Fedora.  As such, the methods may return
 * different results in subsequent calls and may take a while to return.
 */
public class FedoraIngestPackage implements IngestPackage {

    private FedoraClient fc;

    private String pid;

    private APTrustMetadata m;
    
    private String version;

    public FedoraIngestPackage(FedoraClient client, String packagePid, String fedoraVersion) {
        fc = client;
        pid = packagePid;
        version = fedoraVersion;
    }

    /** 
     * Fetches and parses the AP Trust metadata for the package object this
     * object represents.  This is a very costly method and should only be
     * invoked when necessary and ideally only once.
     * @throws RuntimeException if an error occurs while fetching or parsing
     * the metadata.
     */
    public APTrustMetadata getMetadata() {
        try {
            DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
            f.setNamespaceAware(true);
            DocumentBuilder parser = f.newDocumentBuilder();
            Document relsExt = parser.parse(FedoraClient.getDatastreamDissemination(pid, "RELS-EXT").execute(fc).getEntityInputStream());
            XPath xpath = XPathFactory.newInstance().newXPath();
            xpath.setNamespaceContext(new APTrustFedoraNamespaceContext());
            return new APTrustMetadata(pid, 
                    xpath.evaluate("rdf:RDF/rdf:Description/aptrust:hasOwningInstitution", relsExt), 
                    xpath.evaluate("rdf:RDF/rdf:Description/aptrust:title", relsExt),
                    xpath.evaluate("rdf:RDF/rdf:Description/aptrust:hasAccessControlPolicy", relsExt),
                    xpath.evaluate("rdf:RDF/rdf:Description/aptrust:isDPNBound", relsExt).equals("true"));
        } catch (ParserConfigurationException ex) {
            throw new RuntimeException(ex);
        } catch (SAXException ex) {
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } catch (FedoraClientException ex) {
            throw new RuntimeException(ex);
        } catch (XPathExpressionException ex) {
            throw new RuntimeException(ex);
        }
    }

    /** 
     * Queries fedora to determine all of the objects that make up the ingest
     * package.  This is a very costly method and should only be
     * invoked when necessary and ideally no more than once.
     * @throws RuntimeException if an error occurs while making requests or 
     * parsing responses from Fedora.
     */
    public List<DigitalObject> getDigitalObjects() {
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
        return objects;
    }

}
