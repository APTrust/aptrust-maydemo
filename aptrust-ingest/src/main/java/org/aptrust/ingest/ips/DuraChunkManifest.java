package org.aptrust.ingest.ips;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Minimal datastructure with XML bindings to parse the DuraClound chunking
 * manifest file.
 */
@XmlRootElement(name="chunksManifest", namespace="duracloud.org")
public class DuraChunkManifest {

    @XmlElement(name="header")
    public Header header;

    @XmlElementWrapper(name="chunks")
    @XmlElement(name="chunk")
    public Chunk[] chunks;

    @XmlRootElement(name="header")
    public static final class Header {

        @XmlAttribute(name="schemaVersion")
        public String schemaVersion;

        @XmlElement(name="sourceContent")
        public SourceContent sourceContent;
        
    }

    @XmlRootElement(name="sourceContent")
    public static final class SourceContent {
        @XmlAttribute(name="contentId")
        public String contentId;

        @XmlElement(name="mimetype")
        public String mimetype;

        @XmlElement(name="byteSize")
        public String byteSize;

        @XmlElement(name="md5")
        public String md5;
    }

    @XmlRootElement(name="chunk")
    public static final class Chunk {
        @XmlAttribute(name="chunkId")
        String chunkId;

        @XmlAttribute(name="index")
        int index;

        @XmlAttribute(name="byteSize")
        long byteSize;

        @XmlAttribute(name="md5")
        String md5;
    }
}
