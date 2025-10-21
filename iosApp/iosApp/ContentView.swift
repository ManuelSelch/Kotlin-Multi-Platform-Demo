import SwiftUI
import Shared

class ObservableCounterState: ObservableObject {
    @Published var value: Counter.Model
    
    init(value: Counter.Model) {
        self.value = value
    }
}

extension Counter.Model {
    func wrap() -> ObservableCounterState {
        return ObservableCounterState(value: self)
    }
}

struct ContentView: View {
    @State private var showContent = false
    @ObservedObject private var state: ObservableCounterState
    
    private var loop = Counter().build()
    
    init() {
        var counter = Counter()
        
        loop.dispatchEvent(event: Counter.EventIncrement())
        
        state = ObservableCounterState(value: Counter.Model(count: 0))
    }
    
    var body: some View {
        VStack {
            Button("Click me!") {
                withAnimation {
                    showContent = !showContent
                }
            }

            if showContent {
                VStack(spacing: 16) {
                    Image(systemName: "swift")
                    
                        .font(.system(size: 200))
                        .foregroundColor(.accentColor)
                    Text("SwiftUI: \(Greeting().greet())")
                    
                    Spacer()
                    
                    Text("Hello World")
                }
                .transition(.move(edge: .top).combined(with: .opacity))
            }
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .top)
        .padding()
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
