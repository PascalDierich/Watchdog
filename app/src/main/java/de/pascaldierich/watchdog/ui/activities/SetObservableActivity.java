package de.pascaldierich.watchdog.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import de.pascaldierich.watchdog.R;
import de.pascaldierich.watchdog.ui.fragments.SetObservableFragment;

/**
 * Activity to add or change Observables
 * TODO: will get moved to a Fragment (-> twoPaneMode)
 */
public class SetObservableActivity extends AppCompatActivity {
    
    private static final String SET_OBSERVABLE_FRAGMENT_TAG = "SO_FragmentTag";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_observable);
    
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.setObservable_container, new SetObservableFragment(), SET_OBSERVABLE_FRAGMENT_TAG)
                .commit();
    }
}

