package me.redstom.bf.tokenizer;

import lombok.Getter;

import java.util.regex.Pattern;

public enum Token {
    PLUS(Pattern.compile("^\\+"), "+"),
    MINUS(Pattern.compile("^-"), "-"),
    LEFT(Pattern.compile("^<"), "<"),
    RIGHT(Pattern.compile("^>"), ">"),
    BEGIN(Pattern.compile("^\\["), "["),
    END(Pattern.compile("^]"), "]"),
    INPUT(Pattern.compile("^,"), ","),
    OUTPUT(Pattern.compile("^\\."), "."),
    BLANK(Pattern.compile("^\\s"), true);

    @Getter
    private final Pattern regex;
    @Getter
    private final boolean ignore;
    @Getter
    private final String named;

    Token(Pattern regex, String named) {
        this.regex = regex;
        this.ignore = false;
        this.named = named;
    }

    Token(Pattern regex, boolean ignore) {
        this.regex = regex;
        this.ignore = ignore;
        this.named = "???";
    }
}
