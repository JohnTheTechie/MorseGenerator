package com.johnfatso.morsecoder;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class MorseSeriesConverterTest {

    ISequenceGenerator converter;

    @Before
    public void setUp() throws Exception {
        converter = new MorseSeriesConverter();
    }

    @After
    public void tearDown() throws Exception {
        converter = null;
    }

    @Test
    public void dummyTest() {
        assertTrue(true);
    }

    /*

    @Test
    public void setInputString() {
        ISequenceGenerator obj = converter.setInputString("test");

    }

    @Test
    public void getInputString() {
        assertEquals("test", converter.setInputString("test").getInputString());
        assertEquals("", converter.setInputString("").getInputString());
        assertEquals("aa a aaa", converter.setInputString("aa a aaa").getInputString());
    }

    @Test
    public void getMorseSequence() {
        ArrayList<MorseSymbol> symbols = new ArrayList<>();
        symbols.add(MorseSymbol.DOT);
        symbols.add(MorseSymbol.DOT);
        symbols.add(MorseSymbol.DOT);
        symbols.add(MorseSymbol.INTERLETTER_SPACE);
        symbols.add(MorseSymbol.DASH);
        symbols.add(MorseSymbol.DASH);
        symbols.add(MorseSymbol.DASH);
        symbols.add(MorseSymbol.INTERLETTER_SPACE);
        symbols.add(MorseSymbol.DOT);
        symbols.add(MorseSymbol.DOT);
        symbols.add(MorseSymbol.DOT);
        assertEquals(symbols, converter.setInputString("SOS").getMorseSequence());
    }

    @Test
    public void isEmptySequence() {
        assertTrue(converter.isEmptySequence());
        assertTrue(converter.setInputString("").isEmptySequence());
        assertFalse(converter.setInputString("abs").isEmptySequence());
        assertFalse(converter.setInputString("a b").isEmptySequence());
    }
    */
}
