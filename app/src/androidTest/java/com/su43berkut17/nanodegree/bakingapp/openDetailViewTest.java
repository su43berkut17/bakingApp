package com.su43berkut17.nanodegree.bakingapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class openDetailViewTest {
    @Rule public ActivityTestRule<MainActivity> mActivityTestRule
            =new ActivityTestRule<>(MainActivity.class);

    @Test
    public void openFirstStep(){
        //we open the 1st item
        onView(withId(R.id.rvRecipes)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));

        onView(withId(R.id.rvRecipeSteps)).check(matches(hasDescendant(withText("Recipe Introduction"))));

        //we open the ingredient section
        onView(withId(R.id.rvRecipeSteps)).perform(RecyclerViewActions.actionOnItemAtPosition(1,click()));

        onView(withId(R.id.btn_next_step)).check(matches(withText("NEXT STEP")));
    }

    @Test
    public void openLastStep(){
        //we open the 1st item
        onView(withId(R.id.rvRecipes)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));

        onView(withId(R.id.rvRecipeSteps)).check(matches(hasDescendant(withText("Recipe Introduction"))));

        //we open the ingredient section
        onView(withId(R.id.rvRecipeSteps)).perform(RecyclerViewActions.actionOnItemAtPosition(7,click()));

        onView(withId(R.id.btn_previous_step)).check(matches(withText("PREVIOUS STEP")));
    }
}
