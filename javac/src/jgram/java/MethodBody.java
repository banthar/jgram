package jgram.java;

import jgram.JGram.Prefix;
import jgram.java.statement.Block;

abstract class MethodBody {
    @Prefix(";")
    class AbstractMethodBody extends MethodBody {
    }

    class ConcreteMethodBody extends MethodBody {
        Block block;
    }
}
