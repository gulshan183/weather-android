package com.example.myapplication.ui.weather.current

import android.content.Context
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import androidx.test.platform.app.InstrumentationRegistry
import com.example.myapplication.R
import com.example.myapplication.ui.weather.forecast.DaysForecastAdapter
import com.example.myapplication.util.DataBindingIdlingResource
import com.example.myapplication.util.RecyclerViewItemCountAssertion
import com.example.myapplication.util.monitorFragment
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Gulshan Ahluwalia on 2020-02-07.
 */
@RunWith(AndroidJUnit4::class)
@MediumTest
class CurrentWeatherFragmentTest {
    private lateinit var appContext: Context

    private lateinit var dataBindingIdlingResource : DataBindingIdlingResource

    @Before
    fun registerIdlingResource() {
        appContext = InstrumentationRegistry.getInstrumentation().targetContext
        dataBindingIdlingResource = DataBindingIdlingResource()
        //IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
        IdlingRegistry.getInstance().register(dataBindingIdlingResource)
    }

    @After
    fun unregisterIdlingResource() {
        //IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
        IdlingRegistry.getInstance().unregister(dataBindingIdlingResource)
    }


    @Test
    fun viewVisible() {
        val scenario =
            launchFragmentInContainer<CurrentWeatherFragment>(themeResId = R.style.AppTheme)
        dataBindingIdlingResource.monitorFragment(scenario)
        onView(withId(R.id.ti_search)).check(matches(isDisplayed()))
    }

    @Test
    fun initViewState() {
        val scenario =
            launchFragmentInContainer<CurrentWeatherFragment>(themeResId = R.style.AppTheme)
        dataBindingIdlingResource.monitorFragment(scenario)
        onView(withId(R.id.list)).check(RecyclerViewItemCountAssertion(0))
    }

    @Test
    fun searchCities_validate() {
        val scenario =
            launchFragmentInContainer<CurrentWeatherFragment>(themeResId = R.style.AppTheme)
        dataBindingIdlingResource.monitorFragment(scenario)
        onView(withId(R.id.et_search)).perform(
            clearText(),
            typeText("Ludhiana,New york,ABC"),
            pressImeActionButton()
        )
        onView(withId(R.id.list)).check(RecyclerViewItemCountAssertion(3))
    }

    @Test
    fun searchCities_listValidate() {
        val scenario =
            launchFragmentInContainer<CurrentWeatherFragment>(themeResId = R.style.AppTheme)
        dataBindingIdlingResource.monitorFragment(scenario)
        onView(withId(R.id.et_search)).perform(
            clearText(),
            typeText("Ludhiana,New york,ABC"),
            pressImeActionButton()
        )
        onView(withId(R.id.list))
            .perform(
                RecyclerViewActions.scrollToPosition<DaysForecastAdapter.ViewHolder>(0)
            )
        onView(allOf(isDisplayed(), withText("London")))
    }

    @Test
    fun searchCities_validationErrorsMin() {
        val scenario =
            launchFragmentInContainer<CurrentWeatherFragment>(themeResId = R.style.AppTheme)
        dataBindingIdlingResource.monitorFragment(scenario)
        onView(withId(R.id.et_search)).perform(
            clearText(),
            typeText("Ludhiana,New york"),
            pressImeActionButton()
        )
        onView(withText(appContext.getString(R.string.cities_min_error))).check(matches(isDisplayed()))
    }

    @Test
    fun searchCities_validationErrorsMax() {
        val scenario =
            launchFragmentInContainer<CurrentWeatherFragment>(themeResId = R.style.AppTheme)
        dataBindingIdlingResource.monitorFragment(scenario)
        onView(withId(R.id.et_search)).perform(
            clearText(),
            typeText("Ludhiana,New york,acd,das,port blair,jalandhar,luckhnow,Jamshedpur,New delhi"),
            pressImeActionButton()
        )
        onView(withText(appContext.getString(R.string.cities_max_error))).check(matches(isDisplayed()))
    }

}