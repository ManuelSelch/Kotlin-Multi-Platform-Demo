import SwiftUI

struct CounterView: View {
    let count: Int32
    let incrementTapped: () -> ()
    let decrementTapped: () -> ()
    
    var body: some View {
        VStack {
            Text("Counter: \(count)")
            Button("Increment") { incrementTapped() }
            Button("Decrement") { decrementTapped() }
        }
    }
}
