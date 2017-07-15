package edu.gatech.seclass.sdpcryptogram;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import edu.gatech.seclass.utilities.ExternalWebService;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static edu.gatech.seclass.sdpcryptogram.TestSetup.addPlayerRandy;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class Test3ViewCryptograms {

    @Rule
    public final ActivityTestRule<edu.gatech.seclass.sdpcryptogram.LoginActivity> main = new ActivityTestRule<>(edu.gatech.seclass.sdpcryptogram.LoginActivity.class);

    /**
     * Test for SDPCryptogram
     * @author - mintaka438@gmail.com
     * Generated using Barista - http://moquality.com/barista
     */


    @Test
    public void test1_ViewCryptograms() throws InterruptedException {
        // if randy is not existing, create it
        List<String> extPlayerNames = ExternalWebService.getInstance().playernameService();
        if (!extPlayerNames.contains("randy")) {
            addPlayerRandy();
        }
        // log in as `randy`
        onView(withId(R.id.player_radio)).perform(click());
        onView(withId(R.id.username)).perform(click());
        onView(withId(R.id.username)).perform(clearText(), typeText("randy"));
        onView(withId(R.id.login_button)).perform(click());
        Thread.sleep(1000);

        onView(withId(R.id.available_cryptograms_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        Thread.sleep(1000);
        onView(withId(R.id.back_cryptogram_button)).perform(click());

        onView(withId(R.id.available_cryptograms_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        Thread.sleep(1000);
        onView(withId(R.id.back_cryptogram_button)).perform(click());

        onView(withId(R.id.available_cryptograms_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));
        Thread.sleep(1000);
        onView(withId(R.id.back_cryptogram_button)).perform(click());

        onView(withId(R.id.available_cryptograms_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(3, click()));
        Thread.sleep(1000);
        onView(withId(R.id.back_cryptogram_button)).perform(click());
    }
}
