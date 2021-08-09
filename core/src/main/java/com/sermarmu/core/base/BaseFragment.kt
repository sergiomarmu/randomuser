package com.sermarmu.core.base

import android.content.Context
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlin.coroutines.CoroutineContext

abstract class BaseFragment : Fragment(), CoroutineScope {
    override lateinit var coroutineContext: CoroutineContext

    override fun onAttach(context: Context) {
        coroutineContext = SupervisorJob() + Dispatchers.Main
        super.onAttach(context)
    }

    override fun onDestroy() {
        coroutineContext.cancel()
        super.onDestroy()
    }
}