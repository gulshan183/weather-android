package com.example.myapplication.util

import android.app.Activity
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText


/**
 * Created by Gulshan Ahluwalia on 2020-02-05.
 */

/**
 * Config the [EditText] to handle the search IME action.
 * Once the user click search button on soft keyboard the func will be executed.
 *
 * @param func
 */
fun EditText.onSubmit(func: () -> Unit) {

    setOnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            func()
            hideKeyboardFrom(this)
            false
        } else false
    }
}

/**
 * Hide soft keyboard on a particular action
 *
 * @param view
 */
fun hideKeyboardFrom(view: View) {
    val imm: InputMethodManager =
        view.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}