package com.example.myapplication.util

import android.view.inputmethod.EditorInfo
import android.widget.EditText

/**
 * Created by Gulshan Ahluwalia on 2020-02-05.
 */

fun EditText.onSubmit(func: () -> Unit) {
    setOnEditorActionListener { _, actionId, _ ->

        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            func()
        }

        true

    }
}