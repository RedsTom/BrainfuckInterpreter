package me.redstom.bf.parser;

import lombok.Getter;
import me.redstom.bf.exceptions.SyntaxError;
import me.redstom.bf.parser.elements.Block;
import me.redstom.bf.tokenizer.Token;
import me.redstom.bf.tokenizer.Tokenizer;

public class Parser {

    private String string;
    private Tokenizer tokenizer;

    @Getter
    private Token lookahead;

    public Block parse(String code) {
        this.string = code;
        this.tokenizer = new Tokenizer();
        tokenizer.init(code);
        this.lookahead = tokenizer.getNextToken();

        ParseContext ctx = ParseContext.create(this);
        return ctx.parse(Block.Parser.class, new Block.Context(null));
    }

    public Token eat(Token expected) {
        Token token = this.lookahead;

        if(token == null) {
            throw new SyntaxError("Unexpected en of input, expected : \"" + expected.named() + "\" !");
        }

        if(token != expected) {
            throw new SyntaxError("Unexpected \"" + token.named() + "\", expected : " + expected.named() + " !");
        }

        this.lookahead = tokenizer.getNextToken();
        return token;
    }
}
