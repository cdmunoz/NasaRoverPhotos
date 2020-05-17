package co.cdmunoz.nasaroverphotos.ui.splash

import android.content.Intent
import androidx.core.content.edit
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import co.cdmunoz.nasaroverphotos.R
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class SplashActivityUITest {

    @Rule
    @JvmField
    var activityTestRule = ActivityTestRule(SplashActivity::class.java, true, false)

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val preferences = context.getSharedPreferences(SplashActivity.NASA_ROVER_PREFS, 0)
        preferences.edit(true) {
            clear()
        }
    }

    @Test
    fun initial_state_splash_screen_UI_test() {
        activityTestRule.launchActivity(Intent())
        onView(withId(R.id.splash_mars_rover_img)).check(matches(isDisplayed()))
        onView(withText(R.string.nasa_mars_rover_photos_lbl)).check(matches(isDisplayed()))
    }
}