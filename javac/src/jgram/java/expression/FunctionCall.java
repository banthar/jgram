package jgram.java.expression;

import java.util.List;

import jgram.JGram.Prefix;
import jgram.JGram.Separator;
import jgram.JGram.Terminator;

public class FunctionCall extends PostfixExpression {
    TopLevelExpression function;
    @Prefix("(")
    @Terminator(")")
    @Separator(",")
    List<Expression> arguments;
}
