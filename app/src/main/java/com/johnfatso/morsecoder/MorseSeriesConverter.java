package com.johnfatso.morsecoder;

import android.content.res.Resources;
import android.util.Log;

import java.util.ArrayList;
import java.util.Map;

public class MorseSeriesConverter implements ISequenceGenerator{

    private final String TAG = "MorseConverter";

    private final Map<String, MorseTable> keyMap = Map.ofEntries(
            Map.entry("a", MorseTable.A),
            Map.entry("b", MorseTable.B),
            Map.entry("c", MorseTable.C),
            Map.entry("d", MorseTable.D),
            Map.entry("e", MorseTable.E),
            Map.entry("f", MorseTable.F),
            Map.entry("g", MorseTable.G),
            Map.entry("h", MorseTable.H),
            Map.entry("i", MorseTable.I),
            Map.entry("j", MorseTable.J),
            Map.entry("k", MorseTable.K),
            Map.entry("l", MorseTable.L),
            Map.entry("m", MorseTable.M),
            Map.entry("n", MorseTable.N),
            Map.entry("o", MorseTable.O),
            Map.entry("p", MorseTable.P),
            Map.entry("q", MorseTable.Q),
            Map.entry("r", MorseTable.R),
            Map.entry("s", MorseTable.S),
            Map.entry("t", MorseTable.T),
            Map.entry("u", MorseTable.U),
            Map.entry("v", MorseTable.V),
            Map.entry("w", MorseTable.W),
            Map.entry("x", MorseTable.X),
            Map.entry("y", MorseTable.Y),
            Map.entry("z", MorseTable.Z),
            Map.entry("0", MorseTable.N0),
            Map.entry("1", MorseTable.N1),
            Map.entry("2", MorseTable.N2),
            Map.entry("3", MorseTable.N3),
            Map.entry("4", MorseTable.N4),
            Map.entry("5", MorseTable.N5),
            Map.entry("6", MorseTable.N6),
            Map.entry("7", MorseTable.N7),
            Map.entry("8", MorseTable.N8),
            Map.entry("9", MorseTable.N9),
            Map.entry("period", MorseTable.PERIOD),
            Map.entry("comma", MorseTable.COMMA),
            Map.entry("default", MorseTable.DEFAULT)
    );
    private String inputString;
    private ArrayList<MorseSymbol> morseSequnce;

    MorseSeriesConverter(){
        inputString = "";
        morseSequnce = new ArrayList<>();
    }

    @Override
    public ISequenceGenerator setInputString(String inputString) {
        this.inputString = inputString;
        this.calculateSequence();
        return this;
    }

    @Override
    public String getInputString() {
        return this.inputString;
    }

    @Override
    public ArrayList<MorseSymbol> getMorseSequence() {
        return this.morseSequnce;
    }

    @Override
    public boolean isEmptySequence() {
        return morseSequnce.isEmpty();
    }

    private void calculateSequence(){

        if (inputString.length() == 0) {
            Log.i(TAG, "input string is empty");
            return;
        }

        boolean firstLetterFlag = true;
        morseSequnce.clear();
        for (char raw_character: this.inputString.toCharArray() ) {
            char character = raw_character;
            if (Character.isAlphabetic(raw_character))
                character = Character.toLowerCase(raw_character);
            if (firstLetterFlag) {
                firstLetterFlag = false;
            } else {
                morseSequnce.add(MorseSymbol.INTERLETTER_SPACE);
            }
            Log.v(TAG, "analysing character: " + character
                            + " | is defined: " + keyMap.containsKey(""+character)
                            + " | resId: " + keyMap.get(""+character));
            String morsecode = "";
            if (Character.isAlphabetic(character) && keyMap.containsKey(""+character)) {
                morsecode =  keyMap.get(""+character).sequence;
            }
            else if (Character.isDigit(character) && keyMap.containsKey(""+character)) {
                morsecode =  keyMap.get(""+character).sequence;
            } else if (Character.compare(character, '.') == 0 ) {
                morsecode =  keyMap.get(""+character).sequence;
            } else if (Character.compare(character, ',') == 0 ) {
                morsecode =  keyMap.get(""+character).sequence;
            } else if (Character.compare(character, ' ') == 0){
                morseSequnce.add(MorseSymbol.INTERWORD_SPACE);
                continue;
            } else {
                morsecode =  keyMap.get(""+character).sequence;
            }
            morseSequnce.addAll(this.convertCharSeqToMorseSymbolSeq(morsecode));
        }
        Log.v(TAG, "conversion completed: "+morseSequnce);
    }

    private ArrayList<MorseSymbol> convertCharSeqToMorseSymbolSeq(String charSeq) {
        ArrayList<MorseSymbol> letter = new ArrayList<>();
        boolean isFirstChar = true;
        for (Character character: charSeq.toCharArray()) {
            if (isFirstChar) {
                isFirstChar = false;
            }
            else {
                letter.add(MorseSymbol.INTERSYMBOL_SPACE);
            }
            switch (character) {
                case '.':
                    letter.add(MorseSymbol.DOT);
                    break;
                case '-':
                    letter.add(MorseSymbol.DASH);
                    break;
                default:
                    throw new Error("unknown char "+character);
            }
        }
        return letter;
    }
}
