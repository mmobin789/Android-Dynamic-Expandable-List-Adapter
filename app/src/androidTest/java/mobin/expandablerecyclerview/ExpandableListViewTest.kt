package mobin.expandablerecyclerview

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
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
    val mainActivity = getRule()

    fun getRule() = ActivityTestRule(MainActivity::class.java)
    @Test
    fun testExpandableListClick() {
        val randomIndex = Random.nextInt(10)
        val root = RootMatchers.withDecorView(Matchers.`is`(mainActivity.activity.window.decorView))
        onView(withId(R.id.rvM)).inRoot(root).perform(
            RecyclerViewActions.actionOnItemAtPosition<MyAdapter.PViewHolder>(
                randomIndex,
                click()
            )
        )

    }

    @Test
    fun testExpandableListChildClick() {
        val randomIndex = Random.nextInt(10)
        val root = RootMatchers.withDecorView(Matchers.`is`(mainActivity.activity.window.decorView))
        onView(withId(R.id.rvM)).inRoot(root).perform(
            RecyclerViewActions.actionOnItemAtPosition<MyAdapter.PViewHolder>(
                randomIndex,
                click()
            )
        )

    }
}
