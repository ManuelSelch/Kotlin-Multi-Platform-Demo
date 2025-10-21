package de.selch.demo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.GlobalScope
import kt.mobius.Connectable
import kt.mobius.Connection
import kt.mobius.Mobius
import kt.mobius.MobiusLoop
import kt.mobius.Next.Companion.next
import kt.mobius.Update
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class Counter {
    data class Model(val count: Int)

    sealed class Event {
        object Decrement: Event()
        object Increment: Event()
    }

    sealed class Effect

    val update = Update<Model, Event, Effect> { model, event ->
        when(event) {
            Event.Decrement -> next(model.copy(count = model.count - 1))
            Event.Increment -> next(model.copy(count = model.count + 1))
        }
    }

    val effectHandler = Connectable<Effect, Event> { output ->
        object : Connection<Effect> {
            override fun accept(value: Effect) = Unit
            override fun dispose() = Unit
        }
    }

    val loopFactory = Mobius.loop(update, effectHandler)

    fun build(): MobiusLoop<Model, Event, Effect> {
        return loopFactory.startFrom(Model(0))
    }

    fun buildVo2() {

    }
}

class CounterViewModel {
    private val controller = Counter().build()

    // val models: Flow<Counter.Model>
}

class CommonFlow<T>(
    private val flow: Flow<T>
): Flow<T> by flow {

    private fun subscribe(
        scope: CoroutineScope,
        dispatcher: CoroutineDispatcher,
        onCollect: (T) -> Unit
    ): DisposableHandle {
        val job = scope.launch(dispatcher) {
            flow.collect(onCollect)
        }
        return DisposableHandle { job.cancel() }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun subscribe(
        onCollect: (T) -> Unit
    ): DisposableHandle {
        return subscribe(
            scope = GlobalScope,
            dispatcher = Dispatchers.Main,
            onCollect = onCollect,
        )
    }
}