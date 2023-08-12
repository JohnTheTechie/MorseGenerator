package com.johnfatso.morsecoder;

import android.util.Log;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

/**
 * class to manage the threads generating morse code
 * the users are needed to pass
 */
public class MorseExecutor implements IMorseExecutor{

    private final String TAG = "MorseExecutor";

    private final ArrayList<Callback> callbacks;
    private Callback cleanupCallback;
    private boolean executionFlag;
    private final MorseBeeper beeper;
    private Semaphore semaphore;

    MorseExecutor() {
        callbacks = new ArrayList<>();
        executionFlag = false;
        beeper = new MorseBeeper();
    }

    @Override
    public void addExecutionCallBack(IMorseExecutor.Callback callback) {
        this.callbacks.add(callback);
    }

    @Override
    public void removeExecutionCallback(IMorseExecutor.Callback callback) {
        this.callbacks.remove(callback);
    }

    public void setCleanupCallback(IMorseExecutor.Callback callback) {
        this.cleanupCallback = callback;
    }

    public void removeCleanupCallback(IMorseExecutor.Callback callback) {
        this.cleanupCallback = null;
    }

    public void start(String string) {
        if (callbacks.isEmpty() ) throw new Error("callback not set");
        executionFlag = true;
        semaphore = new Semaphore(callbacks.size());
        for (Callback callback: callbacks)
            new Thread(() -> {
                try {
                    semaphore.acquire();
                    ArrayList<MorseSymbol> sequence = new MorseSeriesConverter()
                            .setInputString(string)
                            .getMorseSequence();
                    if (sequence.isEmpty()) {
                        semaphore.release();
                        beeper.stopTone();
                        return;
                    }
                    for (MorseSymbol symbol: sequence) {
                        if (!executionFlag) {
                            beeper.stopTone();
                            semaphore.release();
                            return;
                        }
                        Log.v(TAG, "executing symbol: "+symbol);
                        switch (symbol) {
                            case DOT:
                            case DASH:
                                beeper.startTone(500, 0.5);
                                callback.execute(true);
                                Thread.sleep(symbol.delay);
                                break;
                            case INTERLETTER_SPACE:
                            case INTERWORD_SPACE:
                            case INTERSYMBOL_SPACE:
                            default:
                                beeper.stopTone();
                                callback.execute(false);
                                Thread.sleep(symbol.delay);
                                break;
                        }
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    beeper.stopTone();
                    callback.execute(false);
                    semaphore.release();
                }

            }).start();

        if (cleanupCallback != null) {
            new Thread(() -> {
                try {
                    semaphore.acquire();
                    Thread.sleep(200);
                    cleanupCallback.execute(true);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    semaphore.release();
                }
            }).start();
        }
    }

    public void stop() {
        executionFlag = false;
    }


}
