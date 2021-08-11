package com.sermarmu.randomuser.common

import com.sermarmu.core.base.BaseFragment
import com.sermarmu.randomuser.MessageLauncher
import org.koin.android.ext.android.inject

abstract class AppBaseFragment : BaseFragment() {
    val messageLauncher: MessageLauncher by inject()
}