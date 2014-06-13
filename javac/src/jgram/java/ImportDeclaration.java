package jgram.java;

import java.util.List;

import jgram.JGram.AtLeast;
import jgram.JGram.Prefix;
import jgram.JGram.Separator;
import jgram.JGram.Suffix;

class ImportDeclaration {
    @Prefix("import")
    @Separator(".")
    @Suffix(";")
    @AtLeast(1)
    List<String> qualifiedName;
}
