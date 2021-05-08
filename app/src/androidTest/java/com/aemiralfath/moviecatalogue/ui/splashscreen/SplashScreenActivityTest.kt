package com.aemiralfath.moviecatalogue.ui.splashscreen

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
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
    fun loadMoviesAndTvShow() {
        onView(withId(R.id.rv_home_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_home_movie)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(15)
        )
        onView(withId(R.id.view_pager)).perform(swipeLeft())
        onView(withText(R.string.tab_text_1)).perform(click())

        onView(withText(R.string.tab_text_2)).perform(click())
        onView(withId(R.id.rv_home_tv)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_home_tv)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(15)
        )
        onView(withId(R.id.view_pager)).perform(swipeRight())
    }

    @Test
    fun loadDetailMovie() {
        onView(withId(R.id.rv_home_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_home_movie)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(15)
        )

        onView(withId(R.id.rv_home_movie)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(15, click())
        )

        onView(withId(R.id.img_movie_poster)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_movie_title)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_movie_date)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_movie_rating)).check(matches(isDisplayed()))

        Espresso.pressBack()
        onView(withId(R.id.view_pager)).perform(swipeLeft())
        onView(withText(R.string.tab_text_1)).perform(click())
    }

    @Test
    fun loadDetailTv() {
        onView(withText(R.string.tab_text_2)).perform(click())
        onView(withId(R.id.rv_home_tv))
            .check(matches(isDisplayed()))
        onView(withId(R.id.rv_home_tv)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(15)
        )

        onView(withId(R.id.rv_home_tv)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(15, click())
        )

        onView(withId(R.id.img_tv_poster)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_tv_title)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_tv_date)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_tv_rating)).check(matches(isDisplayed()))

        Espresso.pressBack()
        onView(withId(R.id.view_pager)).perform(swipeRight())
    }

    @Test
    fun loadFavoriteMovieAndTv() {
        onView(withId(R.id.action_list_favorite)).perform(click())

        onView(withId(R.id.rv_home_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.view_pager_favorite)).perform(swipeLeft())

        onView(withId(R.id.rv_home_tv)).check(matches(isDisplayed()))
        onView(withId(R.id.view_pager_favorite)).perform(swipeRight())

        onView(withId(R.id.rv_home_movie)).check(matches(isDisplayed()))
        onView(withText(R.string.tab_text_2)).perform(click())

        onView(withId(R.id.rv_home_tv)).check(matches(isDisplayed()))
        onView(withText(R.string.tab_text_1)).perform(click())

        Espresso.pressBack()
    }

    @Test
    fun addOrRemoveMovieFavorite() {
        onView(withId(R.id.action_list_favorite)).perform(click())
        onView(withId(R.id.rv_home_movie)).check(matches(isDisplayed()))
        Espresso.pressBack()

        onView(withId(R.id.rv_home_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_home_movie)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10)
        )

        onView(withId(R.id.rv_home_movie)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(10, click())
        )

        onView(withId(R.id.action_favorite)).perform(click())

        Espresso.pressBack()
        onView(withId(R.id.action_list_favorite)).perform(click())
        onView(withId(R.id.rv_home_movie)).check(matches(isDisplayed()))
    }

    @Test
    fun addOrRemoveTvFavorite() {
        onView(withId(R.id.action_list_favorite)).perform(click())
        onView(withId(R.id.view_pager_favorite)).perform(swipeLeft())
        onView(withId(R.id.rv_home_tv)).check(matches(isDisplayed()))
        Espresso.pressBack()

        onView(withId(R.id.view_pager)).perform(swipeLeft())
        onView(withId(R.id.rv_home_tv)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_home_tv)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10)
        )

        onView(withId(R.id.rv_home_tv)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(10, click())
        )

        onView(withId(R.id.action_favorite)).perform(click())

        Espresso.pressBack()
        onView(withId(R.id.action_list_favorite)).perform(click())
        onView(withId(R.id.view_pager_favorite)).perform(swipeLeft())
        onView(withId(R.id.rv_home_tv)).check(matches(isDisplayed()))
    }

    @Test
    fun sortMovie() {
        onView(withId(R.id.rv_home_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_home_movie)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(15)
        )

        openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext())
        onView(withText(R.string.menu_oldest)).perform(click())
        onView(withId(R.id.rv_home_movie)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(5)
        )
        onView(withId(R.id.rv_home_movie)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(15)
        )

        openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext())
        onView(withText(R.string.menu_character)).perform(click())
        onView(withId(R.id.rv_home_movie)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(5)
        )
        onView(withId(R.id.rv_home_movie)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(15)
        )

        onView(withId(R.id.view_pager)).perform(swipeLeft())
        onView(withText(R.string.tab_text_1)).perform(click())
    }

    @Test
    fun sortTv() {
        onView(withId(R.id.view_pager)).perform(swipeLeft())
        onView(withId(R.id.rv_home_tv)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_home_tv)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(15)
        )

        openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext())
        onView(withText(R.string.menu_oldest)).perform(click())
        onView(withId(R.id.rv_home_tv)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(5)
        )
        onView(withId(R.id.rv_home_tv)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(15)
        )

        openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext())
        onView(withText(R.string.menu_character)).perform(click())
        onView(withId(R.id.rv_home_tv)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(5)
        )
        onView(withId(R.id.rv_home_tv)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(15)
        )

        onView(withText(R.string.tab_text_1)).perform(click())
    }

    @Test
    fun searchMovie() {
        onView(withId(R.id.rv_home_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_home_movie)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(20)
        )

        onView(withId(R.id.sv_movie)).perform(click())
        onView(withId(R.id.sv_movie)).perform(typeText("justice"))
        onView(withId(R.id.rv_home_movie)).check(matches(isDisplayed()))
    }

    @Test
    fun searchTv() {
        onView(withId(R.id.view_pager)).perform(swipeLeft())
        onView(withId(R.id.rv_home_tv)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_home_tv)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(20)
        )

        onView(withId(R.id.sv_tv)).perform(click())
        onView(withId(R.id.sv_tv)).perform(typeText("doctor"))
        onView(withId(R.id.rv_home_tv)).check(matches(isDisplayed()))
    }
}