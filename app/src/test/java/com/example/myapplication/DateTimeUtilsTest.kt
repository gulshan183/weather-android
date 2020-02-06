package com.example.myapplication

import android.app.Application
import android.os.Build
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.myapplication.util.getDateInUIFormat
import com.example.myapplication.util.getTime
import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

/**
 * Created by Gulshan Ahluwalia on 2020-02-06.
 */
@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P])
class DateTimeUtilsTest {
    private lateinit var context: Application

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun getDateInUIFormat_testToday() {
        val date = getDateInUIFormat(System.currentTimeMillis() / 1000, context)
        Truth.assertThat(date).isEqualTo(context.getString(R.string.today))
    }

    @Test
    fun getDateInUIFormat_testTomorrow() {
        val date =
            getDateInUIFormat((System.currentTimeMillis() + (1000 * 60 * 60 * 25)) / 1000, context)
        Truth.assertThat(date).isEqualTo(context.getString(R.string.tomorrow))
    }

    @Test
    fun getDateInUIFormat_testOtherDay() {
        val date = getDateInUIFormat(
            (System.currentTimeMillis() + ((1000 * 60 * 60 * 25) * 2)) / 1000,
            context
        )
        assert(date != context.getString(R.string.tomorrow) && date != context.getString(R.string.tomorrow))
    }

    @Test
    fun getDateInUIFormat() {
        val time = getTime((System.currentTimeMillis() + ((1000 * 60 * 60 * 25) * 2)) / 1000)
        assert(time?.contains("M") == true)
    }
}