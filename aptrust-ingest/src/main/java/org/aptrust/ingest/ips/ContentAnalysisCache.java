package org.aptrust.ingest.ips;

import java.util.Collection;

/**
 * Implementations of this interface maintain a cache of information known
 * about a DuraCloud space.  Some of the information is relatively easy to
 * detect (like whether a particular contentId exists) but other pieces of
 * information have required content read and parsed and there is a high value
 * to retaining that information.
 */
public interface ContentAnalysisCache {

    /**
     * Makes note of the fact that the given contentId exists.
     */
    public void notifyContentId(String contentId);

    /**
     * Makes note that the given contentId no longer exists.
     */
    public void forgetContentId(String contentId);

    /**
     * Makes note of the fact that the given contentIds are expected in order
     * to complete the object indicated by objectId.
     */
    public void assertObjectParts(Collection<String> contentIds, String objectId);

    /**
     * Makes note of the fact that a DuraChunkManifest exists for the given 
     * contentId.
     */
    public void notifyChunkManifest(String contentId, DuraChunkManifest manifest);

    /**
     * Queries this ContentAnalysisCache to determine if all the parts (as 
     * known by prior method calls) that are needed to complete the given
     * objectId are present.
     */
    public boolean isObjectComplete(String objectId);

    /**
     * Gets every contentId that is known to exist for the given objectId.
     * This may not represent the complete object, and indeed may be empty in
     * the event that the contents of an object aren't known.  To determine 
     * whether this list represents a complete object, invoke 
     * isObjectComplete().
     */
    public Collection<String> getObjectContent(String objectId);
}
