package me.redstom.bf.interpreter;

import lombok.SneakyThrows;
import me.redstom.bf.parser.Node;
import me.redstom.bf.parser.elements.Block;
import me.redstom.bf.parser.elements.Instruction;
import me.redstom.bf.parser.elements.Loop;

import java.util.Arrays;

public class Interpreter {

    private int[] memory;
    private int pointer;

    public void init(Node[] code) {
        this.memory = new int[1];
        this.pointer = 0;

        for(Node node : code) {
            visit(node);
        }
    }

    private void visit(Node node) {
        switch(node) {
            case Instruction i -> step(i);
            case Block b -> step(b);
            case Loop l -> step(l);
            default -> {}
        }
    }


    private void step(Instruction instruction) {
        switch (instruction) {
            case ADD -> {
                memory[pointer]++;
                if(memory[pointer] >= 255) {
                    memory[pointer] = 0;
                }
            }
            case SUB -> {
                memory[pointer]--;
                if(memory[pointer] < 0) {
                    memory[pointer] = 255;
                }
            }
            case MVR -> {
                pointer++;
                if(pointer >= memory.length) {
                    memory = Arrays.copyOf(memory, pointer + 1);
                }
            }
            case MVL -> {
                pointer--;
                if(pointer < 0) {
                    throw new IllegalStateException("Pointer is out of bounds ( < 0)");
                }
            }
            case SHW -> show();
            case INP -> input();
        }
    }

    @SneakyThrows
    private void input() {
        memory[pointer] = (byte) System.in.read();
    }

    private void show() {
        System.out.print((char) memory[pointer]);
    }

    private void step(Block block) {
        for(Node node : block.body()) {
            visit(node);
        }
    }

    private void step(Loop loop) {
        while(memory[pointer] != 0) {
            for(Node node : loop.body()) {
                visit(node);
            }
        }
    }
}
