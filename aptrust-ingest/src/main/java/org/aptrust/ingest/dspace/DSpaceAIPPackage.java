package org.aptrust.ingest.dspace;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Collections;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.io.IOUtils;
import org.aptrust.common.exception.AptrustException;
import org.w3c.dom.Document;

public class DSpaceAIPPackage {
    
    public static void main(String [] args) throws AptrustException {
        System.out.println(new DSpaceAIPPackage(new File("/home/md5wz/Documents/projects/APTrust/DSPACE AIP/aip_example_1.zip")));
    }

    private String dspaceVersion;

    private String id;

    private String title;

    public DSpaceAIPPackage(File file) throws AptrustException {
        try {
            ZipFile z = new ZipFile(file, ZipFile.OPEN_READ);
            ZipEntry metsEntry = z.getEntry("mets.xml");
            DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
            f.setNamespaceAware(true);
            DocumentBuilder parser = f.newDocumentBuilder();
            Document mets = parser.parse(z.getInputStream(metsEntry));

            XPath xpath = getMetsAwareXPath();

            setId((String) xpath.evaluate("/mets:mets/@OBJID", mets, XPathConstants.STRING));
            setTitle((String) xpath.evaluate("/mets:mets/mets:dmdSec/mets:mdWrap[@MDTYPE='MODS']/mets:xmlData/mods:mods/mods:titleInfo/mods:title", mets, XPathConstants.STRING));
            setDspaceVersion(((String) xpath.evaluate("/mets:mets/mets:metsHdr/mets:agent[@OTHERTYPE='DSpace Software']/mets:name", mets, XPathConstants.STRING)).replace("DSpace ", ""));
        } catch (Exception ex) {
            throw new AptrustException(ex);
        }
    }

    public DSpaceAIPPackage(InputStream is) throws AptrustException {
        try {
            ZipInputStream zis = new ZipInputStream(is);
            ZipEntry z = null;
            while ((z = zis.getNextEntry()) != null) {
                if (z.getName().equals("mets.xml")) {
                    ZipEntry metsEntry = z;

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    IOUtils.copy(zis, baos);

                    DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
                    f.setNamespaceAware(true);
                    DocumentBuilder parser = f.newDocumentBuilder();
                    Document mets = parser.parse(new ByteArrayInputStream(baos.toByteArray()));

                    XPath xpath = getMetsAwareXPath();
                    setId((String) xpath.evaluate("/mets:mets/@OBJID", mets, XPathConstants.STRING));
                    setTitle((String) xpath.evaluate("/mets:mets/mets:dmdSec/mets:mdWrap[@MDTYPE='MODS']/mets:xmlData/mods:mods/mods:titleInfo/mods:title", mets, XPathConstants.STRING));
                    setDspaceVersion(((String) xpath.evaluate("/mets:mets/mets:metsHdr/mets:agent[@OTHERTYPE='DSpace Software']/mets:name", mets, XPathConstants.STRING)).replace("DSpace ", ""));
                    return;
                }
            }
        } catch (Exception ex) {
            throw new AptrustException(ex);
        }
    }

    public String toString() {
        return "DSPace version " + getDspaceVersion() + " package " + getId() + " \"" + getTitle() + "\"";
    }

    public String getDspaceVersion() {
        return dspaceVersion;
    }

    public void setDspaceVersion(String dspaceVersion) {
        this.dspaceVersion = dspaceVersion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets and XPath implementation that includes the "mets" and "mods" 
     * namespace mappings.
     */
    private XPath getMetsAwareXPath() {
        XPath xpath = XPathFactory.newInstance().newXPath();
        xpath.setNamespaceContext(new NamespaceContext() {

            String metsNS = "http://www.loc.gov/METS/";
            String modsNS = "http://www.loc.gov/mods/v3";
            
            public String getNamespaceURI(String prefix) {
                if (prefix.equals("mets")) {
                    return metsNS;
                } else if (prefix.equals("mods")) {
                    return modsNS;
                } else {
                    return null;
                }
            }

            public String getPrefix(String namespaceURI) {
                if (namespaceURI.equals(metsNS)) {
                    return "mets";
                } else if (namespaceURI.equals(modsNS)) {
                    return "mods";
                } else {
                    return null;
                }
            }

            public Iterator getPrefixes(String namespaceURI) {
                return Collections.singleton(getPrefix(namespaceURI)).iterator();
            }});
        return xpath;
    }
}
