package jgram.java.statement;

import jgram.JGram.Optional;
import jgram.JGram.Prefix;
import jgram.java.AccessKeyword.Final;
import jgram.java.Identifier;
import jgram.java.Type;
import jgram.java.expression.Expression;

class VariableDeclaration extends Statement {
    @Optional
    Final isFinal;
    Type type;
    Identifier name;
    @Optional
    @Prefix("=")
    Expression value;
    @Prefix(";")
    Void terminator;
}
