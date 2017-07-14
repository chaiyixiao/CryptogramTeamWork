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


/**
 * Test functions of administrator
 * @author - mintaka438@gmail.com
 * Generated using Barista - http://moquality.com/barista
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class Test2Administrator {

    @Rule
    public final ActivityTestRule<edu.gatech.seclass.sdpcryptogram.LoginActivity> main = new ActivityTestRule<>(edu.gatech.seclass.sdpcryptogram.LoginActivity.class);


    @Test
    public void test_AdminLogin() {
        onView(withId(R.id.admin_radio)).perform(click());
        onView(withId(R.id.login_button)).perform(click());
    }

    @Test
    public void test_AddPlayer() {
        onView(withId(R.id.add_player)).perform(click());
        onView(withId(R.id.add_username)).perform(click());
        onView(withId(R.id.add_username)).perform(clearText(), typeText("kenny"));
        onView(withId(R.id.add_first_name)).perform(clearText(), typeText("Kenny"));
        onView(withId(R.id.add_last_name)).perform(clearText(), typeText("White"));
        onView(withId(R.id.save_player)).perform(click());
        onView(withId(R.id.cancel_add_player)).perform(click());
    }

    @Test
    public void testAddCryptogram() {
        onView(withId(R.id.add_cryptogram)).perform(click());
        onView(withId(R.id.encoded_phrase)).perform(click());
        onView(withId(R.id.solution_phrase)).perform(clearText(), typeText("This is a cat."));
        onView(withId(R.id.encoded_phrase)).perform(clearText(), typeText("Yjod od s vsy."));
        onView(withId(R.id.save_button)).perform(click());
        onView(withId(R.id.cancel_button)).perform(click());
    }
}
