package jgram.java.expression;

import jgram.JGram.Prefix;

public class Addition extends AdditiveExpression {
    AdditiveExpression left;
    @Prefix("+")
    MultiplicativeExpression right;
}
