package jgram.java.expression;

import jgram.JGram.Prefix;
import jgram.JGram.Suffix;

public class Parentheses extends TopLevelExpression {
    @Prefix("(")
    @Suffix(")")
    Expression expression;
}
