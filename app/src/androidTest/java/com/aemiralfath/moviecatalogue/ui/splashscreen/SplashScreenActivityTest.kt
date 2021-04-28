package com.aemiralfath.moviecatalogue.ui.splashscreen

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.aemiralfath.moviecatalogue.R
import com.aemiralfath.moviecatalogue.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class SplashScreenActivityTest {

    @get:Rule
    var activityRule = ActivityScenarioRule(SplashScreenActivity::class.java)

    @Before
    fun setUp() {
        ActivityScenario.launch(SplashScreenActivity::class.java)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }

    @Test
    fun loadAndScrollMovies() {
        onView(withId(R.id.rv_home_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_home_movie)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10)
        )
        onView(withId(R.id.view_pager)).perform(swipeLeft())
        onView(withText(R.string.tab_text_1)).perform(click())
    }

    @Test
    fun loadAndScrollTvShow() {
        onView(withText(R.string.tab_text_2)).perform(click())
        onView(withId(R.id.rv_home_tv)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_home_tv)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10)
        )
        onView(withId(R.id.view_pager)).perform(swipeRight())
    }

    @Test
    fun loadDetailMovie() {
        onView(withId(R.id.rv_home_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_home_movie)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10)
        )

        onView(withId(R.id.rv_home_movie)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(10, click())
        )

        onView(withId(R.id.img_movie_poster)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_movie_title)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_movie_date)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_movie_rating)).check(matches(isDisplayed()))

        pressBack()
        onView(withId(R.id.view_pager)).perform(swipeLeft())
        onView(withText(R.string.tab_text_1)).perform(click())
    }

    @Test
    fun loadDetailTv() {
        onView(withText(R.string.tab_text_2)).perform(click())
        onView(withId(R.id.rv_home_tv))
            .check(matches(isDisplayed()))
        onView(withId(R.id.rv_home_tv)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10)
        )

        onView(withId(R.id.rv_home_tv)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(10, click())
        )

        onView(withId(R.id.img_tv_poster)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_tv_title)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_tv_date)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_tv_rating)).check(matches(isDisplayed()))

        pressBack()
        onView(withId(R.id.view_pager)).perform(swipeRight())
    }

}