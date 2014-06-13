package jgram.java.expression;

import jgram.java.ElementTest;

import org.junit.Test;

public class NewInvocationTest extends ElementTest {
    @Test
    public void test0() throws Exception {
        parse("new Type()");
    }

    @Test
    public void test1() throws Exception {
        parse("new Type<>()");
    }

    @Test
    public void test2() throws Exception {
        parse("new Reflections(\"jgram.java\")");
    }

    @Override
    public Class<?> getElementClass() {
        return NewInvocation.class;
    }
}
