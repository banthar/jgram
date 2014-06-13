package jgram.java.statement;

import jgram.JGram.Suffix;
import jgram.java.expression.AssignmentExpression;

class AssignmentStatement extends Statement {
    @Suffix(";")
    AssignmentExpression expression;
}
