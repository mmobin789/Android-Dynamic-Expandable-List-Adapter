package mobin.expandablerecyclerview

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import mobin.expandablerecyclerview.adapters.MyAdapter
import mobin.expandablerecyclerview.adapters.getRecyclerView
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test
import kotlin.random.Random

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

class ExpandableListViewTest {

    @Rule
    @JvmField
    val mActivityTestRule = ActivityTestRule(MainActivity::class.java)


    @Test
    fun testExpandableListClick() {
        val randomIndex = Random.nextInt(10)
        onView(withId(R.id.rvM)).perform(
            RecyclerViewActions.actionOnItemAtPosition<MyAdapter.PViewHolder>(
                randomIndex,
                click()
            )
        )

    }

    @Test
    fun testExpandableListText() {
        val randomIndex = Random.nextInt(10)
//        onView(withId(R.id.rvM)).perform(
//            RecyclerViewActions.actionOnItemAtPosition<MyAdapter.PViewHolder>(
//                randomIndex,
//                )
//        )

    }

    @Test
    fun testExpandedListClick() {
        val randomIndex = Random.nextInt(10)
//        val root =
//            RootMatchers.withDecorView(Matchers.`is`(mActivityTestRule.activity.window.decorView))


        onView(withId(R.id.rvM)).perform(
            RecyclerViewActions.actionOnItemAtPosition<MyAdapter.PViewHolder>(
                randomIndex,
                click()
            )
        )

        onView(withId(R.id.rvM)).perform(
            RecyclerViewActions.actionOnItemAtPosition<MyAdapter.PViewHolder>(
                randomIndex,
                clickActionOnChildRecyclerView(randomIndex)
            )
        )

    }

//    private fun eMatcher() = object : TypeSafeMatcher<View>() {
//        override fun describeTo(description: Description?) {
//
//        }
//
//        override fun matchesSafely(item: View?): Boolean {
//
//        }
//
//    }

    private fun clickActionOnChildRecyclerView(index: Int) = object : ViewAction {
        override fun getConstraints(): Matcher<View>? {
            return null
        }

        override fun getDescription(): String {
            return "Click On Child RV @ $index"
        }

        override fun perform(uiController: UiController?, view: View?) {
            val rvChild = view?.getRecyclerView()
            click().perform(
                uiController,
                rvChild?.findViewHolderForAdapterPosition(index)?.itemView
            )

        }
    }


}
