package co.cdmunoz.nasaroverphotos.ui.generalinfo

import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import co.cdmunoz.nasaroverphotos.MainActivity
import co.cdmunoz.nasaroverphotos.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GeneralInfoFragmentUITest {

    @get:Rule
    val activityTestRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun initial_state_general_info_UI_test() {
        activityTestRule.scenario.moveToState(Lifecycle.State.RESUMED)
        onView(withId(R.id.nav_graph_info)).perform(click())
        onView(withText(R.string.info_fragment_title)).check(matches(isDisplayed()))
    }
}