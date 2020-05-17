package co.cdmunoz.nasaroverphotos.utils

import androidx.test.espresso.IdlingRegistry
import co.cdmunoz.nasaroverphotos.utils.test.EspressoIdlingResource
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class EspressoIdlingResourceRule : TestWatcher() {

    private val idlingResource = EspressoIdlingResource.countingIdlingResource

    override fun starting(description: Description?) {
        IdlingRegistry.getInstance().register(idlingResource)
        super.starting(description)
    }

    override fun finished(description: Description?) {
        IdlingRegistry.getInstance().unregister(idlingResource)
        super.starting(description)
    }
}