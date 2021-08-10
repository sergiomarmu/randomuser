package com.sermarmu.randomuser.ui.feature.user

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sermarmu.randomuser.R
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * After run this class be sure that mobile is on and unlocked otherwise
 * a java.lang.RuntimeException will be threw
 *
 * This test is designed to testing UI with items already loaded.
 * Tested on a real device
 */

@RunWith(AndroidJUnit4::class)
class CharacterFragmentTest {

    private lateinit var navController: NavController

    @Before
    fun setUp() {
        navController = TestNavHostController(
            ApplicationProvider.getApplicationContext())

        launchFragmentInContainer<UserFragment>(
            themeResId = R.style.AppTheme
        ).onFragment {
            navController.setGraph(R.navigation.nav_main_activity_graph)

            Navigation.setViewNavController(
                it.requireView(),
                navController
            )
        }
    }

    @Test
    fun shouldClickLoadUserButton() {
        onView(withId(R.id.fnb_load_more_users))
            .check(ViewAssertions.matches(isDisplayed()))

        onView(withId(R.id.fnb_load_more_users))
            .perform(click())
    }

    @Test
    fun shouldClickSearchViewAndTypeSomething() {
        onView(withId(R.id.tie_users_search))
            .check(ViewAssertions.matches(isDisplayed()))

        onView(withId(R.id.tie_users_search))
            .perform(typeText("Something"), closeSoftKeyboard())
    }

    @Test
    fun shouldScrollToPosition() {
        val position = 3

        onView(withId(R.id.rv_users))
            .check(ViewAssertions.matches(isDisplayed()))

        onView(withId(R.id.rv_users))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(position))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(position.plus(5)))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(position.minus(8)))

    }

    @Test
    fun shouldClickFirsItemGoFragmentDetailAndGoBack() {
        val position = 1

        onView(withId(R.id.rv_users))
            .check(ViewAssertions.matches(isDisplayed()))

        onView(withId(R.id.rv_users))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    position,
                    click()
                )
            )

        pressBack()
    }

}