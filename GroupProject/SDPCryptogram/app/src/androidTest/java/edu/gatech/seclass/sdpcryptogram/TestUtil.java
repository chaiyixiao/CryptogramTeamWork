package edu.gatech.seclass.sdpcryptogram;

import android.support.annotation.NonNull;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

import java.security.SecureRandom;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.core.deps.guava.base.Preconditions.checkNotNull;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by rigel on 7/14/17.
 */

public class TestUtil {

    public static Matcher<View> atPosition(final int position, @NonNull final Matcher<View> itemMatcher) {
        checkNotNull(itemMatcher);
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("has item at position " + position + ": ");
                itemMatcher.describeTo(description);
            }

            @Override
            protected boolean matchesSafely(final RecyclerView view) {
                RecyclerView.ViewHolder viewHolder = view.findViewHolderForAdapterPosition(position);
                if (viewHolder == null) {
                    // has no item on such position
                    return false;
                }
                return itemMatcher.matches(viewHolder.itemView);
            }
        };
    }

    private  static final String availableChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private  static SecureRandom randomG = new SecureRandom();
    private static String randomString(int len){
        StringBuilder sb = new StringBuilder( len );
        for( int i = 0; i < len; i++ )
            sb.append(availableChars.charAt(randomG.nextInt(availableChars.length()) ) );
        return sb.toString();
    }

    public static String addPlayerRandy(String username) throws InterruptedException {

        if (username.equals("random")) {
            username = randomString(8);
        }

        onView(withId(R.id.admin_radio)).perform(click());
        onView(withId(R.id.login_button)).perform(click());
        onView(withId(R.id.add_player)).perform(click());

        // add a valid new player (may not be new if the test has been run once)
        onView(withId(R.id.add_username)).perform(click());
        onView(withId(R.id.add_username)).perform(clearText(), typeText(username));
        onView(withId(R.id.add_first_name)).perform(clearText(), typeText("Randy"));
        onView(withId(R.id.add_last_name)).perform(clearText(), typeText("Marsh"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.save_player)).perform(click());
        Thread.sleep(1000);
        onView(withId(R.id.cancel_add_player)).perform(click());
        Espresso.pressBack();

        return username;
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
