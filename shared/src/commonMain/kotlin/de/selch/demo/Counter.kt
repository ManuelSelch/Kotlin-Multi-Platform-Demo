package de.selch.demo

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class CounterState(
    var value: Int = 0
)

enum class CounterAction {
    Increment,
    Decrement
}

class CounterStore : Store<CounterState, CounterAction>(CounterState()) {

    override fun reduce(state: CounterState, action: CounterAction): CounterState {
        return when(action) {
            CounterAction.Increment -> {
                fetchSomething()
                state.copy(value = state.value+1)
            }
            CounterAction.Decrement -> state.copy(value = state.value-1)
        }
    }

    private fun fetchSomething() {
        scope.launch {
            delay(3000)
            dispatch(CounterAction.Decrement)
        }
    }

}