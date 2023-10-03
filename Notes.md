``` 
Exception in thread "main" java.lang.AssertionError: Parsing error expected for (x)
        at jstest.prefix.ParserTester.assertParsingError(Unknown Source)
        at jstest.prefix.Kind.printParsingError(Unknown Source)
        at jstest.prefix.Kind.lambda$selector$1(Unknown Source)
        at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
        at jstest.expression.BaseTester.test(Unknown Source)
        at jstest.expression.Builder.lambda$selector$4(Unknown Source)
        at base.Selector$Composite.lambda$v$0(Unknown Source)
        at base.Selector.lambda$test$2(Unknown Source)
        at base.Log.lambda$action$0(Unknown Source)
        at base.Log.silentScope(Unknown Source)
        at base.Log.scope(Unknown Source)
        at base.Log.scope(Unknown Source)
        at base.Selector.lambda$test$3(Unknown Source)
        at java.base/java.lang.Iterable.forEach(Iterable.java:75)
        at base.Selector.test(Unknown Source)
        at base.Selector.main(Unknown Source)
        at jstest.prefix.FullPrefixTest.main(Unknown Source)
        at jstest.prefix.FullPostfixTest.main(Unknown Source)
ERROR: Tests: failed

```