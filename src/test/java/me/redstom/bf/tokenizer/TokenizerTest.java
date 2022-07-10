package me.redstom.bf.tokenizer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TokenizerTest {

    private Tokenizer tokenizer;

    @BeforeEach
    public void init() {
        tokenizer = new Tokenizer();
    }

    @Test
    public void testTokenizerParsesCorrectly() {
        tokenizer.init("""
                +-<>[].,
                """);

        assertEquals(Token.PLUS, tokenizer.getNextToken());
        assertEquals(Token.MINUS, tokenizer.getNextToken());
        assertEquals(Token.LEFT, tokenizer.getNextToken());
        assertEquals(Token.RIGHT, tokenizer.getNextToken());
        assertEquals(Token.BEGIN, tokenizer.getNextToken());
        assertEquals(Token.END, tokenizer.getNextToken());
        assertEquals(Token.OUTPUT, tokenizer.getNextToken());
        assertEquals(Token.INPUT, tokenizer.getNextToken());
    }

    @Test
    public void testEOF() {
        tokenizer.init("""
                """);

        assertNull(tokenizer.getNextToken());
    }

    @Test
    public void testUnexpectedToken() {
        tokenizer.init("""
                \\
                """);

        assertNull(tokenizer.getNextToken());
    }
}
