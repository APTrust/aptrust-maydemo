package org.aptrust.ingest.api;

import java.util.List;
import org.aptrust.common.metadata.APTrustMetadata;

/**
 * An interface whose implementation define a package of digital objects and
 * metadata for submission into AP Trust.
 */
public interface IngestPackage {
 
    /**
     * Gets the AP Trust required submission metadata that should be applied
     * to all of the digital objects in this package.
     */
    public APTrustMetadata getMetadata();

    /**
     * Gets a list of DigitalObject objects that represent materials for
     * inclusion in AP Trust.
     */
    public List<DigitalObject> getDigitalObjects();
}
