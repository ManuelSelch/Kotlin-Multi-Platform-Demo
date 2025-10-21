import SwiftUI

struct CounterView: ConnectedView {
    
    struct Props {
        let value: Int32
        
        let onIncrement: () -> Void
        let onDecrement: () -> Void
    }
    
    func map(state: CounterState, dispatch: @escaping DispatchFunction) -> Props {
        return Props(
            value: state.value,
            onIncrement: { dispatch(.Increment) },
            onDecrement: { dispatch(.Decrement) }
        )
    }
    
    func body(props: Props) -> some View {
        VStack {
            Text("Counter View")
            Text("\(props.value)")
            
            Button("Increment") { props.onIncrement() }
            Button("Decrement") { props.onDecrement() }
        }
    }
}
