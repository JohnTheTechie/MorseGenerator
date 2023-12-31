package com.johnfatso.morsecoder;

public enum MorseTable {

    A(".-"),
    B("-..."),
    C("-.-."),
    D("-.."),
    E("."),
    F("..-."),
    G("--."),
    H("...."),
    I(".."),
    J(".---"),
    K("-.-"),
    L("-"),
    M("--"),
    N("-."),
    O("---"),
    P(".--."),
    Q("--.-"),
    R(".-."),
    S("..."),
    T("-"),
    U("..-"),
    V("...-"),
    W(".--"),
    X("-..-"),
    Y("-.--"),
    Z("--.."),
    N0("-----"),
    N1(".----"),
    N2("..---"),
    N3("...--"),
    N4("....-"),
    N5("....."),
    N6("-...."),
    N7("--..."),
    N8("---.."),
    N9("----."),
    PERIOD(".-.-.-"),
    COMMA("--..--"),
    DEFAULT("..--.-");

    public final String sequence;

    MorseTable(String sequence) {
        this.sequence = sequence;
    }
}
