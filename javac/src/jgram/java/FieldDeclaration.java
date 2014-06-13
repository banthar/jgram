package jgram.java;

import java.util.Set;

import jgram.JGram.Optional;
import jgram.JGram.Prefix;
import jgram.java.expression.Expression;

class FieldDeclaration extends ClassMember {
    Set<AccessKeyword> accessMode;
    Type type;
    Identifier name;
    @Optional
    @Prefix("=")
    Expression value;
    @Prefix(";")
    Void end;
}
