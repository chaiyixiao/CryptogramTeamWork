package edu.gatech.seclass.sdpcryptogram;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static edu.gatech.seclass.sdpcryptogram.TestUtil.addPlayerRandy;
import static edu.gatech.seclass.sdpcryptogram.TestUtil.setTextWithID;

/**
 * Created by rigel on 7/15/17.
 */

public class Test5PlayCryptogram {

    @Rule
    public final ActivityTestRule<LoginActivity> main = new ActivityTestRule<>(edu.gatech.seclass.sdpcryptogram.LoginActivity.class);

//    @BeforeClass
//    public static void mySetup() throws InterruptedException {
//        addCryptogramsForTest();
//    }

    @Test
    public void test1_SubmitCorrectSolution () throws InterruptedException {
        // create a new user with a random username
        String username = addPlayerRandy("random");

        onView(withId(R.id.player_radio)).perform(click());
        onView(withId(R.id.username)).perform(click());
        onView(withId(R.id.username)).perform(clearText(), typeText(username));
        onView(withId(R.id.login_button)).perform(click());

        // select the fourth cryptogram to solve
        onView(withId(R.id.available_cryptograms_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(4, click()));

        onView(withId(R.id.play_cryptogram_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, setTextWithID(R.id.cryptogram_my_letter, "T")));
        onView(withId(R.id.play_cryptogram_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, setTextWithID(R.id.cryptogram_my_letter, "h")));
        onView(withId(R.id.play_cryptogram_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(2, setTextWithID(R.id.cryptogram_my_letter, "i")));
        onView(withId(R.id.play_cryptogram_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(3, setTextWithID(R.id.cryptogram_my_letter, "s")));
        onView(withId(R.id.play_cryptogram_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(8, setTextWithID(R.id.cryptogram_my_letter, "a")));

        // incomplete submission
        onView(withId(R.id.submit_cryptogram_button)).perform(click());
        Thread.sleep(2000);

        onView(withId(R.id.play_cryptogram_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(10, setTextWithID(R.id.cryptogram_my_letter, "d")));
        onView(withId(R.id.play_cryptogram_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(5, setTextWithID(R.id.cryptogram_my_letter, "i")));
        onView(withId(R.id.play_cryptogram_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(6, setTextWithID(R.id.cryptogram_my_letter, "s")));
        onView(withId(R.id.play_cryptogram_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(11, setTextWithID(R.id.cryptogram_my_letter, "a")));
        onView(withId(R.id.play_cryptogram_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(12, setTextWithID(R.id.cryptogram_my_letter, "t")));

        // incorrect submission
        onView(withId(R.id.submit_cryptogram_button)).perform(click());
        Thread.sleep(2000);

        // check total incorrect number
//        onView(withId(R.id.back_cryptogram_button)).perform(click());
//        Thread.sleep(2000);
//        onView(withId(R.id.incorrect_num)).check(matches(withText("1")));

        // select the fourth cryptogram to correct the solution
//        onView(withId(R.id.available_cryptograms_recycler_view))
//                .perform(RecyclerViewActions.actionOnItemAtPosition(4, click()));
        onView(withId(R.id.play_cryptogram_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(10, setTextWithID(R.id.cryptogram_my_letter, "c")));

        // correct submission
        onView(withId(R.id.submit_cryptogram_button)).perform(click());
        Thread.sleep(2000);

        // check the number of solved cryptograms
        onView(withId(R.id.back_cryptogram_button)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.solved_num)).check(matches(withText("1")));
        onView(withId(R.id.started_num)).check(matches(withText("1")));
        onView(withId(R.id.incorrect_num)).check(matches(withText("1")));
    }
}
