package com.example.sidappidea;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ConfigurableActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String[] ACTIONS = {
            "START",
            "ACQ",
            "UHUB",
            "STOP"
    };
    // Chronometer
    private Chronometer chronometer;

    // ConstraintLayout
    private ConstraintLayout layout;

    // Actions List TextView
    private TextView actionsListView;
    private final List<Action> actions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurable);

        // Save the Chronometer as a member variable
        chronometer = (Chronometer) findViewById(R.id.configurable_chrono_match);

        // Save the Text View as a member variable
        actionsListView = (TextView) findViewById(R.id.configurable_text_actions);

        // Save the Constraint Layout as a member variable
        layout = (ConstraintLayout) findViewById(R.id.main_layout);

    }

//    @Override
//    public void onClick(View view) {
//
//        // Get the ID of the button that was clicked
//        final int id = view.getId();
//
//        // Switch on the ID of the button that was clicked
//        // Start button was clicked
//        if (id == R.id.main_btn_start) {
//            // Start the chronometer
//            startMatch();
//            // Acquire button was clicked
//        } else if (id == R.id.main_btn_acquire) {
//            // Acquire the ball
//            acquireBall();
//            // Score button was clicked
//        } else if (id == R.id.main_btn_score) {
//            // Score the ball
//            scoreBall();
//            // End button was clicked
//        } else if (id == R.id.main_btn_end) {
//            // End the match
//            endMatch();
//        } else if (id == R.id.main_btn_undo) {
//            // Undo the last action
//            if (actions.size() > 0) {
//                actions.remove(actions.size() - 1);
//            }
//            // Update the TextView
//            updateActionsListView();
//        }
    }

    private void updateActionsListView() {
        // Clear the TextView
        actionsListView.setText("");
        // Loop through the actions and add them to the TextView
        for (Action action : actions) {
            actionsListView.append(action.getAction() + " ");
        }
    }

    private void startMatch() {
        // Start the chronometer
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
        // Add the START action to the actions list
        actions.add(new Action(ACTIONS[0], SystemClock.elapsedRealtime() - chronometer.getBase()));

        // Update the TextView
        updateActionsListView();
    }

    private void acquireBall() {
        // Add the ACQ action to the actions list
        actions.add(new Action(ACTIONS[1], SystemClock.elapsedRealtime() - chronometer.getBase()));

        // Update the TextView
        updateActionsListView();
    }

    private void scoreBall() {
        // Add the UHUB action to the actions list
        actions.add(new Action(ACTIONS[2], SystemClock.elapsedRealtime() - chronometer.getBase()));

        // Update the TextView
        updateActionsListView();
    }

    private void endMatch() {
        // Stop the chronometer
        chronometer.stop();

        // Add the STOP action to the actions list
        actions.add(new Action(ACTIONS[3], SystemClock.elapsedRealtime() - chronometer.getBase()));

        // Update the TextView
        updateActionsListView();

        StringBuilder sb = new StringBuilder();
        for (Action action : actions) {
            sb.append(action.getTimeString()).append(" ").append(action.getAction()).append("\n");
        }

        // Make pop-up with result
        new AlertDialog.Builder(this).setTitle("Result").setMessage(sb.toString()).show();
    }

    private static class Action {
        private final String action;
        private final long time;


        public Action(String action, long time) {
            this.action = action;
            this.time = time;
        }

        public String getAction() {
            return action;
        }

        public long getTime() {
            return time;
        }

        public String getTimeString() {
            return String.format(Locale.getDefault(), "%02d:%02d", time / 60000, (time % 60000) / 1000);
        }


    }
}

