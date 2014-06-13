package jgram.java.expression;

import java.util.List;

import jgram.JGram.Prefix;
import jgram.JGram.Separator;
import jgram.JGram.Terminator;
import jgram.java.Type;

public class NewInvocation extends PrefixExpression {
    @Prefix("new")
    Type type;
    @Prefix("(")
    @Terminator(")")
    @Separator(",")
    List<Expression> arguments;
}
