package org.aptrust.ingest.api;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * A class that can be used to specify a Digital Object from either Fedora
 * or DSpace in an ingest package.
 */
@XmlRootElement(name="digitalObject")
public class DigitalObject {

    public enum Type {
        FEDORA,
        DSPACE;
    }

    private String id;

    private Type type;

    private String version;

    private long approximateSize;
    
    public DigitalObject() {
    }

    public DigitalObject(String id, Type type, String version, long approximateSize) {
        this.id = id;
        this.type = type;
        this.version = version;
        this.approximateSize = approximateSize;
    }

    @XmlElement(name="pid")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @XmlAttribute
    public Type getType() {
        return type;
    }

    public void setType(Type t) {
        type = t;
    }

    @XmlAttribute
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @XmlElement(name="size")
    public long getApproximateSize() {
        return approximateSize;
    }
    
    public void setApproximateSize(long size) {
        approximateSize = size;
    }
}
