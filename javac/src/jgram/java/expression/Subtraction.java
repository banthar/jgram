package jgram.java.expression;

import jgram.JGram.Prefix;

public class Subtraction extends AdditiveExpression {
    MultiplicativeExpression left;
    @Prefix("-")
    AdditiveExpression right;
}
