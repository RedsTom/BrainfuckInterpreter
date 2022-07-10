package me.redstom.bf.tokenizer;

import lombok.SneakyThrows;

import java.util.regex.Matcher;

public class Tokenizer {

    private String string;
    private int index;

    public void init(String code) {
        this.string = code;
        this.index = 0;
    }

    private boolean isEOF() {
        return index >= string.length();
    }

    public Token getNextToken() {
        if(isEOF()) {
            return null;
        }

        String slice = this.string.substring(index);

        for (Token token : Token.values()) {
            Matcher matcher = token.regex().matcher(slice);

            if(matcher.find()) {
                index += matcher.end() - matcher.start();
                return token.ignore() ? getNextToken() : token;
            }
        }

        return null;
    }
}
