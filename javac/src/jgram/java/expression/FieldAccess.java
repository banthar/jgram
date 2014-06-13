package jgram.java.expression;

import java.util.List;

import jgram.JGram.AtLeast;
import jgram.JGram.Separator;

public class FieldAccess extends PostfixExpression {
    @AtLeast(2)
    @Separator(".")
    List<TopLevelExpression> sub;
}
