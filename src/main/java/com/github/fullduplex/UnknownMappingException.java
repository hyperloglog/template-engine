package com.github.fullduplex;

/**
 * Author: Rob Martin
 * Created: 2/21/13 8:20 PM
 */
public class UnknownMappingException extends RuntimeException {

    private final String mapping;

    public UnknownMappingException(String mapping) {
        this.mapping = mapping;
    }

    @Override
    public String getMessage() {
        return "Mapping not found: " + mapping;
    }

}
