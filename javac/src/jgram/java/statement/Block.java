package jgram.java.statement;

import java.util.List;

import jgram.JGram.Prefix;
import jgram.JGram.Terminator;

public class Block extends Statement {
    @Prefix("{")
    @Terminator("}")
    List<Statement> statements;
}
