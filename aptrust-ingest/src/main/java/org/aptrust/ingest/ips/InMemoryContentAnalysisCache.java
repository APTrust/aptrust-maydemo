package org.aptrust.ingest.ips;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InMemoryContentAnalysisCache implements ContentAnalysisCache {

    final Logger logger = LoggerFactory.getLogger(ContentAnalysisCache.class);

    private Set<String> contentIds;

    private Map<String, Set<String>> objectIdToRequiredContentMap;

    private Map<String, String> objectIdToTitleCache;

    private Map<String, DuraChunkManifest> contentIdToChunkManifestMap;
    
    public InMemoryContentAnalysisCache() {
        contentIds = new HashSet<String>();
        objectIdToRequiredContentMap = new HashMap<String, Set<String>>();
        objectIdToTitleCache = new HashMap<String, String>();
        contentIdToChunkManifestMap = new HashMap<String, DuraChunkManifest>();
    }

    public void notifyContentId(String contentId) {
        if (contentIds.contains(contentId)) {
            throw new RuntimeException();
        }
        contentIds.add(contentId);
        logger.debug("+" + contentId);
    }

    public void forgetContentId(String contentId) {
        contentIds.remove(contentId);
        logger.debug("-" + contentId);
    }

    public void assertObjectParts(Collection<String> contentIds, String objectId) {
        for (String contentId : contentIds) {
            logger.debug("+" + contentId + " --> " + objectId);
        }
        objectIdToRequiredContentMap.put(objectId, new HashSet<String>(contentIds));
    }

    public void notifyChunkManifest(String contentId, DuraChunkManifest manifest) {
        contentIdToChunkManifestMap.put(contentId, manifest);
    }

    public boolean isObjectComplete(String objectId) {
        if (objectIdToRequiredContentMap.containsKey(objectId)) {
            for (String contentId : objectIdToRequiredContentMap.get(objectId)) {
                if (!contentIds.contains(contentId)) {
                    if (!contentIdToChunkManifestMap.containsKey(contentId)) {
                        logger.trace(objectId + " is not complete because it lacks " + contentId);
                        return false;
                    } else {
                        for (DuraChunkManifest.Chunk chunk : contentIdToChunkManifestMap.get(contentId).chunks) {
                            if (!contentIds.contains(chunk.chunkId)) {
                                logger.trace(objectId + " is not complete because it lacks " + contentId);
                                return false;
                            }
                        }
                    }
                }
            }
        } else {
            logger.trace(objectId + " is not complete because its required parts have not been specified");
            return false;
        }
        return true;
    }

    public Collection<String> getObjectContent(String objectId) {
        Collection<String> result = new ArrayList<String>();
        if (objectIdToRequiredContentMap.containsKey(objectId)) {
            for (String contentId : objectIdToRequiredContentMap.get(objectId)) {
                if (contentIds.contains(contentId)) {
                    result.add(contentId);
                } else if (contentIdToChunkManifestMap.containsKey(contentId)) {
                    result.add(contentId + ".dura-manifest");
                    for (DuraChunkManifest.Chunk chunk : contentIdToChunkManifestMap.get(contentId).chunks) {
                        if (contentIds.contains(chunk.chunkId)) {
                            result.add(chunk.chunkId);
                        }
                    }
                }
            }
        }
        return result;
    }

    public void cacheObjectTitle(String objectId, String title) {
        objectIdToTitleCache.put(objectId, title);
    }

    public String getObjectTitle(String objectId) {
        return objectIdToTitleCache.get(objectId);
    }

    public void forgetObject(String objectId) {
        objectIdToTitleCache.remove(objectId);
        objectIdToRequiredContentMap.remove(objectId);
    }

}
