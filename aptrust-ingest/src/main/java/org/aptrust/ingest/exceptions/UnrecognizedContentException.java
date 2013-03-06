package org.aptrust.ingest.exceptions;

import org.aptrust.common.exception.AptrustException;

public class UnrecognizedContentException extends AptrustException {

    String contentId;
    
    public UnrecognizedContentException(String contentId) {
        super("Unrecognized content: " + contentId);
        this.contentId = contentId;
    }

    public String getContentId() {
        return contentId;
    }

}
