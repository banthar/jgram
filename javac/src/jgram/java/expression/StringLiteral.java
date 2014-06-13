package jgram.java.expression;

import jgram.JGram.Pattern;

public class StringLiteral extends TopLevelExpression {
    @Pattern("\".*\"")
    String s;
}
