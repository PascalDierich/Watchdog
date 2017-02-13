package de.pascaldierich.model.local;

import android.support.test.runner.AndroidJUnit4;
import android.test.ProviderTestCase2;

import org.junit.runner.RunWith;

/**
 * Created by Pascal Dierich on Feb, 2017.
 */

@RunWith(AndroidJUnit4.class)
public class ProviderTest extends ProviderTestCase2<WatchdogProvider> {

    /**
     * Constructor.
     *
     * @param providerClass     The class name of the provider under test
     * @param providerAuthority The provider's authority string
     */
    public ProviderTest(Class<WatchdogProvider> providerClass, String providerAuthority) {
        super(providerClass, providerAuthority);
    }


}
