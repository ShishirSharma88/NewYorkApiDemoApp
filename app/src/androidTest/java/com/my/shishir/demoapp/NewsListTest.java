package com.my.shishir.demoapp;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;

import com.my.shishir.demoapp.utility.Utility;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * This class include small test cases for both positive and negative scenarios
 */
@RunWith(AndroidJUnit4.class)
public class NewsListTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule
            = new ActivityTestRule<MainActivity>(MainActivity.class);


    // This is just if recyclerView got initialized or not
    @Test
    public void testRecyclerViewExist() {
        RecyclerView recyclerView
                = (RecyclerView) mainActivityActivityTestRule.getActivity()
                .findViewById(R.id.list_news);

        assertNotNull(recyclerView);
    }

    // This test case is for testing view(eg. recyclerView) or UI after
    // adapter setup completely.
    @Test
    public void testRecyclerViewItemsAfterDataSet() throws InterruptedException {

        // Waiting for Api call and Adapter for setup completely
        Thread.sleep(15000);

        RecyclerView recyclerView
                = (RecyclerView) mainActivityActivityTestRule.getActivity()
                .findViewById(R.id.list_news);

        assertNotNull(recyclerView.getAdapter());
        assertEquals(recyclerView.getChildCount() > 0, true);
    }

    // This is a negative small test case in which we change url to empty
    // and check for the toast message to get appear on the screen
    @Test
    public void testRecyclerViewItemsDataSetupFail() throws InterruptedException {

        // It is added here to maintain a gap or delay
        // in second api call by refresh button after activity get started
        Thread.sleep(8000);

        Utility.BASE_URL = "aaaaaa";

        onView(withId(R.id.swipe_layout_refresh)).perform(swipeDown());
        onView(withText(R.string.download_failed)).
                inRoot(withDecorView(not(is(mainActivityActivityTestRule.getActivity().getWindow().getDecorView())))).
                check(matches(isDisplayed()));
    }
}
