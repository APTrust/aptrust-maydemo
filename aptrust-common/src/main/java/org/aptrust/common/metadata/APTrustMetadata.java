package org.aptrust.common.metadata;


/**
 * A data structure to encapsulate the metadata for materials participating in
 * the AP Trust repository.
 */
public class APTrustMetadata {

    private String id;

    private String institution;

    private String title;

    private String accessConditions;

    private boolean dpnBound;

    public APTrustMetadata(String id, String institution, String title, String accessConditions, boolean dpnBound) {
        this.id = id;
        this.institution = institution;
        this.title = title;
        if (accessConditions.equals("world") || accessConditions.equals("institution") || accessConditions.equals("restricted")) {
            this.accessConditions = accessConditions;
        } else {
            throw new IllegalArgumentException("access conditions must be \"world\", \"institution\" or \"restricted\", not \"" + accessConditions + "\". (" + id + ")");
        }

        this.dpnBound = dpnBound;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getInstitution() {
        return institution;
    }

    public String getAccessConditions() {
        return accessConditions;
    }

    public boolean isDPNBound() {
        return dpnBound;
    }

}
