package jgram.java.expression;

import org.junit.Test;

public class ExpressionTest extends PostfixExpressionTest {
    @Test
    public void test5() throws Exception {
        parse("this.classes = new HashSet<>()");
    }

    @Test
    public void test6() throws Exception {
        parse("a+b-c");
    }

    @Override
    public Class<?> getElementClass() {
        return Expression.class;
    }
}
