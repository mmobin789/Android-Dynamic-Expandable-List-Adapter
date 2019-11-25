package mobin.expandablerecyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.BoundedMatcher
import mobin.expandablerecyclerview.adapters.getRecyclerView
import org.hamcrest.Description
import org.hamcrest.Matcher

object TestUtils {
    fun clickActionOnChildRecyclerView(index: Int) = object : ViewAction {
        override fun getConstraints(): Matcher<View>? {
            return null
        }

        override fun getDescription(): String {
            return "Click On Child RV @ $index"
        }

        override fun perform(uiController: UiController?, view: View?) {
            val rvChild = view?.getRecyclerView()
            ViewActions.click().perform(
                uiController,
                rvChild?.findViewHolderForAdapterPosition(index)?.itemView
            )

        }
    }


    fun atPosition(position: Int, itemMatcher: Matcher<View>): Matcher<View> {
        return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("has item at position $position: ")
                itemMatcher.describeTo(description)
            }

            override fun matchesSafely(view: RecyclerView): Boolean {
                val viewHolder = view.findViewHolderForAdapterPosition(position)
                    ?: // has no item on such position
                    return false
                return itemMatcher.matches(viewHolder.itemView)
            }
        }
    }

    fun atChildPosition(position: Int, itemMatcher: Matcher<View>): Matcher<View> {
        return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("has item at position $position: ")
                itemMatcher.describeTo(description)
            }

            override fun matchesSafely(view: RecyclerView): Boolean {
                val viewHolder =
                    view.findViewHolderForAdapterPosition(position)?.itemView?.getRecyclerView()?.findViewHolderForAdapterPosition(
                        position
                    )
                        ?: // has no item on such position
                        return false
                return itemMatcher.matches(viewHolder.itemView)
            }
        }
    }
}