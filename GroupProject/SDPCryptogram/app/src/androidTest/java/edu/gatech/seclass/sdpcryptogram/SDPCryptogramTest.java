package edu.gatech.seclass.sdpcryptogram;

import edu.gatech.seclass.sdpcryptogram.LoginActivity;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static android.support.test.espresso.assertion.ViewAssertions.*;
import static org.hamcrest.Matchers.*;
import android.test.suitebuilder.annotation.LargeTest;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class SDPCryptogramTest {

    @Rule
    public final ActivityTestRule<edu.gatech.seclass.sdpcryptogram.LoginActivity> main = new ActivityTestRule<>(edu.gatech.seclass.sdpcryptogram.LoginActivity.class);

    /**
     * Test for SDPCryptogram
     * @author - baskerk@yahoo.com
     * Generated using Barista - http://moquality.com/barista
     */
    @Test
    public void test_SDPCryptogramTest1() {
        onView(withId(R.id.admin_radio)).perform(click());
        onView(withId(R.id.login_button)).perform(click());
        onView(withId(R.id.add_player)).check(matches(isDisplayed()));

    }


    /**
     * Test for SDPCryptogram
     * @author - baskerk@yahoo.com
     * Generated using Barista - http://moquality.com/barista
     */
    @Test
    public void test_SDPCryptogramTest2() {
        onView(withId(R.id.admin_radio)).perform(click());
        onView(withId(R.id.login_button)).perform(click());
        onView(withId(R.id.action_bar)).check(matches(isDisplayed()));
        onView(withId(R.id.add_cryptogram)).perform(click());
        onView(withText("Encoded Phrase:")).check(matches(isDisplayed()));
        onView(withText("Solution Phrase:")).check(matches(isDisplayed()));
        onView(withId(R.id.encoded_phrase)).perform(click());
        onView(withId(R.id.encoded_phrase)).perform(click());
        onView(withId(R.id.encoded_phrase)).perform(clearText(), typeText("abc"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.reset_button)).perform(click());
        onView(withId(R.id.encoded_phrase)).check(matches(withText("")));

    }

}