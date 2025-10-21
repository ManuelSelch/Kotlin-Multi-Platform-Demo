package de.selch.demo

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class CounterState(
    var value: Int = 0
)

@Suppress("unused")
class Counter: Store<CounterState>(CounterState()) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    fun increment() {
        state.value += 1
        notify()

        scope.launch {
            delay(3000)
            decrement()
        }
    }

    fun decrement() {
        state.value -= 1
        notify()
    }

    fun subscribe(listener: (CounterState) -> Unit) {
        setListener(listener)
    }
}