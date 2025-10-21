package de.selch.demo

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class CounterState(
    var value: Int = 0
)

enum class CounterAction {
    Increment,
    Decrement
}

@Suppress("unused")
class Counter: Store<CounterState>(CounterState()) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    fun dispatch(action: CounterAction) {
        when(action) {
            CounterAction.Increment -> {
                state.value += 1;
                fetchSomething()
            }
            CounterAction.Decrement ->
                state.value -= 1
        }

        notify()
    }

    private fun fetchSomething() {
        scope.launch {
            delay(3000)

            state.value -= 1
            notify()
        }
    }

    fun subscribe(listener: (CounterState) -> Unit) {
        setListener(listener)
    }
}