package com.aemiralfath.moviecatalogue.ui.splashscreen

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.aemiralfath.moviecatalogue.R
import org.hamcrest.Matchers.allOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class SplashScreenActivityTest {

    @Before
    fun setUp() {
        ActivityScenario.launch(SplashScreenActivity::class.java)
    }

    @get:Rule
    var activityRule = ActivityScenarioRule(SplashScreenActivity::class.java)

    @Test
    fun loadMovie() {
        //splash screen
        onView(ViewMatchers.withId(R.id.img_splashscreen))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(isRoot()).perform(waitFor())


        onView(allOf(ViewMatchers.withId(R.id.rv_home_movie)))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

//        onView(ViewMatchers.withId(R.id.rv_home_movie)).perform(
//            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
//                20
//            )
//        )
    }

    private fun waitFor(): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): org.hamcrest.Matcher<View> {
                return isRoot();
            }

            override fun getDescription(): String {
                return "wait for " + 3000 + "milliseconds"
            }

            override fun perform(uiController: UiController, view: View?) {
                uiController.loopMainThreadForAtLeast(3000)
            }
        }
    }
}