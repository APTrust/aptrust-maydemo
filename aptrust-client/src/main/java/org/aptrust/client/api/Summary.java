package org.aptrust.client.api;

/**
 *  Provides a basic summary of the ingested content for a single institution
 *  in AP Trust.
 */
public class Summary {

    private String institutionId;

    private Long packageCount;

    private Long objectCount;

    private Long bytesUsed;

    private Long dpnBoundPackageCount;

    private Long publicPackageCount;

    private Long institutionPackageCount;

    private Long privatePackageCount;

    private Long failedPackageCount;

    /**
     * Gets the identifier of the institution whose summary this object 
     * represents.
     */
    public String getInstitutionId() {
        return institutionId;
    }

    public void setInstitutionId(String institutionId) {
        this.institutionId = institutionId;
    }

    /**
     * Gets the total number of packages.
     */
    public Long getPackageCount() {
        return packageCount;
    }

    public void setPackageCount(Long packageCount) {
        this.packageCount = packageCount;
    }

    /**
     * Gets the total number of fedora objects.
     */
    public Long getObjectCount() {
        return objectCount;
    }

    public void setObjectCount(Long objectCount) {
        this.objectCount = objectCount;
    }

    /**
     * Gets the total number of bytes of storage used.
     */
    public Long getBytesUsed() {
        return bytesUsed;
    }

    public void setBytesUsed(Long bytesUsed) {
        this.bytesUsed = bytesUsed;
    }

    /**
     * Gets the number of packages that are flagged to be included in DPN.
     */
    public Long getDpnBoundPackageCount() {
        return dpnBoundPackageCount;
    }

    public void setDpnBoundPackageCount(Long dpnBoundPackageCount) {
        this.dpnBoundPackageCount = dpnBoundPackageCount;
    }

    /**
     * Gets the number of packages that are marked as world accessible.
     * @return
     */
    public Long getPublicPackageCount() {
        return publicPackageCount;
    }

    public void setPublicPackageCount(Long publicPackageCount) {
        this.publicPackageCount = publicPackageCount;
    }

    /**
     * Gets the number of packages that are marked as accessible only to
     * the owning institution.
     */
    public Long getInstitutionPackageCount() {
        return institutionPackageCount;
    }

    public void setInstitutionPackageCount(Long institutionPackageCount) {
        this.institutionPackageCount = institutionPackageCount;
    }

    /**
     * Gets the number of package that are marked as private.
     */
    public Long getPrivatePackageCount() {
        return privatePackageCount;
    }

    public void setPrivatePackageCount(Long privatePackageCount) {
        this.privatePackageCount = privatePackageCount;
    }

    /**
     * Gets the number of packages that have (in whole or part) failed health
     * checks.
     */
    public Long getFailedPackageCount() {
        return failedPackageCount;
    }

    public void setFailedPackageCount(Long failedPackageCount) {
        this.failedPackageCount = failedPackageCount;
    }

    /**
     * Returns a pretty-printed String representation of the values in this 
     * object.  This may be suitable for debugging output, but contains line
     * breaks.
     */
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("institutionId:           " + institutionId + "\n");
        sb.append("packageCount:            " + packageCount + "\n");
        sb.append("objectCount:             " + objectCount + "\n");
        sb.append("bytesUsed:               " + bytesUsed + "\n");
        sb.append("dpnBoundPackageCount:    " + dpnBoundPackageCount + "\n");
        sb.append("publicPackageCount:      " + publicPackageCount + "\n");
        sb.append("institutionPackageCount: " + institutionPackageCount + "\n");
        sb.append("privatePackageCount:     " + privatePackageCount + "\n");
        sb.append("failedPackageCount:      " + failedPackageCount + "\n");
        return sb.toString();
    }

}
