package jgram.java;

import org.junit.Test;

public class TypeTest extends ElementTest {
    @Test
    public void test0() throws Exception {
        parse("Set");
    }

    @Test
    public void test1() throws Exception {
        parse("Set<Class>");
    }

    @Test
    public void test2() throws Exception {
        parse("Set<?>");
    }

    @Test
    public void test3() throws Exception {
        parse("Set<Class<?>>");
    }

    @Override
    public Class<?> getElementClass() {
        return Type.class;
    }
}
