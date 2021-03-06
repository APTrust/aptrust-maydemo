package org.aptrust.ingest.api;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.aptrust.common.metadata.APTrustMetadata;

/**
 * An class for which instances define a packages of digital objects and
 * metadata for submission into AP Trust.
 */
@XmlRootElement(name="package")
public class IngestPackage {
 
    private APTrustMetadata metadata;

    private DigitalObject[] objects;

    /**
     * Gets the AP Trust required submission metadata that should be applied
     * to all of the digital objects in this package.
     */
    @XmlElement
    public APTrustMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(APTrustMetadata m) {
        metadata = m;
    }

    /**
     * Gets a list of DigitalObject objects that represent materials for
     * inclusion in AP Trust.
     */
    @XmlElementWrapper(name="digitalObjects")
    @XmlElement(name="digitalObject")
    public DigitalObject[] getDigitalObjects() {
        return objects;
    }

    public void setDigitalObjects(DigitalObject[] o) {
        objects = o;
    }
}
