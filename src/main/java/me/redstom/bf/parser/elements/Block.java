package me.redstom.bf.parser.elements;

import lombok.ToString;
import me.redstom.bf.parser.Node;
import me.redstom.bf.parser.ParseContext;
import me.redstom.bf.parser.ParserElement;
import me.redstom.bf.tokenizer.Token;

import java.util.ArrayList;
import java.util.List;

public record Block(Node[] body) implements Node {

    /**
     * Program
     * : PLUS
     * | MINUS
     * | LEFT
     * | RIGHT
     * | BEGIN
     * | END
     * | INPUT
     * | OUTPUT
     * ;
     */
    public static class Parser implements ParserElement<Block, Context> {
        @Override
        public Block parse(ParseContext ctx, Context context) {

            List<Node> nodes = new ArrayList<>();

            while(ctx.lookahead() != context.stop()) {
                switch (ctx.lookahead()) {
                    case BEGIN -> {
                        nodes.add(ctx.parse(Loop.Parser.class));
                    }
                    case PLUS -> {
                        ctx.eat(Token.PLUS);
                        nodes.add(Instruction.ADD);
                    }
                    case MINUS -> {
                        ctx.eat(Token.MINUS);
                        nodes.add(Instruction.SUB);
                    }
                    case LEFT -> {
                        ctx.eat(Token.LEFT);
                        nodes.add(Instruction.MVL);
                    }
                    case RIGHT -> {
                        ctx.eat(Token.RIGHT);
                        nodes.add(Instruction.MVR);
                    }
                    case INPUT -> {
                        ctx.eat(Token.INPUT);
                        nodes.add(Instruction.INP);
                    }
                    case OUTPUT -> {
                        ctx.eat(Token.OUTPUT);
                        nodes.add(Instruction.SHW);
                    }
                    default -> {}
                };
            }

            return new Block(nodes.toArray(Node[]::new));
        }
    }

    public record Context(Token stop) {
    }
}
