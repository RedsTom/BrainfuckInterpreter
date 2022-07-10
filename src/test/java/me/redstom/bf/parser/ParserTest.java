package me.redstom.bf.parser;

import me.redstom.bf.parser.elements.Block;
import me.redstom.bf.parser.elements.Instruction;
import me.redstom.bf.parser.elements.Loop;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    private Parser parser;

    @BeforeEach
    public void init() {
        parser = new Parser();
    }

    @Test
    public void testParse() {
        String code = "++++[>++<-]";
        Block block = parser.parse(code);

        Node[] body = block.body();

        assertEquals(5, body.length);
        for(int i = 0; i < 4; i++) {
            assertEquals(Instruction.ADD, body[i]);
        }

        assertInstanceOf(Loop.class, body[4]);
        Loop loop = (Loop) body[4];

        Node[] loopBody = loop.body();
        assertEquals(5, loopBody.length);

        assertEquals(loopBody[0], Instruction.MVR);
        assertEquals(loopBody[1], Instruction.ADD);
        assertEquals(loopBody[2], Instruction.ADD);
        assertEquals(loopBody[3], Instruction.MVL);
        assertEquals(loopBody[4], Instruction.SUB);
    }
}
