package org.aptrust.ingest.api;

/**
 * A class that can be used to specify a Digital Object from either Fedora
 * or DSpace in an ingest package.
 */
public class DigitalObject {

    public enum Type {
        FEDORA,
        DSPACE;
    }

    public String id;
 
    public Type type;

    public String version;
    
    public long approximateSize;
    
    public DigitalObject(String id, Type type, String version, long approximateSize) {
        this.id = id;
        this.type = type;
        this.version = version;
        this.approximateSize = approximateSize;
    }

    public String getId() {
        return id;
    }

    public Type getType() {
        return type;
    }

    public String getVersion() {
        return version;
    }

    public long getApproximateSize() {
        return approximateSize;
    }
}
