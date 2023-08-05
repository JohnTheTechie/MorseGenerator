package com.johnfatso.morsecoder;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {
    private MutableLiveData<Boolean> playState;
    private MutableLiveData<FlasherState> flasherState;
    private MutableLiveData<Boolean> FlasherWidgetState;
    private MutableLiveData<Boolean> beeperWidgetState;

    public MutableLiveData<Boolean> getPlayState() {
        if (playState == null) {
            playState = new MutableLiveData<Boolean>();
            playState.setValue(false);
        }
        return playState;
    }

    public MutableLiveData<FlasherState> getFlasherState() {
        if (flasherState == null) {
            flasherState = new MutableLiveData<>();
            flasherState.setValue(FlasherState.IDLE);
        }
        return flasherState;
    }

    public MutableLiveData<Boolean> getFlasherWidgetState() {
        if (FlasherWidgetState == null) {
            FlasherWidgetState = new MutableLiveData<>();
            FlasherWidgetState.setValue(true);
        }
        return FlasherWidgetState;
    }

    public MutableLiveData<Boolean> getBeeperWidgetState() {
        if (beeperWidgetState == null) {
            beeperWidgetState = new MutableLiveData<>();
            beeperWidgetState.setValue(true);
        }
        return beeperWidgetState;
    }

}
