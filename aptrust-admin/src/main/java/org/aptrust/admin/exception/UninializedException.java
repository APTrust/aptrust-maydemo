package org.aptrust.admin.exception;

import org.aptrust.common.exception.AptrustException;
/**
 * 
 * @author Daniel Bernstein
 *
 */
public class UninializedException extends AptrustException{
    public UninializedException() {
        super("The AP Trust Web Application must be initialized before it can be accessed");
    }
}
