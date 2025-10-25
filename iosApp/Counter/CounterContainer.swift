import SwiftUI
import Shared

typealias CounterStore = Shared.de.selch.demo.CounterStore
typealias CounterState = Shared.de.selch.demo.CounterState
typealias CounterAction = Shared.de.selch.demo.CounterAction

struct CounterContainer: ContainerView {
    typealias State = CounterState
    typealias Action = CounterAction
    typealias Store = ObservableCounterStore
    
    func body(state: CounterState, dispatch: @escaping (CounterAction) -> ()) -> some View {
        VStack {
            Text("Hello World Container")
            
            CounterView(
                count: state.value,
                incrementTapped: { dispatch(.Increment) },
                decrementTapped: { dispatch(.Decrement) }
            )
        }
    }
}


class ObservableCounterStore: ObservableStore {
    @Published var state = CounterState(value: 0)
    
    let store: CounterStore
    
    init(store: CounterStore) {
        self.store = store
        store.subscribe(observer: { [weak self] state in self?.state = state as! CounterState })
    }
    
    func dispatch(_ action: CounterAction) {
        store.dispatch(action: action)
    }
}

