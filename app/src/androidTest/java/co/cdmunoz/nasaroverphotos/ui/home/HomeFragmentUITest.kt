package co.cdmunoz.nasaroverphotos.ui.home

import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import co.cdmunoz.nasaroverphotos.MainActivity
import co.cdmunoz.nasaroverphotos.R
import co.cdmunoz.nasaroverphotos.utils.EspressoIdlingResourceRule
import co.cdmunoz.nasaroverphotos.utils.EspressoTestsHelpers
import org.hamcrest.CoreMatchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class HomeFragmentUITest {

    @get:Rule
    val activityTestRule = ActivityScenarioRule(MainActivity::class.java)

    @get:Rule
    val espressoIdlingResourceRule = EspressoIdlingResourceRule()

    @Test
    fun initial_state_home_screen_UI_test() {
        activityTestRule.scenario.moveToState(Lifecycle.State.RESUMED)
        onView(withId(R.id.home_photos_progress_container)).check(matches(not(isDisplayed())))
        with(onView(withId(R.id.home_photos_list))) {
            check(matches(isDisplayed()))
            check(matches(EspressoTestsHelpers.recyclerViewSizeMatcher(25)))
        }
    }

    @Test
    fun scroll_to_eleventh_item_and_click_UI_test() {
        activityTestRule.scenario.moveToState(Lifecycle.State.RESUMED)
        with(onView(withId(R.id.home_photos_list))) {
            perform(RecyclerViewActions.scrollToPosition<PhotosAdapter.PhotosViewHolder>(10))
            perform(click())
        }
    }
}