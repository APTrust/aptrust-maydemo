package org.aptrust.ingest.fedora;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.aptrust.common.metadata.APTrustMetadata;
/**
 * A simple class to mirror the structure of the xml/rdf serialization of the
 * fedora RELS-EXT datastream for objects that contain AP Trust metadata.  This
 * class has JAXB bindings to allow it to be used to unmarshall the RELS-EXT 
 * datastream to extract the APTrust metadata.  This class it not complete 
 * enough to be used to marshall that XML.
 */
@XmlRootElement(namespace="http://www.w3.org/1999/02/22-rdf-syntax-ns#", name="RDF")
public class APTrustRelsExt {

    private Description description;

    @XmlElement(namespace="http://www.w3.org/1999/02/22-rdf-syntax-ns#", name="Description")
    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    private static class Description extends APTrustMetadata {
        @XmlAttribute(namespace="http://www.w3.org/1999/02/22-rdf-syntax-ns#", name="about")
        public String objectUri;
    }
}
