package com.android.currencyconverter.view

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.android.currencyconverter.R
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity>
            = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun onStartProgressBarShouldDisplayed() {
        onView(withId(R.id.progressBar)).check(matches(isDisplayed()))
    }

    @Test
    fun onStartAppNameShouldDisplayed() {
        onView(withId(R.id.appName)).check(matches(withText("Currency Converter")))
    }

    @Test
    fun loaderShouldVisibleAndRetryButtonShouldBeHideWhenTrue() {
        activityRule.scenario.onActivity { it.updateLoadingView(true) }
        onView(withId(R.id.progressBar)).check(matches(isDisplayed()))
        onView(withId(R.id.retry)).check(matches(not(isDisplayed())))
    }

    @Test
    fun retryButtonShouldVisibleAndLoaderShouldBeHideWhenTrue() {
        activityRule.scenario.onActivity { it.updateLoadingView(false) }
        onView(withId(R.id.retry)).check(matches(isDisplayed()))
        onView(withId(R.id.progressBar)).check(matches(not(isDisplayed())))
    }
}