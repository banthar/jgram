package jgram.java;

import java.util.List;

import jgram.JGram.Optional;
import jgram.JGram.Prefix;
import jgram.JGram.Separator;
import jgram.JGram.Terminator;

public class Type extends TypeArgument {
    QualifiedName name;
    @Optional
    @Prefix("<")
    @Terminator(">")
    @Separator(",")
    List<TypeArgument> arguments;
}
