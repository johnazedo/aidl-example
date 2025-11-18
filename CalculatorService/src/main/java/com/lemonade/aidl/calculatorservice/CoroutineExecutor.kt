package com.lemonade.aidl.calculatorservice

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CoroutineExecutor(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val mainDispatcher: CoroutineDispatcher = Dispatchers.Main
) {

    private val scope = CoroutineScope(SupervisorJob() + dispatcher)

    fun <T> execute(run: suspend () -> T, onSuccess: (T) -> Unit, onError: (Throwable) -> Unit) {
        scope.launch {
            val result = runCatching { run() }
            withContext(mainDispatcher) {
                result.onSuccess { onSuccess(it) }
                result.onFailure { onError(it) }
            }
        }
    }

    fun shutdown() {
        scope.cancel()
    }
}