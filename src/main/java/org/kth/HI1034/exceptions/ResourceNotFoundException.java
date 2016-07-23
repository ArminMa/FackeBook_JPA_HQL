package org.kth.HI1034.exceptions;

import org.kth.HI1034.exceptions.exeptionHandler.GenericException;

//@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends GenericException {

    public ResourceNotFoundException(String msg) {
        super(System.currentTimeMillis() + ": " + msg);
    }





}
