package org.aptrust.common.metadata;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;


/**
 * A data structure to encapsulate the metadata for materials participating in
 * the AP Trust repository.
 */
public class APTrustMetadata {

    protected String id;

    protected String institution;

    protected String title;

    protected String accessConditions;

    protected boolean dpnBound;

    @XmlAttribute(name="about", namespace="http://www.w3.org/1999/02/22-rdf-syntax-ns#", required=true)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @XmlElement(name="title", namespace="http://www.aptrust.org/relationships#", required=true)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @XmlElement(name="hasOwningInstitution", namespace="http://www.aptrust.org/relationships#", required=true)
    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    @XmlElement(name="hasAccessControlPolicy", namespace="http://www.aptrust.org/relationships#", required=true)
    public String getAccessConditions() {
        return accessConditions;
    }

    public void setAccessConditions(String accessConditions) {
        if (accessConditions.equals("world") || accessConditions.equals("institution") || accessConditions.equals("restricted")) {
            this.accessConditions = accessConditions;
        } else {
            throw new IllegalArgumentException("access conditions must be \"world\", \"institution\" or \"restricted\", not \"" + accessConditions + "\". (" + id + ")");
        }
    }

    @XmlElement(name="isDPNBound", namespace="http://www.aptrust.org/relationships#", required=true)
    public boolean isDPNBound() {
        return dpnBound;
    }

    public void setDPNBound(boolean dpnBound) {
        this.dpnBound = dpnBound;
    }
}
