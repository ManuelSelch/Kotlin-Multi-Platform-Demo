package de.selch.demo
data class CounterState(
    var value: Int = 0
)

@Suppress("unused")
class Counter: Store<CounterState>(CounterState()) {

    fun increment() {
        state.value += 1
        notify()
    }

    fun decrement() {
        state.value -= 1
        notify()
    }

    fun subscribe(listener: (CounterState) -> Unit) {
        setListener(listener)
    }
}