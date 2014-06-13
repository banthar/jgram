package jgram.java.statement;

import jgram.JGram.Prefix;
import jgram.JGram.Suffix;
import jgram.java.expression.Expression;

@Prefix("while")
public class WhileLoop extends Statement {
    @Prefix("(")
    @Suffix(")")
    Expression condition;
}
