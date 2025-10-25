import Foundation
import SwiftUI
import Shared

protocol ContainerView: View {
    associatedtype State
    associatedtype Action
    associatedtype V: View
    associatedtype Store: ObservableStore<State, Action>
    
    typealias Dispatch = (Action) -> ()
        
    func body(state: State, dispatch: @escaping Dispatch) -> V
}

extension ContainerView {
    func render(state: State, dispatch: @escaping Dispatch) -> V {
        return body(state: state, dispatch: dispatch)
    }
    
    var body: ContainerConnector<V, State, Action, Store> {
        return ContainerConnector(content: render)
    }
}

struct ContainerConnector<
    V: View,
    State, Action,
    Store: ObservableStore<State, Action>
>: View {
    typealias Dispatch = (Action) -> ()
    
    @EnvironmentObject var store: Store
    let content: (State, @escaping Dispatch) -> V
    
    public var body: V {
        return content(store.state, store.dispatch)
    }
}

protocol ObservableStore<S, A>: ObservableObject {
    associatedtype S
    associatedtype A
    
    var state: S {get}
    
    func dispatch(_ action: A)
}
