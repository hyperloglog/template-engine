package com.github.fullduplex;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;

import java.util.Map;

import static com.github.fullduplex.TemplateEngine.substitute;

/**
 * Author: rmartin
 * Date: 2/23/13
 */
public class TemplateEngineExamples {

    public void simpleExample() {
        Map<String, String> mapping = Maps.newHashMap();
        mapping.put("city", "Boulder");
        mapping.put("state", "Colorado");
        mapping.put("country", "USA");

        String template = "Hello ${city}, ${state} in ${country}!";
        System.out.println();
        System.out.println(Strings.repeat("=", 100));
        System.out.println("Simple example:");
        System.out.println();
        System.out.println("Mapping: " + mapping);
        System.out.println("Template: " + template);
        System.out.println("Result: " + substitute(template, mapping) );
    }

    public void escapingExample() {
        Map<String, String> varNames = Maps.newHashMap();
        varNames.put("var", "firstName");

        String template = "This is an expression: \\${${var}}";
        System.out.println();
        System.out.println(Strings.repeat("=", 100));
        System.out.println("Escaping example:");
        System.out.println();
        System.out.println("Mapping: " + varNames);
        System.out.println("Template: " + template);
        System.out.println("Result: " + substitute(template, varNames) );
    }

    public static void main(String[] args) {
        TemplateEngineExamples examples = new TemplateEngineExamples();
        examples.simpleExample();
        examples.escapingExample();
    }
}
