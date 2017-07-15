package edu.gatech.seclass.sdpcryptogram;

import android.support.test.espresso.action.ViewActions;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by rigel on 7/14/17.
 */

public class TestSetup {

    public static void addPlayerRandy() throws InterruptedException {
        onView(withId(R.id.admin_radio)).perform(click());
        onView(withId(R.id.login_button)).perform(click());
        onView(withId(R.id.add_player)).perform(click());

        // add a valid new player (may not be new if the test has been run once)
        onView(withId(R.id.add_username)).perform(click());
        onView(withId(R.id.add_username)).perform(clearText(), typeText("randy"));
        onView(withId(R.id.add_first_name)).perform(clearText(), typeText("Randy"));
        onView(withId(R.id.add_last_name)).perform(clearText(), typeText("Marsh"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.save_player)).perform(click());
        Thread.sleep(2500);
        onView(withId(R.id.cancel_add_player)).perform(click());
    }

    public static void addCryptograms() throws InterruptedException {
        onView(withId(R.id.admin_radio)).perform(click());
        onView(withId(R.id.login_button)).perform(click());
        onView(withId(R.id.add_cryptogram)).perform(click());

        onView(withId(R.id.solution_phrase)).perform(clearText(), typeText("42 is the @nswer!"));
        onView(withId(R.id.encoded_phrase)).perform(clearText(), typeText("42 od yjr @mdert!"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.save_button)).perform(click());

        onView(withId(R.id.solution_phrase)).perform(clearText(), typeText("This is a cat."));
        onView(withId(R.id.encoded_phrase)).perform(clearText(), typeText("Yjod od s vsy."), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.save_button)).perform(click());

        onView(withId(R.id.cancel_button)).perform(click());
    }

}
