package com.github.fullduplex;

/**
 * Author: Rob Martin
 * Created: 2/21/13 8:20 PM
 */
public class TemplateParseException extends RuntimeException {

    private final String message;

    public TemplateParseException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return "Template parse exception: " + message;
    }

}
