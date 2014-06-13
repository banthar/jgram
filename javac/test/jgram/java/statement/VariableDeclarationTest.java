package jgram.java.statement;

import jgram.java.ElementTest;

import org.junit.Test;

public class VariableDeclarationTest extends ElementTest {
    @Test
    public void test0() throws Exception {
        parse("final Parser parser;");
    }

    @Test
    public void test1() throws Exception {
        parse("final Parser parser = x;");
    }

    @Test
    public void test2() throws Exception {
        parse("final Parser parser = classParser(type);");
    }

    @Override
    public Class<?> getElementClass() {
        return VariableDeclaration.class;
    }
}
