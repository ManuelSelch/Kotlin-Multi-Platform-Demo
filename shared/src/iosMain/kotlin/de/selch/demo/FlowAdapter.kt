package de.selch.demo

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FlowAdapter<T>(
    private val flow: Flow<T>
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    private var job: Job? = null

    fun collect(onEach: (T) -> Unit) {
        job?.cancel()
        job = scope.launch {
            flow.collectLatest { onEach(it) }
        }
    }

    fun cancel() {
        job?.cancel()
        job = null
    }
}