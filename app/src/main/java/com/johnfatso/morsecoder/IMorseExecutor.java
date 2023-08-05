package com.johnfatso.morsecoder;

public interface IMorseExecutor {
    public interface Callback {
        void execute(boolean newState);
    }

    void addExecutionCallBack(IMorseExecutor.Callback callback);
    void removeExecutionCallback(IMorseExecutor.Callback callback);
    void setCleanupCallback(IMorseExecutor.Callback callback);
    void removeCleanupCallback(IMorseExecutor.Callback callback);
    void start(String string);
    void stop();

}
