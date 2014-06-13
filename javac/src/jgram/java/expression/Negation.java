package jgram.java.expression;

import jgram.JGram.Prefix;

public class Negation extends PrefixExpression {
    @Prefix("!")
    PostfixExpression expression;
}
