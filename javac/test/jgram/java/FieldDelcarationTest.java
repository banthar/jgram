package jgram.java;

import org.junit.Test;

public class FieldDelcarationTest extends ElementTest {
    @Test
    public void test0() throws Exception {
        parse("private final Reflections reflections = new Reflections(\"jgram.java\");");
    }

    @Test
    public void test1() throws Exception {
        parse("private final Map<Class<?>, Parser> classParsers = new HashMap<>();");
    }

    @Override
    public Class<?> getElementClass() {
        return FieldDeclaration.class;
    }
}
