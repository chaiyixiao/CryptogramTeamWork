package edu.gatech.seclass.sdpcryptogram;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * @author - Basker and Longfei
 * Generated using Barista - http://moquality.com/barista
 * This will verify that the administator and valid players can login
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class Test1Login {

    @Rule
    public final ActivityTestRule<edu.gatech.seclass.sdpcryptogram.LoginActivity> main = new ActivityTestRule<>(edu.gatech.seclass.sdpcryptogram.LoginActivity.class);

    // check admin login
    @Test
    public void test1_AdminLogin() throws InterruptedException {
        onView(withId(R.id.admin_radio)).perform(click());
        onView(withId(R.id.login_button)).perform(click());
        onView(withId(R.id.add_player)).check(matches(isDisplayed()));
        Thread.sleep(1500);
    }

    // check empty username login
    @Test
    public void test2_EmptyPlayerLogin() throws InterruptedException {
        onView(withId(R.id.player_radio)).perform(click());
        onView(withId(R.id.login_button)).perform(click());
        onView(withId(R.id.username)).check(matches(hasErrorText("please enter a valid username")));
        Thread.sleep(1500);
    }

    // check invalid username login
    @Test
    public void test3_InvalidPlayerLogin() throws InterruptedException {
        onView(withId(R.id.player_radio)).perform(click());
        onView(withId(R.id.username)).perform(typeText("invalid"));
        onView(withId(R.id.login_button)).perform(click());
        onView(withId(R.id.username)).check(matches(hasErrorText("please enter a valid username")));
        Thread.sleep(1500);
    }

    // check that players created on other phones cannot login in this phone
    @Test
    public void test4_OtherPlayerLogin() throws InterruptedException {
        onView(withId(R.id.player_radio)).perform(click());
        onView(withId(R.id.username)).perform(typeText("example555"));
        onView(withId(R.id.login_button)).perform(click());
        onView(withId(R.id.username)).check(matches(hasErrorText("please enter a valid username")));
        Thread.sleep(1500);
    }

    // check that a valid player can log in
    @Test
    public void test5_ValidPlayerLogin() {
        // This test cannot be carried out before the administrator create a valid player,
        // so this test is fulfilled in `Test2Administrator`.
    }

}
