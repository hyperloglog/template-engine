package com.github.fullduplex;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

import java.util.Map;

/**
 * Author: Rob Martin
 * Created: 2/21/13 8:05 PM
 */
public class TemplateEngine {

    // expressions are represented with a dollar sign and wrapping braces, eg. ${foo}
    private static final char CHAR_ESCAPE = '\\';
    private static final char CHAR_INIT = '$';
    private static final char CHAR_OPEN = '{';
    private static final char CHAR_CLOSE = '}';

    /**
     * Given a template and mapping, will replace expressions within the template with their corresonding values
     * from mapping.
     *
     * Eg. "Hello ${who}" + {"who" => "world!"} = "Hello world!"
     *
     * @param template
     * @param mapping
     * @return template with substitutions
     *
     * @throws UnknownMappingException if an expression in the template refers to a mapping not provided in mapping
     * parameter
     */
    public static String substitute(String template, Map<String, String> mapping) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(template), "Parameter template is required.");
        Preconditions.checkArgument(mapping != null, "Parameter mapping is required.");

        StringBuilder output = new StringBuilder();
        CharacterStream stream = new CharacterStream(template);

        while (stream.hasNext()) {
            char c = stream.next();

            // output escaped character
            if (c == CHAR_ESCAPE) {
                // consume/output next character, unless it's a newline
                if (stream.hasNext()) {
                    // todo: fix newline issue
                }
            } else if (c == CHAR_INIT) {
                // expression beginning?
                if (stream.hasNext()) {
                    char nextChar = stream.next();
                    if (nextChar == CHAR_OPEN) {
                        output.append(evaluateExpression(stream, mapping));
                    } else {
                        // false alarm - expression not opened
                        output.append(c).append(nextChar);
                    }
                }
            } else {
                output.append(c);
            }
        }

        return output.toString();
    }

    /**
     * Evaluate a template expression. Expressions are assumed to following the same restrictions as java variable
     * names. Expressions must have matching keys in the mapping.
     *
     * @param stream
     * @param mapping
     * @return the result of looking up the expression in the mapping
     *
     * @throws UnknownMappingException if the expression is not found in the mapping
     * @throws TemplateParseException if the expression is
     */
    protected static String evaluateExpression(CharacterStream stream, Map<String, String> mapping) {
        if (!stream.hasNext())
            throw new TemplateParseException("Empty expression at position " + stream.currIndex());

        StringBuilder expression = new StringBuilder();

        // first character is special
        char c = stream.next();
        if (!Character.isJavaIdentifierStart(c))
            throw new TemplateParseException("Invalid start character for variable: " + c);
        expression.append(c);

        boolean closed = false; // the expression has been closed

        // consume characters from stream, stopping if/when we encounter the close character
        while (stream.hasNext() && c != CHAR_CLOSE) {
            c = stream.next();

            if (c == CHAR_CLOSE) {
                // set close flag, and toss the character away
                closed = true;
            } else {
                if (!Character.isJavaIdentifierPart(c))
                    throw new TemplateParseException("Invalid start character for variable: " + c);
                expression.append(c);
            }
        }

        // ensure we found the close character
        if (!closed)
            throw new TemplateParseException("Expected expression close at position " + stream.currIndex());

        String varName = expression.toString();
        if (!mapping.containsKey(varName))
            throw new UnknownMappingException(varName);

        return mapping.get(varName);
    }

}
