package de.pascaldierich.model.services.plus;

import org.junit.Test;

import java.io.IOException;

import de.pascaldierich.model.network.ConstantsApi;
import de.pascaldierich.model.network.models.plus.activities.PlusActivitiesPage;
import de.pascaldierich.model.network.models.plus.activities.item.PlusActivitiesItem;
import de.pascaldierich.model.network.models.plus.activities.item.actor.PlusActivitiesItemActor;
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
//            "113944603497026777117",
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

        CHECK_FOR_ELEMENTS(plusActivitiesPage);
    }

    private void CHECK_FOR_ELEMENTS(PlusActivitiesPage page) {
        assertTrue(page.getKind().equalsIgnoreCase("plus#activityFeed"));
        assertTrue(page.getEtag() != null);
        assertTrue(page.getTitle() != null);
//        assertTrue(page.getUpdated() != null);
//        assertTrue(page.getId().equals("113944603497026777117")
//                    || page.getId().equals("106460744327009487967")
//                    || page.getId().equals("107736861938601589615"));
        assertTrue(page.getItems() != null);
        System.out.println("Header Check passed");

        if (!page.getItems().isEmpty()) {
            for (PlusActivitiesItem item : page.getItems()) {
                assertTrue(item.getKind().equalsIgnoreCase("plus#activity"));
                assertTrue(item.getTitle() != null);
                assertTrue(item.getPublished() != null);
                assertTrue(item.getUrl() != null);
                assertTrue(item.getActor() != null);
                assertTrue(item.getVerb() != null);
                assertTrue(item.getObject() != null);
                System.out.println("Item Check passed");

                CHECK_FOR_ACTOR(item.getActor());

                assertTrue(item.getActor().getImage().getUrl() != null);
                System.out.println("Actor Item Check passed");

                assertTrue(item.getObject().getUrl() != null);
                assertTrue(item.getObject().getActor() != null);
                assertTrue(item.getObject().getContent() != null);
                assertTrue(item.getObject().getObjectType() != null);
                System.out.println("Object Item Check passed");

                CHECK_FOR_ACTOR(item.getObject().getActor());
            }
        }


//        assertTrue(page.);
    }

    private void CHECK_FOR_ACTOR(PlusActivitiesItemActor actor) {
        assertTrue(actor.getDisplayName() != null);
        assertTrue(actor.getUrl() != null);
        assertTrue(actor.getImage() != null);
        System.out.println("Actor Check passed");
    }
}
