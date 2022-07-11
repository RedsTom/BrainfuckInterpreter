package me.redstom.bf.translator;

import me.redstom.bf.parser.Node;
import me.redstom.bf.parser.elements.Block;
import me.redstom.bf.parser.elements.Instruction;
import me.redstom.bf.parser.elements.Loop;

import java.util.Arrays;

public class PythonTranslator implements Translator {

    private StringBuilder code;
    private int indentLevel;

    @Override
    public String translate(Node[] code) {
        this.code = new StringBuilder();
        this.indentLevel = 0;

        this.code.append("""
                class BrainFuck:
                    def __init__(self):
                        self.memory = [0]
                        self.pointer = 0
                        
                    def add(self):
                        self.memory[self.pointer] += 1
                        if self.memory[self.pointer] >= 255:
                            self.memory[self.pointer] = 0
                    
                    def sub(self):
                        self.memory[self.pointer] -= 1
                        if self.memory[self.pointer] < 0:
                            self.memory[self.pointer] = 255
                            
                    def mvr(self):
                        self.pointer += 1
                        if self.pointer >= len(self.memory):
                            self.memory.append(0)
                            
                    def mvl(self):
                        self.pointer -= 1
                        if self.pointer < 0:
                            raise Exception("Pointer is out of bounds ( < 0)")
                            
                    def shw(self):
                        print(chr(self.memory[self.pointer]), end="")
                    
                bf = BrainFuck()
                """);

        for (Node node : code) {
            translate(node);
        }

        return this.code.toString();
    }

    private void translate(Node node) {
        switch (node) {
            case Instruction i -> translateI(i);
            case Block b -> translateB(b);
            case Loop l -> translateL(l);
            default -> {
            }
        }
    }

    private void translateI(Instruction i) {
        switch (i) {
            case ADD -> {
                code.append("\n")
                    .append("\t".repeat(indentLevel))
                    .append("bf.add()");
            }
            case SUB -> {
                code.append("\n")
                    .append("\t".repeat(indentLevel))
                    .append("bf.sub()");
            }
            case MVL -> {
                code.append("\n")
                    .append("\t".repeat(indentLevel))
                    .append("bf.mvl()");
            }
            case MVR -> {
                code.append("\n")
                    .append("\t".repeat(indentLevel))
                    .append("bf.mvr()");
            }
            case INP -> {
                code.append("\n")
                    .append("\t".repeat(indentLevel))
                    .append("bf.memory[bf.pointer] = ord(input())");
            }
            case SHW -> {
                code.append("\n")
                    .append("\t".repeat(indentLevel))
                    .append("bf.shw()");
            }
        }
    }

    private void translateB(Block b) {
        for (Node node : b.body()) {
            translate(node);
        }
    }

    private void translateL(Loop l) {
        code.append("\n")
            .append("\t".repeat(indentLevel))
            .append("while bf.memory[bf.pointer] != 0:");
        if(l.body().length > 0) {
            indentLevel++;
            for (Node node : l.body()) {
                translate(node);
            }
            indentLevel = Math.max(0, indentLevel - 1);
        } else {
            code.append("\n")
                .append("\t".repeat(indentLevel + 1))
                .append("pass");
        }
    }
}
