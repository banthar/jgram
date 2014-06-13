package jgram;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class JGram {
    @Retention(RetentionPolicy.RUNTIME)
    public static @interface Pattern {
        public String value();
    }

    @Retention(RetentionPolicy.RUNTIME)
    public static @interface Prefix {
        public String value();
    }

    @Retention(RetentionPolicy.RUNTIME)
    public static @interface Suffix {
        public String value();
    }

    @Retention(RetentionPolicy.RUNTIME)
    public static @interface Separator {
        public String value();
    }

    @Retention(RetentionPolicy.RUNTIME)
    public static @interface Terminator {
        public String value();
    }

    @Retention(RetentionPolicy.RUNTIME)
    public static @interface Optional {
    }

    @Retention(RetentionPolicy.RUNTIME)
    public static @interface AtLeast {
        public int value();
    }
}
