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

    @Before
    public void setUp() throws Exception {
        mapping = Maps.newHashMap();
        mapping.put("city", "Boulder");
        mapping.put("state", "Colorado");
    }

    @Test
    public void testSingleSubstitution() {
        assertEquals("Hello Boulder!", TemplateEngine.substitute("Hello ${city}!", mapping));
    }

    @Test
    public void testMultipleSubstitutions() {
        assertEquals("Hello Boulder in Colorado!", TemplateEngine.substitute("Hello ${city} in ${state}!", mapping));
    }

    @Test
    public void testEscapedSubstitution() throws Exception {
        assertEquals("map = ${'a' -> '1'}", TemplateEngine.substitute("map = \\${'a' -> '1'}", mapping));
    }

    @Test(expected = TemplateParseException.class)
    public void testEvaluateEmptyExpression() {
        TemplateEngine.evaluateExpression(new CharacterStream(""), mapping);
    }

    @Test(expected = TemplateParseException.class)
    public void testEvaluateUnclosedExpression() {
        TemplateEngine.evaluateExpression(new CharacterStream("foo"), mapping);
    }

    @Test
    public void testEvaluateValidExpression() {
        String result = TemplateEngine.evaluateExpression(new CharacterStream("city}"), mapping);
        assertEquals("Expression evaluation failed", mapping.get("city"), result);
    }
}
