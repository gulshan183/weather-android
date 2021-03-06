package com.example.myapplication.ui.weather.forecast

import android.content.Context
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.GrantPermissionRule
import com.example.myapplication.R
import com.example.myapplication.util.DataBindingIdlingResource
import com.example.myapplication.util.RecyclerViewItemCountAssertion
import com.example.myapplication.util.monitorFragment
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Gulshan Ahluwalia on 2020-02-06.
 */
@RunWith(AndroidJUnit4::class)
class ForecastWeatherFragmentTest {
    private lateinit var appContext: Context

    private lateinit var dataBindingIdlingResource : DataBindingIdlingResource

    @get:Rule
    var permissionRule: GrantPermissionRule =
        GrantPermissionRule.grant(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )

    @Before
    fun registerIdlingResource() {
        appContext = InstrumentationRegistry.getInstrumentation().targetContext
        dataBindingIdlingResource = DataBindingIdlingResource()
        IdlingRegistry.getInstance().register(dataBindingIdlingResource)
    }

    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(dataBindingIdlingResource)
    }


    @Test
    fun dataVisible_today() {
        val scenario = launchFragmentInContainer<ForecastWeatherFragment>()
        dataBindingIdlingResource.monitorFragment(scenario)
        onView(withId(R.id.list))
            .perform(
                RecyclerViewActions.scrollToPosition<DaysForecastAdapter.ViewHolder>(0)
            )
        onView(withText(appContext.getString(R.string.today)))
            .check(matches(isDisplayed()))
    }

    @Test
    fun dataVisible_tomorrow() {
        val scenario = launchFragmentInContainer<ForecastWeatherFragment>()
        dataBindingIdlingResource.monitorFragment(scenario)
        onView(withId(R.id.list))
            .perform(
                RecyclerViewActions.scrollToPosition<DaysForecastAdapter.ViewHolder>(0)
            )
        onView(withText(appContext.getString(R.string.tomorrow)))
            .check(matches(isDisplayed()))
    }

    @Test
    fun hoursData_visible() {
        val scenario = launchFragmentInContainer<ForecastWeatherFragment>()
        dataBindingIdlingResource.monitorFragment(scenario)
        onView(allOf(isDisplayed(), withId(R.id.hours_list)))
    }

    @Test
    fun data_totalCount() {
        val scenario = launchFragmentInContainer<ForecastWeatherFragment>()
        dataBindingIdlingResource.monitorFragment(scenario)
        onView(withId(R.id.list)).check(RecyclerViewItemCountAssertion(2))
    }

}