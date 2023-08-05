package com.johnfatso.morsecoder;

import android.content.res.Resources;
import android.os.SystemClock;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

/**
 * class to manage the threads generating morse code
 * the users are needed to pass
 */
public class MorseExecutor implements IMorseExecutor{


    private final ArrayList<Callback> callbacks;
    private Callback cleanupCallback;
    private boolean executionFlag;
    private Semaphore semaphore;

    MorseExecutor() {
        callbacks = new ArrayList<>();
        executionFlag = false;
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
            new Thread() {
                @Override
                public void run() {
                    try {
                        semaphore.acquire();
                        ArrayList<MorseSymbol> sequence = new MorseSeriesConverter()
                                .setInputString(string)
                                .getMorseSequence();
                        if (sequence.isEmpty()) {
                            semaphore.release();
                            return;
                        }
                        for (MorseSymbol symbol: sequence) {
                            if (!executionFlag) {
                                semaphore.release();
                                return;
                            }
                            switch (symbol) {
                                case DOT:
                                case DASH:
                                    callback.execute(true);
                                    sleep(symbol.delay);
                                    break;
                                case INTERLETTER_SPACE:
                                case INTERWORD_SPACE:
                                case INTERSYMBOL_SPACE:
                                default:
                                    callback.execute(false);
                                    sleep(symbol.delay);
                                    break;
                            }
                        }
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    } finally {
                        semaphore.release();
                    }

                }
            }.start();

        if (cleanupCallback != null) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        semaphore.acquire();
                        cleanupCallback.execute(true);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    } finally {
                        semaphore.release();
                    }
                }
            }.start();
        }
    }

    public void stop() {
        executionFlag = false;
    }


}
