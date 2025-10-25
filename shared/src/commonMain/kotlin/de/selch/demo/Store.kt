package de.selch.demo

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

abstract class Store<S, A>(
    private var state: S
) {
    val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    private val observers = mutableListOf<(S) -> Unit>()

    fun subscribe(observer: (S) -> Unit) {
        observers += observer
        observer(state) // emit current state
    }

    fun unsubscribe(observer: (S) -> Unit) {
        observers -= observer
    }

    private fun notifyObservers() {
        observers.forEach { it(state) }
    }

    fun dispatch(action: A) {
        state = reduce(state, action)
        notifyObservers()
    }

    abstract fun reduce(state: S, action: A): S
}