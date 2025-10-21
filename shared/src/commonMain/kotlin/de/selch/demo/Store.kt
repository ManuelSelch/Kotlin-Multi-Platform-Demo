package de.selch.demo
open class Store<T>(var state: T) {
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