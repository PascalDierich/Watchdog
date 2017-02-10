package de.pascaldierich.model.services.plus;

import org.junit.Test;

import java.io.IOException;

import de.pascaldierich.model.network.ConstantsApi;
import de.pascaldierich.model.network.models.pages.PlusPeoplePage;
import de.pascaldierich.model.network.services.PlusService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.junit.Assert.assertTrue;

/**
 * Created by Pascal Dierich on Feb, 2017.
 */
public class PlusServicePeople {

    String[] peoples = {
            "PascalDierich",
            "Pascal Dierich",
            "Bill Gates",
            "Hans Peter",
            "Dr. No",
            "&9402",
            "     ",
//            "", // --> Bad Request
            "?=)(/&&%$§§§421234405406"
    };

    @Test
    public void PLUS_API_PEOPLE_NOT_NULL() {
        for (String people : peoples) {
            TEST_WITH_NAME(people);
        }
    }

    private void TEST_WITH_NAME(String people) {
        HttpLoggingInterceptor mLoggingInterceptor = new HttpLoggingInterceptor();
        mLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(mLoggingInterceptor).build();

        Retrofit mGoogleClient = new Retrofit.Builder()
                .baseUrl(ConstantsApi.GOOGLE_BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PlusService plusService = mGoogleClient.create(PlusService.class);
        PlusPeoplePage plusPeoplePage = null;
        try {
            plusPeoplePage =
                    plusService.getPeople(ConstantsApi.PLUS_API_KEY,
                            people,
                            5)
                            .execute()
                            .body();

            assertTrue(plusPeoplePage != null);

            System.out.println("NullPointer Check passed");

        } catch (IOException ioe) {
            assertTrue("IOException", false);
        }

//        CHECK_FOR_ELEMENTS(youTubeChannelsPage);

    }


}
