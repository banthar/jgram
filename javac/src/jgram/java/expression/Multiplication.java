package jgram.java.expression;

import java.util.List;

import jgram.JGram.AtLeast;
import jgram.JGram.Separator;

public class Multiplication extends MultiplicativeExpression {
    @AtLeast(2)
    @Separator("*")
    List<PrefixExpression> sub;
}
