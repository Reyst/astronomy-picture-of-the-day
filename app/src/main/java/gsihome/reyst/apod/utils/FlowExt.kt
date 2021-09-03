package gsihome.reyst.apod.utils

import androidx.lifecycle.LifecycleCoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect

fun <T> Flow<T>.launchWhenCreated(
    scope: LifecycleCoroutineScope,
    block: suspend (T) -> Unit
): Job = scope.launchWhenCreated {
    this@launchWhenCreated.collect(block)
}

fun <T> Flow<T>.launchWhenStarted(
    scope: LifecycleCoroutineScope,
    block: suspend (T) -> Unit
): Job = scope.launchWhenStarted {
    this@launchWhenStarted.collect(block)
}

