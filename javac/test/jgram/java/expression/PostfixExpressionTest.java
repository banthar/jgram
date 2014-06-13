package jgram.java.expression;

import org.junit.Test;

public class PostfixExpressionTest extends TopLevelExpressionTest {
    @Test
    public void test4() throws Exception {
        parse("a(b)");
    }

    @Override
    public Class<?> getElementClass() {
        return PostfixExpression.class;
    }
}
