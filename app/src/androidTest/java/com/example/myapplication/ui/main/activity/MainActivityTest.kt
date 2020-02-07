package com.example.myapplication.ui.main.activity

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.myapplication.R
import com.example.myapplication.util.DataBindingIdlingResource
import com.example.myapplication.util.monitorActivity
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Gulshan Ahluwalia on 2020-02-06.
 */
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    val appContext by lazy {
        InstrumentationRegistry.getInstrumentation().targetContext
    }

    private val dataBindingIdlingResource = DataBindingIdlingResource()

    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(dataBindingIdlingResource)
    }

    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(dataBindingIdlingResource)
    }


    @Test
    fun toolbar_nameVisible() {
        val scenario = ActivityScenario.launch<MainActivity>(MainActivity::class.java)
        dataBindingIdlingResource.monitorActivity(scenario)
        Espresso.onView(ViewMatchers.withText(appContext.getString(R.string.app_name)))
            .check(matches(isDisplayed()))
    }


}