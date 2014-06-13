package jgram.java;

import java.util.List;
import java.util.Set;

import jgram.JGram.Prefix;
import jgram.JGram.Terminator;

class ClassDeclaration {
    Set<AccessKeyword> accessMode;
    @Prefix("class")
    String name;
    @Prefix("{")
    @Terminator("}")
    List<ClassMember> members;
}
