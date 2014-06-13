package jgram.java;

import jgram.Generator;
import jgram.Tokenizer;

import org.junit.Test;

public class MethodDeclarationTest {
    @Test
    public void testname() throws Exception {
        new Generator().parserOf(MethodDeclarationTest.class).parse(new Tokenizer("private final Map<Class<?>, Parser> classParsers = new HashMap<>();"));
    }
}
