package jgram.java;

import jgram.JGram.Prefix;

public abstract class AccessKeyword {
    @Prefix("private")
    public class Private extends AccessKeyword {
    }

    @Prefix("public")
    public class Public extends AccessKeyword {
    }

    @Prefix("final")
    public class Final extends AccessKeyword {
    }
}
