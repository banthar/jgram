package jgram.java;

import jgram.JGram.Pattern;

public class Identifier {
    @Pattern("[a-zA-Z_$][a-zA-Z\\d_$]*")
    String name;
}
