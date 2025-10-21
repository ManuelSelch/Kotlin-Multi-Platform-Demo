import SwiftUI

@main
struct iOSApp: App {
    let store: ObservableCounterStore
    
    init() {
        store = ObservableCounterStore(store: Counter())
    }
    
    var body: some Scene {
        WindowGroup {
            CounterView()
                .environmentObject(store)
        }
    }
}
