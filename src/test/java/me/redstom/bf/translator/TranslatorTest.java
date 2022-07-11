package me.redstom.bf.translator;

import me.redstom.bf.parser.Parser;
import me.redstom.bf.parser.elements.Block;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TranslatorTest {

    private Translator translator;
    private Parser parser;

    @BeforeEach
    void init() {
        this.parser = new Parser();
    }

    @Test
    void testPythonTranslator() {
        this.translator = new PythonTranslator();

        String code = "++++++++++[>+++++++>++++++++++>+++>+<<<<-]>++.>+.+++++++..+++.>++.<<+++++++++++++++.>.+++.------.--------.>+.>.";
        Block block = parser.parse(code);

        String result = translator.translate(block.body());
        System.out.println(result);
    }
}
