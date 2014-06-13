package jgram.java.expression;

import java.util.List;

import jgram.JGram.AtLeast;
import jgram.JGram.Separator;

public class Assignment extends AssignmentExpression {
    @Separator("=")
    @AtLeast(2)
    List<EqualityExpression> sub;
}
