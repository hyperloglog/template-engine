template-engine
===============

A simple templating engine. Syntax for expressions is: ${expr}, where expr is a key provided in a Map.

eg.

    Map<String, String> mapping = Maps.newHashMap();
    mapping.put("city", "Boulder");
    mapping.put("state", "Colorado");
    mapping.put("country", "USA");
    String result = TemplateEngine.substitute("Hello ${city}, ${state} in ${country}!", mapping);

Escaping of expressions can be achieved by backslash-escaping the dollar sign:

eg.

    Map<String, String> mapping = Maps.newHashMap();
    mapping.put("varName", "x");
    String result = TemplateEngine.substitute("Expression is: \\${${varName}}", mapping);

See com.github.fullduplex.TemplateEngineExamples and com.github.fullduplex.TemplateEngineTest for more examples.

