package com.johnfatso.morsecoder;

public enum MorseSymbol {
    DOT(1),
    DASH(3),
    INTERSYMBOL_SPACE(1),
    INTERLETTER_SPACE(3),
    INTERWORD_SPACE(7),
    UNKNOWN_SYMBOL(7);

    public final long delay;

    MorseSymbol(long delay){
        this.delay = delay*300;
    }
}
