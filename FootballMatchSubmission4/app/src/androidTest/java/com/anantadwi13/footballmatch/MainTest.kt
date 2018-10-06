package com.anantadwi13.footballmatch

import android.support.test.espresso.Espresso.*
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.anantadwi13.footballmatch.main.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainTest {
    @Rule
    @JvmField var activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testRecyclerView(){
        onView(withId(R.id.lastMatch)).check(matches(isDisplayed()))
        onView(withId(R.id.lastMatch)).perform(click())
        onView(withId(R.id.recyclerview)).check(matches(isDisplayed()))
        Thread.sleep(2000)
        onView(withId(R.id.recyclerview)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
        Thread.sleep(2000)
        onView(withId(R.id.recyclerview)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(10,click()))
        Thread.sleep(1000)
        onView(withId(R.id.add_to_favorite)).perform(click())
        onView(withText("Added to favorite!")).check(matches(isDisplayed()))
        Thread.sleep(1000)
        onView(withId(R.id.add_to_favorite)).perform(click())
        onView(withText("Removed from favorite!")).check(matches(isDisplayed()))
        Thread.sleep(1000)
        onView(withId(R.id.add_to_favorite)).perform(click())
        Thread.sleep(2000)
        pressBack()

        onView(withId(R.id.nextMatch)).check(matches(isDisplayed()))
        onView(withId(R.id.nextMatch)).perform(click())
        onView(withId(R.id.recyclerview)).check(matches(isDisplayed()))
        Thread.sleep(2000)
        onView(withText("Man United")).perform(click())
        Thread.sleep(1000)
        onView(withId(R.id.add_to_favorite)).perform(click())
        onView(withText("Added to favorite!")).check(matches(isDisplayed()))
        Thread.sleep(1000)
        onView(withId(R.id.add_to_favorite)).perform(click())
        onView(withText("Removed from favorite!")).check(matches(isDisplayed()))
        Thread.sleep(1000)
        onView(withId(R.id.add_to_favorite)).perform(click())
        Thread.sleep(2000)
        pressBack()

        onView(withId(R.id.favMatch)).check(matches(isDisplayed()))
        onView(withId(R.id.favMatch)).perform(click())
        onView(withId(R.id.recyclerview)).check(matches(isDisplayed()))
        Thread.sleep(500)
        onView(withText("Man United")).check(matches(isDisplayed()))
        onView(withText("Man United")).perform(click())
        Thread.sleep(1000)
        pressBack()
        Thread.sleep(10000)
    }
}