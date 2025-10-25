import SwiftUI

@main
struct iOSApp: App {
    let store: ObservableCounterStore
    
    init() {
        store = ObservableCounterStore(store: CounterStore())
    }
    
    var body: some Scene {
        WindowGroup {
            CounterContainer()
                .environmentObject(store)
        }
    }
}
