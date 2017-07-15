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
    public void test1_SubmitCorrectSolution () {
        onView(withId(R.id.username)).perform(click());
        onView(withId(R.id.username)).perform(clearText(), typeText("randy"));
        onView(withId(R.id.login_button)).perform(click());
        onView(withId(R.id.available_cryptograms_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(5, click()));

        onView(withId(R.id.play_cryptogram_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, clearText()));
        onView(withId(R.id.play_cryptogram_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, typeText("T")));

        onView(withId(R.id.play_cryptogram_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, clearText()));
        onView(withId(R.id.play_cryptogram_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, typeText("h")));

        onView(withId(R.id.play_cryptogram_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(2, clearText()));
        onView(withId(R.id.play_cryptogram_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(2, typeText("i")));

        onView(withId(R.id.play_cryptogram_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(3, clearText()));
        onView(withId(R.id.play_cryptogram_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(3, typeText("s")));

        onView(withId(R.id.play_cryptogram_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(8, clearText()));
        onView(withId(R.id.play_cryptogram_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(8, typeText("a")));

        onView(withId(R.id.play_cryptogram_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(9, clearText()));
        onView(withId(R.id.play_cryptogram_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(9, typeText("c")));

        onView(withId(R.id.submit_cryptogram_button)).perform(click());
        onView(withId(R.id.back_cryptogram_button)).perform(click());

        onView(withId(R.id.solved_num)).check(matches(withText("1")));
    }
}
