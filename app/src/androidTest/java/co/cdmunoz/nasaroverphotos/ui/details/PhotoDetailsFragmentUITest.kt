package co.cdmunoz.nasaroverphotos.ui.details

import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import co.cdmunoz.nasaroverphotos.MainActivity
import co.cdmunoz.nasaroverphotos.R
import co.cdmunoz.nasaroverphotos.ui.home.PhotosAdapter
import co.cdmunoz.nasaroverphotos.utils.EspressoIdlingResourceRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class PhotoDetailsFragmentUITest {

    @get:Rule
    val activityTestRule = ActivityScenarioRule(MainActivity::class.java)

    @get:Rule
    val espressoIdlingResourceRule = EspressoIdlingResourceRule()

    @Test
    fun initial_state_photo_details_UI_test() {
        val textToMatch = "Earth's Date: "
        activityTestRule.scenario.moveToState(Lifecycle.State.RESUMED)
        with(onView(withId(R.id.home_photos_list))) {
            perform(RecyclerViewActions.scrollToPosition<PhotosAdapter.PhotosViewHolder>(0))
            perform(click())
        }
        onView(withText(textToMatch)).check(matches(isDisplayed()))
    }

    @Test
    fun back_button_test_UI_test() {
        initial_state_photo_details_UI_test()
        Espresso.pressBack()
        onView(withId(R.id.home_photos_list)).check(matches(isDisplayed()))
    }

    /* Going to general info screen after photo details then loading home again should display photo details initial state */
    @Test
    fun go_to_general_info_then_back_UI_test() {
        val textToMatch = "Earth's Date: "
        initial_state_photo_details_UI_test()
        onView(withId(R.id.nav_graph_info)).perform(click())
        onView(withId(R.id.nav_graph_home)).perform(click())
        onView(withText(textToMatch)).check(matches(isDisplayed()))
    }
}