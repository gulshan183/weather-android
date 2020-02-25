package com.example.myapplication.ui.main.fragment

import android.content.Context
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.myapplication.R
import com.example.myapplication.util.DataBindingIdlingResource
import com.example.myapplication.util.monitorFragment
import org.hamcrest.CoreMatchers.containsString
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Gulshan Ahluwalia on 2020-02-06.
 */
@RunWith(AndroidJUnit4::class)
class MainFragmentTest {

    private lateinit var appContext: Context

    private lateinit var dataBindingIdlingResource : DataBindingIdlingResource

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


    /**
     * Assert that navigation items are displaying
     *
     */
    @Test
    fun mainMenu_visible() {
        val scenario = launchFragmentInContainer<MainFragment>()
        dataBindingIdlingResource.monitorFragment(scenario)

        Espresso.onView(ViewMatchers.withId(R.id.bt_step1))
            .check(matches(withText(containsString(appContext.getString(R.string.current_weather)))))
        Espresso.onView(ViewMatchers.withId(R.id.bt_step2))
            .check(matches(withText(containsString(appContext.getString(R.string.weather_forecast)))))
    }

}