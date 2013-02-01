package org.aptrust.ingest.api;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.aptrust.common.metadata.APTrustMetadata;

/**
 * An object that contains the the data describing an ingest operation with
 * methods to serialize it into XML for transmission.
 *
 */
public class IngestManifest {
    
    private String label;

    private List<IngestPackage> submit;

    /**
     * Instantiates a simple manifest with the given label and list of packages
     * to submit.
     * @param label the label to be displayed for this ingest operation.
     * @param submit a list of packages to submit
     */
    public IngestManifest(String label, List<IngestPackage> submit) {
        this.label = label;
        this.submit = submit;
    }
    
    public String getLabel() {
        return label;
    }

    public List<IngestPackage> listPackagesToSubmit() {
        return submit;
    }

    /**
     * Use of the manifest to remove/delete pacakges is not yet supported.
     */
    public List<String> listPackageIdsToRemove() {
        return Collections.emptyList();
    }

    /**
     * Writes out and XML serialization to the provided OutputStream.
     * @param os the OutputStream to which the XML will be written
     * @param username the name of the user whose initiating the ingest 
     * described by this manifest.
     * @throws XMLStreamException if an error occurs while generating the XML
     */
    public void writeOutXML(OutputStream os, String username) throws XMLStreamException {
        XMLStreamWriter w = XMLOutputFactory.newInstance().createXMLStreamWriter(os);
        w.writeStartDocument("UTF-8", "1.0");
        w.writeCharacters("\n");
        w.writeStartElement("manifest");
        w.writeStartElement("description");
        w.writeStartElement("name");
        w.writeCharacters(label);
        w.writeEndElement();
        w.writeStartElement("suppliedUsername");
        w.writeCharacters(username);
        w.writeEndElement();
        w.writeStartElement("ingestInitiated");
        w.writeCharacters(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.sss").format(new Date()));
        w.writeEndElement();
        w.writeEndElement();
        for (IngestPackage p : submit) {
            APTrustMetadata m = p.getMetadata();
            w.writeStartElement("package");
            w.writeStartElement("metadata");
            w.writeStartElement("title");
            w.writeCharacters(m.getTitle());
            w.writeEndElement();
            w.writeStartElement("DPNBound");
            w.writeCharacters(m.isDPNBound() ? "true" : "false");
            w.writeEndElement();
            w.writeStartElement("accessControl");
            w.writeCharacters(m.getAccessConditions());
            w.writeEndElement();
            w.writeEndElement();
            w.writeStartElement("digitalObjects");
            for (DigitalObject o : p.getDigitalObjects()) {
                w.writeStartElement("digitalObject");
                w.writeAttribute("type", o.getType().name());
                w.writeAttribute("version", o.getVersion());
                w.writeStartElement("pid");
                w.writeCharacters(o.getId());
                w.writeEndElement();
                w.writeStartElement("approximateSize");
                w.writeCharacters(String.valueOf(o.getApproximateSize()));
                w.writeEndElement();
                w.writeEndElement();
            }
            w.writeEndElement();
        }
        w.writeEndElement();
        w.writeEndDocument();
        w.flush();
    }

}
