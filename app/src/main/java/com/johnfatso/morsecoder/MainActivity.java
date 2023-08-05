package com.johnfatso.morsecoder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    // button and player status flags
    private boolean currBeeperState = true;
    private boolean currFlasherState = true;
    private boolean currPlayActive = false;

    // logger tag
    private static final String TAG = "MainActivity";

    // View references in the main activity
    private Button playButton;
    private Button dimmerButton;
    private Button beeperButton;
    private View flasherView;
    private EditText inputView;

    // executor object for morse code. The object must extend IMorseExecutor
    private final IMorseExecutor executor  = new MorseExecutor();

    // ViewModel of the activity to hold all state data
    MainViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "main activity is started");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        model = new ViewModelProvider(this).get(MainViewModel.class);

        playButton = findViewById(R.id.main_button_play);
        dimmerButton = findViewById(R.id.main_button_flasher);
        beeperButton = findViewById(R.id.main_button_beeper);
        flasherView = findViewById(R.id.main_blinker_view);
        inputView = findViewById(R.id.main_text_input_box);

        // register the callback to the executor on each cycle
        executor.addExecutionCallBack(flasherCallback);
        executor.setCleanupCallback(cleanupCallback);

        // set state observers for the three buttons and the Flasher view

        final Observer<Boolean> playStateObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean newState) {
                playButton.setText(newState ? R.string.stop_button_text : R.string.play_button_text);
                currPlayActive = newState;
            }
        };
        model.getPlayState().observe(this, playStateObserver);

        final Observer<FlasherState> flasherStateObserver = new Observer<FlasherState>() {
            @Override
            public void onChanged(FlasherState flasherState) {
                setFlasherState(flasherState);
            }
        };
        model.getFlasherState().observe(this, flasherStateObserver);

        final Observer<Boolean> flasherButtonStateObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean newState) {
                dimmerButton.setBackgroundColor(newState ? getColor(R.color.purple) : getColor(R.color.grey) );
                flasherView.setEnabled(newState);
                currFlasherState = newState;
            }
        };
        model.getFlasherWidgetState().observe(this, flasherButtonStateObserver);

        final Observer<Boolean> beeperButtonStateObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean newState) {
                beeperButton.setBackgroundColor(newState ? getColor(R.color.purple) : getColor(R.color.grey));
                currBeeperState = newState;
            }
        };
        model.getBeeperWidgetState().observe(this, beeperButtonStateObserver);

        // set onClick methods for the buttons
        playButton.setOnClickListener(view -> onPlayButtonPressed(currPlayActive));
        beeperButton.setOnClickListener(view -> onBeeperStateChanged(currBeeperState));
        dimmerButton.setOnClickListener(view -> onFlasherStateChanged(currFlasherState));
    }

    private void onBeeperStateChanged(boolean currState) {
        model.getBeeperWidgetState().setValue(!currState);
        // TODO: when beeper is implemented, control beeper state from here
    }

    private void onFlasherStateChanged(boolean currState) {
        model.getFlasherWidgetState().setValue(!currState);
        model.getFlasherState().setValue(FlasherState.IDLE);
    }

    private void onPlayButtonPressed(boolean currState) {
        if (!currState) {
            executor.start(inputView.getText().toString());
            model.getPlayState().setValue(true);
        }
        else {
            executor.stop();
        }
    }

    private synchronized void setFlasherState(FlasherState state) {
        switch (state) {
            case ON:
                flasherView.setBackgroundColor(getColor(R.color.white));
                break;
            case OFF:
                flasherView.setBackgroundColor(getColor(R.color.black));
                break;
            case IDLE:
                flasherView.setBackgroundColor(getColor(R.color.dark));
        }
    }

    /**
     * callback function for controlling the flasher when executor runs
     */
    IMorseExecutor.Callback flasherCallback = (boolean newState) -> {
        if (currFlasherState) {
            model.getFlasherState().postValue(newState ? FlasherState.ON : FlasherState.OFF);
        }
        else {
            model.getFlasherState().postValue(FlasherState.IDLE);
        }
    };

    /**
     * callback function for cleaning up the activity after each execution
     */
    IMorseExecutor.Callback cleanupCallback = (boolean result) -> {
        Log.v(TAG, "clean up started");
        setFlasherState(FlasherState.IDLE);
        model.getPlayState().postValue(false);
    };
}