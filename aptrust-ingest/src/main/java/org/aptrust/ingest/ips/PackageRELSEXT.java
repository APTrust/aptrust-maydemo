package org.aptrust.ingest.ips;

import java.net.URISyntaxException;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.aptrust.common.fedora.APTrustFedoraConstants;
import org.aptrust.common.metadata.APTrustMetadata;

@XmlRootElement(namespace=APTrustFedoraConstants.RDF_URI_PREFIX, name="RDF")
public class PackageRELSEXT {

    private Description d;

    public void setDescription(Description description) {
        d = description;
    }
    
    @XmlElement(namespace=APTrustFedoraConstants.RDF_URI_PREFIX, name="Description")
    public Description getDescription() {
        return d;
    }

    public static class ResourceDesignator {
        @XmlAttribute(name="resource", namespace=APTrustFedoraConstants.RDF_URI_PREFIX)
        String resource;
        
        public ResourceDesignator(String s) {
            resource = s;
        }
    }

    @XmlRootElement(namespace=APTrustFedoraConstants.RDF_URI_PREFIX, name="Description")
    public static class Description extends APTrustMetadata {

        private ResourceDesignator[] contentModels;

        private ResourceDesignator[] includesResource;

        public Description() {
        }

        public Description(APTrustMetadata m) {
            try {
                setId(m.getId());
            } catch (URISyntaxException e) {
                //can't happen
                throw new RuntimeException(e);
            }
            setInstitution(m.getInstitution());
            setTitle(m.getTitle());
            setAccessConditions(m.getAccessConditions());
            setDPNBound(m.isDPNBound());
        }
        @XmlElement(name="hasModel", namespace=APTrustFedoraConstants.RDF_URI_PREFIX)
        ResourceDesignator[] getContentModelURIs() {
            return contentModels;
        }

        public void setContentModelURIs(ResourceDesignator[] uris) {
            contentModels = uris;
        }

        @XmlElement(name="includesResource", namespace=APTrustFedoraConstants.APTRUST_URI_PREFIX)
        ResourceDesignator[] getIncludedResourceURIs() {
            return includesResource;
        }

        public void setIncludedResourceURIs(ResourceDesignator[] uris) {
            includesResource = uris;
        }
    }

}
