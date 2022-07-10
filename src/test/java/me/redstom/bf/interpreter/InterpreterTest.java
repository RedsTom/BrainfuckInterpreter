package me.redstom.bf.interpreter;

import me.redstom.bf.parser.Parser;
import me.redstom.bf.parser.elements.Block;
import org.junit.jupiter.api.*;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class InterpreterTest {


    private final OutputStream outContent = new ByteArrayOutputStream();
    private final OutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    private Interpreter interpreter;
    private Parser parser;

    @BeforeAll
    public void setUp() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @BeforeEach
    public void init() {
        interpreter = new Interpreter();
        parser = new Parser();
    }

    @Test
    public void testHelloWorld() {
        String code = "++++++++++[>+++++++>++++++++++>+++>+<<<<-]>++.>+.+++++++..+++.>++.<<+++++++++++++++.>.+++.------.--------.>+.>.";

        Block block = parser.parse(code);
        interpreter.init(block.body());

        assertEquals("Hello World!\n", outContent.toString());
    }


    @AfterAll
    public void tearDown() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }
}
