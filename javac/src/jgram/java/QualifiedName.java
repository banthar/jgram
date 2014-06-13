package jgram.java;

import java.util.List;

import jgram.JGram.AtLeast;
import jgram.JGram.Separator;

class QualifiedName {
    @Separator(".")
    @AtLeast(1)
    List<Identifier> name;
}
