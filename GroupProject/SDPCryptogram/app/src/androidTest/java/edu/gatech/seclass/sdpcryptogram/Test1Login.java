package edu.gatech.seclass.sdpcryptogram;

import edu.gatech.seclass.sdpcryptogram.LoginActivity;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static android.support.test.espresso.assertion.ViewAssertions.*;
import static org.hamcrest.Matchers.*;
import android.test.suitebuilder.annotation.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.runner.RunWith;

/**
 * @author - baskerk@yahoo.com
 * Generated using Barista - http://moquality.com/barista
 *
 * Test login
 * This will verify that the administator and valid players can login
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class Test1Login {

    @Rule
    public final ActivityTestRule<edu.gatech.seclass.sdpcryptogram.LoginActivity> main = new ActivityTestRule<>(edu.gatech.seclass.sdpcryptogram.LoginActivity.class);


    @Test
    public void test_AdministratorLogin() {
        onView(withId(R.id.admin_radio)).perform(click());
        onView(withId(R.id.login_button)).perform(click());
        onView(withId(R.id.add_player)).check(matches(isDisplayed()));
    }

}
