package com.sermarmu.core.extension

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Calls the specified suspending block within [Dispatchers.IO], suspends until it
 * completes, and returns the result.
 */
suspend inline fun <T> launchInIO(
    noinline block: suspend CoroutineScope.() -> T
): T = withContext(Dispatchers.IO, block)

/**
 * Calls the specified suspending block within [Dispatchers.Main], suspends until it
 * completes, and returns the result.
 */
suspend inline fun <T> launchInMain(
    noinline block: suspend CoroutineScope.() -> T
): T = withContext(Dispatchers.Main, block)