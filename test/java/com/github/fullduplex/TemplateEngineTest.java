package com.github.fullduplex;

import com.google.common.collect.Maps;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static junit.framework.Assert.assertEquals;

/**
 * Author: Rob Martin
 * Created: 2/21/13 9:57 PM
 */
public class TemplateEngineTest {

    private Map<String, String> mapping;
    private Map<String, String> emptyMapping;

    @Before
    public void setUp() throws Exception {
        mapping = Maps.newHashMap();
        mapping.put("city", "Boulder");
        mapping.put("state", "Colorado");
        mapping.put("country", "USA");

        emptyMapping = Maps.newHashMap();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // substitution tests
    //
    @Test
    public void testSingleSubstitution() {
        assertEquals("Hello Boulder!", TemplateEngine.substitute("Hello ${city}!", mapping));
    }

    @Test
    public void testSubstitutionWithNewline() {
        assertEquals("Hello \nBoulder!", TemplateEngine.substitute("Hello \n${city}!", mapping));
    }

    @Test
    public void testMultipleSubstitutions() {
        assertEquals("Hello Boulder, Colorado in USA!",
                     TemplateEngine.substitute("Hello ${city}, ${state} in ${country}!", mapping));
    }

    @Test
    public void testOpenExpressionFalseAlarm() {
        assertEquals("That costs $5.", TemplateEngine.substitute("That costs $5.", emptyMapping));
    }

    @Test
    public void testEscapedSubstitution() throws Exception {
        assertEquals("map = ${'a' -> '1'}", TemplateEngine.substitute("map = \\${'a' -> '1'}", mapping));
    }

    @Test(expected = UnknownMappingException.class)
    public void testMissingMapping() {
        TemplateEngine.substitute("one ${num} three", emptyMapping);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // expression evaluation tests

    @Test(expected = TemplateParseException.class)
    public void testEvaluateEmptyExpression() {
        TemplateEngine.evaluateExpression(new CharacterStream(""), mapping);
    }

    @Test(expected = TemplateParseException.class)
    public void testEvaluateUnclosedExpression() {
        TemplateEngine.evaluateExpression(new CharacterStream("foo"), mapping);
    }

    @Test(expected = TemplateParseException.class)
    public void testInvalidExpressionInvalidStartCharacter() {
        TemplateEngine.evaluateExpression(new CharacterStream("1var}"), mapping);
    }

    @Test(expected = TemplateParseException.class)
    public void testInvalidExpressionInvalidCharacter() {
        TemplateEngine.evaluateExpression(new CharacterStream("x&y}"), mapping);
    }

    @Test
    public void testEvaluateValidExpression() {
        String result = TemplateEngine.evaluateExpression(new CharacterStream("city}"), mapping);
        assertEquals("Expression evaluation failed", mapping.get("city"), result);
    }

}
