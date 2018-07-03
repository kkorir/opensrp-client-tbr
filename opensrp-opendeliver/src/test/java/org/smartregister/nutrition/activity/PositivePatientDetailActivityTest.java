package org.smartregister.nutrition.activity;

import android.content.Intent;
import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.robolectric.Robolectric;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.android.controller.ActivityController;
import org.smartregister.nutrition.BaseUnitTest;

import static junit.framework.TestCase.assertNotNull;
import static org.powermock.api.mockito.PowerMockito.spy;

/**
 * Created by ndegwamartin on 18/12/2017.
 */

public class PositivePatientDetailActivityTest extends BaseUnitTest {

    private ActivityController<PositivePatientDetailActivity> controller;
    private PositivePatientDetailActivity activity;

    @Before
    public void setUp() {
        org.mockito.MockitoAnnotations.initMocks(this);
        Intent intent = new Intent(RuntimeEnvironment.application, PositivePatientDetailActivity.class);
        controller = Robolectric.buildActivity(PositivePatientDetailActivity.class, intent);
        activity = controller.get();
        controller.setup();
    }

    private void destroyController() {
        try {
            activity.finish();
            controller.pause().stop().destroy(); //destroy controller if we can

        } catch (Exception e) {
            Log.e(getClass().getCanonicalName(), e.getMessage());
        }
    }

    @After
    public void tearDown() {
        destroyController();
    }

    @Test
    public void activitySetUpCorrectlyWithNoException() {

        PositivePatientDetailActivity spyActivity = spy(activity);
        assertNotNull(spyActivity);
    }
}