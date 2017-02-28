package de.pascaldierich.watchdog.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import de.pascaldierich.watchdog.R;
import de.pascaldierich.watchdog.ui.fragments.ObservableListFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.observableList_container, new ObservableListFragment(), "")
                .commit();
    }
}
