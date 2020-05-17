package co.cdmunoz.nasaroverphotos.utils

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher

object EspressoTestsHelpers {
    fun recyclerViewSizeMatcher(matcherSize: Int): Matcher<View?>? {
        return object : BoundedMatcher<View?, RecyclerView>(RecyclerView::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("with list size: $matcherSize")
            }

            override fun matchesSafely(recyclerView: RecyclerView): Boolean {
                return matcherSize == recyclerView.adapter!!.itemCount
            }
        }
    }
}