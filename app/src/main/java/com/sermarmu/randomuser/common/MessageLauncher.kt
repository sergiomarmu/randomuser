package com.sermarmu.randomuser

import android.content.Context
import android.view.View
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar
import com.sermarmu.data.handler.DataException
import java.util.*

interface MessageLauncher {
    fun showPositive(
        view: View,
        @StringRes stringId: Int
    )

    fun showNegative(
        view: View,
        throwable: Throwable
    )
}

class MessageLauncherImpl(
    private val context: Context
) : MessageLauncher {

    override fun showPositive(
        view: View,
        @StringRes stringId: Int
    ) {
        Snackbar.make(
            view,
            context.getString(stringId)
                .toUpperCase(Locale.ROOT),
            Snackbar.LENGTH_LONG
        ).show()
    }

    override fun showNegative(
        view: View,
        throwable: Throwable
    ) {
        Snackbar.make(
            view,
            context.getString(
                when (throwable) {
                    is DataException ->
                        R.string.error_no_internet_connection
                    else ->
                        R.string.error_unknown
                }
            ).toUpperCase(Locale.ROOT),
            Snackbar.LENGTH_LONG
        ).show()
    }
}