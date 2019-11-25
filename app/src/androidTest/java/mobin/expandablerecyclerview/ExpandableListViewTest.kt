package mobin.expandablerecyclerview

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import mobin.expandablerecyclerview.TestUtils.atChildPosition
import mobin.expandablerecyclerview.TestUtils.atPosition
import mobin.expandablerecyclerview.TestUtils.clickActionOnChildRecyclerView
import mobin.expandablerecyclerview.adapters.MyAdapter
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

    /**
     * Test click on expandable listing.
     * Assertion: List is expanded or not.
     * Passing Criteria: state can be either expandable or expanded.
     */
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

    /**
     * Test click on expanded listing
     * Assertion: List is expandable not expanded.
     * Passing Criteria: List must be not be in expanded state.
     */
    @Test
    fun testExpandedListClick() {
        val randomIndex = Random.nextInt(10)


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

    /**
     * Test text on expandable listing
     * Assertion: List is expanded or not.
     * Passing Criteria: state can be either expandable or expanded.
     */
    @Test
    fun testExpandableText() {
        val randomIndex = Random.nextInt(10)
        onView(withId(R.id.rvM))
            .check(matches(atPosition(randomIndex, hasDescendant(withText("Parent $randomIndex")))))
    }

    /**
     * Test text on expanded listing
     * Assertion: List is expanded.
     * Passing Criteria: List must be in expanded state.
     */
    // todo this method is buggy must be stable before back-merge to master.
    @Test
    fun testExpandedText() {
        val randomIndex = Random.nextInt(10)
        onView(withId(R.id.rvM))
            .check(matches(atChildPosition(randomIndex, withText("Child $randomIndex"))))
    }


}
