package com.johnfatso.morsecoder;

import android.util.Log;

public class ScreenDimmer implements IPlayer{

    private final String TAG = "ScreenDimmer";
    private boolean printingFlag = false;

    public ScreenDimmer() {
        Log.v(TAG, "Screendimmer instance created");
    }

    @Override
    public void start(String string) {

    }

    @Override
    public void stop() {

    }
}
