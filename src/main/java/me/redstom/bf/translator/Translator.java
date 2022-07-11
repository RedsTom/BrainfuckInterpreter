package me.redstom.bf.translator;

import me.redstom.bf.parser.Node;

public interface Translator {

    String translate(Node[] code);

}
