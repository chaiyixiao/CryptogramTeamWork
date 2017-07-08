package edu.gatech.seclass.sdpcryptogram;

import edu.gatech.seclass.sdpcryptogram.LoginActivity;
import edu.gatech.seclass.sdpcryptogram.AdminAddCryptogramActivity;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static android.support.test.espresso.assertion.ViewAssertions.*;
import static org.hamcrest.Matchers.*;
import android.test.suitebuilder.annotation.LargeTest;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
//import android.support.test.espresso.contrib.RecyclerViewActions;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class SDPCryptogramTest {

    @Rule
    public final ActivityTestRule<edu.gatech.seclass.sdpcryptogram.LoginActivity> main = new ActivityTestRule<>(edu.gatech.seclass.sdpcryptogram.LoginActivity.class);

    /**
     * Test for SDPCryptogram
     * @author - baskerk@yahoo.com
     * Generated using Barista - http://moquality.com/barista
     *
     * Test Administrator login
     * This will verify that the Admin can login
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
     *
     * Test player login
     * This will verify that the player can login
     *
     */
    @Test
    public void test_SDPCryptogramTest2() {
        onView(withId(R.id.admin_radio)).perform(click());
        onView(withId(R.id.login_button)).perform(click());
        onView(withId(R.id.add_player)).perform(click());
        onView(withId(R.id.add_username)).perform(click());
        onView(withId(R.id.add_username)).perform(clearText(), typeText("u1"));
        onView(withId(R.id.add_first_name)).perform(clearText(), typeText("f1"));
        onView(withId(R.id.add_last_name)).perform(clearText(), typeText("l1"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.save_player)).perform(click());
        onView(withId(R.id.cancel_add_player)).perform(click());
        Espresso.pressBack();
        onView(withId(R.id.player_radio)).perform(click());
        onView(withId(R.id.username)).perform(click());
        onView(withId(R.id.username)).perform(clearText(), typeText("u1"));
        onView(withId(R.id.login_button)).perform(click());
        onView(withId(R.id.available_cryptograms_recycler_view)).check(matches(isDisplayed()));
//        onView(withId(R.id.admin_radio)).perform(click());
//        onView(withId(R.id.login_button)).perform(click());
//        onView(withId(R.id.action_bar)).check(matches(isDisplayed()));
//        onView(withId(R.id.add_cryptogram)).perform(click());
//        onView(withText("Encoded Phrase:")).check(matches(isDisplayed()));
//        onView(withText("Solution Phrase:")).check(matches(isDisplayed()));
//        onView(withId(R.id.encoded_phrase)).perform(click());
//        onView(withId(R.id.encoded_phrase)).perform(click());
//        onView(withId(R.id.encoded_phrase)).perform(clearText(), typeText("abc"), ViewActions.closeSoftKeyboard());
//        onView(withId(R.id.reset_button)).perform(click());
//        onView(withId(R.id.encoded_phrase)).check(matches(withText("")));

    }

    /**
     * Test for SDPCryptogram
     * @author - baskerk@yahoo.com
     * Generated using Barista - http://moquality.com/barista
     *
     * Test Administrator options
     * verify that addPlayer and addCrypogram options are available
     */
    @Test
    public void test_SDPCryptogramTest3() {
        onView(withId(R.id.admin_radio)).perform(click());
        onView(withId(R.id.login_button)).perform(click());
        onView(withId(R.id.add_player)).check(matches(isDisplayed()));
        onView(withId(R.id.add_cryptogram)).check(matches(isDisplayed()));

    }

    /**
     * Test for SDPCryptogram
     * @author - baskerk@yahoo.com
     * Generated using Barista - http://moquality.com/barista
     *
     * Persist Data across multiple runs - does not work due to External Webservice
     */
    @Test
    public void test_SDPCryptogramTest4() {
    }

    /**
     * Test for SDPCryptogram
     * @author - baskerk@yahoo.com
     * Generated using Barista - http://moquality.com/barista
     *
     * Share Updated Player Ratings - does not work due to External Webservice
     */
    @Test
    public void test_SDPCryptogramTest5() {
    }


//    @Rule
//    public final ActivityTestRule<edu.gatech.seclass.sdpcryptogram.AdminAddCryptogramActivity> adminAddCryptogramActivityRule = new ActivityTestRule<>(edu.gatech.seclass.sdpcryptogram.AdminAddCryptogramActivity.class);
    /**
     * Test for SDPCryptogram
     * @author - baskerk@yahoo.com
     * Generated using Barista - http://moquality.com/barista
     *
     * Receive unique id for cryptograms.
     * Verify that a new cryptogram can be added with a new Id
     */
    @Test
    public void test_SDPCryptogramTest6() {
        onView(withId(R.id.admin_radio)).perform(click());
        onView(withId(R.id.login_button)).perform(click());
        onView(withId(R.id.add_cryptogram)).perform(click());
        onView(withId(R.id.encoded_phrase)).perform(clearText(), typeText("uesu"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.solution_phrase)).perform(clearText(), typeText("test"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.save_button)).perform(click());

        //unable to check the text from a Toast
//        AdminAddCryptogramActivity activity = new ActivityTestRule<>(edu.gatech.seclass.sdpcryptogram.AdminAddCryptogramActivity.class).getActivity();
//        onView(withText(startsWith("Added"))).
//                inRoot(withDecorView(not(is(activity.getWindow().getDecorView())))).
//                check(matches(isDisplayed()));
        onView(withId(R.id.cancel_button)).perform(click());
        Espresso.pressBack();
    }

    /**
     * Test for SDPCryptogram
     * @author - baskerk@yahoo.com
     * Generated using Barista - http://moquality.com/barista
     *
     * Solve a cryptogram.
     */
    @Test
    public void test_SDPCryptogramTest7() {
        onView(withId(R.id.player_radio)).perform(click());
        onView(withId(R.id.username)).perform(click());
        onView(withId(R.id.username)).perform(clearText(), typeText("u1"));
        onView(withId(R.id.login_button)).perform(click());
        onView(withId(R.id.available_cryptograms_recycler_view)).check(matches(isDisplayed()));
//        onView(withId(R.id.play_cryptogram_recycler_view)).perform(RecyclerViewActions.scrollToPosition(0));
        //unable to check the text from a Toast
//        AdminAddCryptogramActivity activity = new ActivityTestRule<>(edu.gatech.seclass.sdpcryptogram.AdminAddCryptogramActivity.class).getActivity();
//        onView(withText(startsWith("Added"))).
//                inRoot(withDecorView(not(is(activity.getWindow().getDecorView())))).
//                check(matches(isDisplayed()));
        onView(withId(R.id.cancel_button)).perform(click());
        Espresso.pressBack();
    }

}