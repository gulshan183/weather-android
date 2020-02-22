package com.example.myapplication


import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.myapplication.ui.main.activity.MainActivity
import com.example.myapplication.util.DataBindingIdlingResource
import com.example.myapplication.util.EspressoIdlingResource
import com.example.myapplication.util.monitorActivity
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Gulshan Ahluwalia on 2020-02-07.
 */

/**
 * Test the navigation of complete app, along with different scenarios
 *
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class AppNavigationTest {


    private val dataBindingIdlingResource = DataBindingIdlingResource()

    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
        IdlingRegistry.getInstance().register(dataBindingIdlingResource)
    }


    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
        IdlingRegistry.getInstance().unregister(dataBindingIdlingResource)
    }

    @Test
    fun homeToCurrentWeather() {
        val scenario = ActivityScenario.launch<MainActivity>(MainActivity::class.java)
        dataBindingIdlingResource.monitorActivity(scenario)
        onView(withId(R.id.bt_step1)).perform(click())
        onView(withId(R.id.cl_current_weather)).check(matches(isDisplayed()))
    }

    @Test
    fun currentWeatherScreen_back() {
        val scenario = ActivityScenario.launch<MainActivity>(MainActivity::class.java)
        dataBindingIdlingResource.monitorActivity(scenario)
        onView(withId(R.id.bt_step1)).perform(click())
        onView(withId(R.id.cl_current_weather)).check(matches(isDisplayed()))
        pressBack()
        onView(withId(R.id.main)).check(matches(isDisplayed()))
    }

    @Test
    fun currentWeatherScreen_UIBack() {
        val scenario = ActivityScenario.launch<MainActivity>(MainActivity::class.java)
        dataBindingIdlingResource.monitorActivity(scenario)
        onView(withId(R.id.bt_step1)).perform(click())
        onView(withId(R.id.ti_search)).check(matches(isDisplayed()))
        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click())
        onView(withId(R.id.main)).check(matches(isDisplayed()))
    }

    @Test
    fun homeToWeatherForecast() {
        val scenario = ActivityScenario.launch<MainActivity>(MainActivity::class.java)
        dataBindingIdlingResource.monitorActivity(scenario)
        onView(withId(R.id.bt_step2)).perform(click())
        onView(withId(R.id.cl_weather_forecast)).check(matches(isDisplayed()))
    }

    @Test
    fun forecastWeatherScreen_back() {
        val scenario = ActivityScenario.launch<MainActivity>(MainActivity::class.java)
        dataBindingIdlingResource.monitorActivity(scenario)
        onView(withId(R.id.bt_step2)).perform(click())
        onView(withId(R.id.cl_weather_forecast)).check(matches(isDisplayed()))
        pressBack()
        onView(withId(R.id.main)).check(matches(isDisplayed()))
    }

    @Test
    fun forecastWeatherScreen_UIBack() {
        val scenario = ActivityScenario.launch<MainActivity>(MainActivity::class.java)
        dataBindingIdlingResource.monitorActivity(scenario)
        onView(withId(R.id.bt_step2)).perform(click())
        onView(withId(R.id.cl_weather_forecast)).check(matches(isDisplayed()))
        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click())
        onView(withId(R.id.main)).check(matches(isDisplayed()))
    }
}
