package com.example.myapplication.util

import com.google.common.truth.Truth
import org.junit.Test

/**
 * Created by Gulshan Ahluwalia on 2020-02-07.
 */

class TextUtilsTest {
    @Test
    fun toTitleCase() {
        Truth.assertThat("Clear sky".toTitleCase()).isEqualTo("Clear Sky")
    }

    @Test
    fun toWindFormat() {
        Truth.assertThat("21.2".toWindFormat()).isEqualTo("21.2 m/s")
    }

    @Test
    fun toDegreeFormat() {
        Truth.assertThat("21.2".toDegreeFormat()).isEqualTo("21.2\u00B0")
    }

    @Test
    fun getMinMaxDegreeFormat(){
        Truth.assertThat(getMinMaxDegreeFormat("2","1")).
            isEqualTo("2\u00B0 / 1\u00B0")
    }
}