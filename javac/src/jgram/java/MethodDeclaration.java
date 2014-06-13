package jgram.java;

import java.util.List;
import java.util.Set;

import jgram.JGram.Prefix;
import jgram.JGram.Separator;
import jgram.JGram.Terminator;

class MethodDeclaration extends ClassMember {
    Set<AccessKeyword> accessMode;
    Type returnType;
    String name;
    @Prefix("(")
    @Separator(",")
    @Terminator(")")
    List<ArgumentDeclaration> arguments;
    MethodBody body;
}
