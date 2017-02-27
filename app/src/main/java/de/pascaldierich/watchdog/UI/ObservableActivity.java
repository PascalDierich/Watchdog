package de.pascaldierich.watchdog.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import de.pascaldierich.watchdog.R;

public class ObservableActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observable);
    }

    @Override
    protected void onStart() {
        super.onStart();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.observableList_container, new ObservableListFragment(), "")
                .commit();
    }
}
