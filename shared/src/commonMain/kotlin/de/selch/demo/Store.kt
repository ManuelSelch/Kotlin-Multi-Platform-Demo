package de.selch.demo

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

open class Store<T>(var state: T) {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    private var listener: ((T) -> Unit)? = null

    fun setListener(listener: (T) -> Unit) {
        this.listener = listener
        listener(state) // emit current state
    }

    fun unsubscribe() {
        listener = null
    }

    fun notify() {
        listener?.invoke(state)
    }
}