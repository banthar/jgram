package jgram.java.expression;

import jgram.ParseException;
import jgram.java.ElementTest;

import org.junit.Test;

public class StringLiteralTest extends ElementTest {
    @Test
    public void test0() throws Exception {
        parse("\"\"");
    }

    @Test
    public void test1() throws Exception {
        parse("\"a.b.c\"");
    }

    @Test(expected = ParseException.class)
    public void test2() throws Exception {
        parse("abc");
    }

    @Override
    public Class<?> getElementClass() {
        return StringLiteral.class;
    }
}
