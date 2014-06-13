package jgram.java.expression;

import jgram.java.ElementTest;

import org.junit.Test;

public class FunctionCallTest extends ElementTest {
    @Test
    public void testName() throws Exception {
        parse("classParser(type)");
    }

    @Override
    public Class<?> getElementClass() {
        return FunctionCall.class;
    }
}
