package mobin.expandablerecyclerview

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import kotlinx.android.synthetic.main.activity_main.*
import mobin.expandablerecyclerview.adapters.MyAdapter
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test
import kotlin.random.Random

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

class ExpandableListViewTest {
    @get:Rule
    val mainActivity = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testExpandableListClick() {
        val randomParentIndex = Random.nextInt(10)
        val randomChildIndex = Random.nextInt(10)
        val root = RootMatchers.withDecorView(Matchers.`is`(mainActivity.activity.window.decorView))
        val parentViewClickAction =
            RecyclerViewActions.actionOnItemAtPosition<MyAdapter.PViewHolder>(
                randomParentIndex,
                click()
            )
        val parentRV = mainActivity.activity.rvM

        onView(withId(parentRV.id)).inRoot(root).perform(parentViewClickAction)

        val myAdapter = parentRV.adapter as MyAdapter

        val childViewClickAction =
            RecyclerViewActions.actionOnItemAtPosition<MyAdapter.CViewHolder>(
                randomChildIndex,
                click()
            )


    }

}
