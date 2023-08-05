package com.johnfatso.morsecoder;

import java.util.ArrayList;

public interface ISequenceGenerator {
    ISequenceGenerator setInputString(String inputString);
    String getInputString();
    ArrayList<MorseSymbol> getMorseSequence();
    boolean isEmptySequence();
}
