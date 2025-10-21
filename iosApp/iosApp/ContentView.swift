import SwiftUI
import Shared

typealias Counter = Shared.de.selch.demo.Counter
typealias CounterState = Shared.de.selch.demo.CounterState

struct ContentView: View {
    @State var value: Int32 = 0
    
    let counter: Counter

    init() {
        self.counter = Counter()
    }
    
    var body: some View {
        VStack {
            Text("current value: \(value)")
            Button("Increment") { counter.increment() }
            Button("Decrement") { counter.decrement() }
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .top)
        .padding()
        .onAppear {
            counter.subscribe(listener: { state in self.value = state.value })
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
