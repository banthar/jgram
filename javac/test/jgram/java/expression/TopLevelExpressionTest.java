package jgram.java.expression;

import jgram.java.ElementTest;

import org.junit.Test;

public class TopLevelExpressionTest extends ElementTest {
    @Test
    public void test0() throws Exception {
        parse("(a)");
    }

    @Test
    public void test1() throws Exception {
        parse("a");
    }

    @Test
    public void test2() throws Exception {
        parse("this");
    }

    @Test
    public void test3() throws Exception {
        parse("\"xxx\"");
    }

    @Override
    public Class<?> getElementClass() {
        return TopLevelExpression.class;
    }
}
