package me.redstom.bf.parser.elements;

import me.redstom.bf.parser.Node;
import me.redstom.bf.parser.ParseContext;
import me.redstom.bf.parser.ParserElement;
import me.redstom.bf.tokenizer.Token;

public record Loop(Node[] body) implements Node {

    public static class Parser implements ParserElement<Loop, Void> {
        @Override
        public Loop parse(ParseContext ctx, Void unused) {
            ctx.eat(Token.BEGIN);
            Node[] body = ctx.parse(Block.Parser.class, new Block.Context(Token.END)).body();
            ctx.eat(Token.END);
            return new Loop(body);
        }
    }

}
