package org.aptrust.common.exception;

/**
 * 
 * @author Daniel Bernstein
 * @created Dec 11, 2012
 *
 */
public class AptrustException extends Exception {

    public AptrustException(Exception rootCause) {
        super(rootCause);
    }

    public AptrustException(String message) {
        super(message);
    }

    public AptrustException(String message, Throwable rootCause) {
        super(message, rootCause);
    }

}
