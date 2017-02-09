package de.pascaldierich.watchdog.ui;

import android.app.Activity;
import android.os.Bundle;

import de.pascaldierich.watchdog.R;

public class ObservableActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observable);
    }
}
