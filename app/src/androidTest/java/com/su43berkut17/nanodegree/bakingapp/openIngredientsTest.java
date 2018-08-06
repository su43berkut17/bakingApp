package com.su43berkut17.nanodegree.bakingapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class openIngredientsTest {
    @Rule public ActivityTestRule<MainActivity> mActivityTestRule
    =new ActivityTestRule<>(MainActivity.class);

    @Test
    public void clickRecipe(){
        //we open the 1st item
        onView(withId(R.id.rvRecipes)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));

        onView(withId(R.id.rvRecipeSteps)).check(matches(hasDescendant(withText("Ingredients"))));

        //we open the ingredient section
        onView(withId(R.id.rvRecipeSteps)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));

        onView(withId(R.id.rvIngredientList)).check(matches(hasDescendant(withText("1 - Graham Cracker crumbs"))));
    }
}
