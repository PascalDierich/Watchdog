package de.pascaldierich.model.services.plus;

import org.junit.Test;

import java.io.IOException;

import de.pascaldierich.model.network.ConstantsApi;
import de.pascaldierich.model.network.models.pages.PlusActivitiesPage;
import de.pascaldierich.model.network.services.PlusService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.junit.Assert.assertTrue;

/**
 * Created by Pascal Dierich on Feb, 2017.
 */
public class PlusServiceActivities {

    String[] ids = {
            "113944603497026777117",
            "106460744327009487967",
            "107736861938601589615"
    };

    @Test
    public void PLUS_API_ACTIVITIES_NOT_NULL() {
        for (String id : ids) {
            TEST_WITH_ID(id);
        }
    }

    private void TEST_WITH_ID(String id) {
        HttpLoggingInterceptor mLoggingInterceptor = new HttpLoggingInterceptor();
        mLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(mLoggingInterceptor).build();

        Retrofit mGoogleClient = new Retrofit.Builder()
                .baseUrl(ConstantsApi.GOOGLE_BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PlusService plusService = mGoogleClient.create(PlusService.class);
        PlusActivitiesPage plusActivitiesPage = null;
        try {
            plusActivitiesPage =
                    plusService.getActivities(id,
                            ConstantsApi.PLUS_COLLECTION,
                            ConstantsApi.PLUS_API_KEY,
                            5)
                            .execute()
                            .body();

            assertTrue(plusActivitiesPage != null);

            System.out.println("NullPointer Check passed");

        } catch (IOException ioe) {
            assertTrue("IOException", false);
        }

//        CHECK_FOR_ELEMENTS(plusActivitiesPage);
    }



}
