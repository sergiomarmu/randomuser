package com.sermarmu.randomuser.extensions

import android.view.View
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

fun EditText.debounce(
    delay: Long = 1_000L,
    coroutineScope: CoroutineScope,
    action: (String) -> Unit
) {
    var debounceJob: Job? = null
    debounceJob = coroutineScope.launch {
        debounceJob?.cancel()
        doAfterTextChanged {
            postDelayed({ action(it.toString()) }, delay)
        }
    }
}

fun TextInputEditText.readOnlyMode(
    text: String
) {
    setText(text)
    isClickable = false
    isFocusable = false
    isFocusableInTouchMode = false
    isCursorVisible = false
    isSingleLine = true
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}