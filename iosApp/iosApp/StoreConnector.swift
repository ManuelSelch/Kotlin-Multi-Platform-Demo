import SwiftUI
import Shared

typealias Counter = Shared.de.selch.demo.Counter
typealias CounterState = Shared.de.selch.demo.CounterState
typealias CounterAction = Shared.de.selch.demo.CounterAction


class ObservableCounterStore: ObservableObject {
    @Published var state = CounterState(value: 0)
    
    let store: Counter
    
    init(store: Counter) {
        self.store = store
        store.subscribe(listener: { [weak self] state in self?.state = state })
    }
    
    func dispatch(_ action: CounterAction) {
        store.dispatch(action: action)
    }
}

typealias DispatchFunction = (CounterAction) -> ()

protocol ConnectedView: View {
    associatedtype Props
    associatedtype V: View
        
    func map(state: CounterState, dispatch: @escaping DispatchFunction) -> Props
    func body(props: Props) -> V
}

extension ConnectedView {
    func render(state: CounterState, dispatch: @escaping DispatchFunction) -> V {
        let props = map(state: state, dispatch: dispatch)
        return body(props: props)
    }
    
    var body: StoreConnector<V> {
        return StoreConnector(content: render)
    }
}

public struct StoreConnector<V: View>: View {
    @EnvironmentObject var store: ObservableCounterStore
    let content: (CounterState, @escaping DispatchFunction) -> V
    
    public var body: V {
        return content(store.state, store.dispatch)
    }
}
