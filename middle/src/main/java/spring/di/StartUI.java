package spring.di;

import org.springframework.stereotype.Component;

@Component
public class StartUI {
    private Store store;
    private ConsoleInput consoleInput;

    public StartUI(Store store, ConsoleInput input) {
        this.store = store;
        this.consoleInput = input;
    }

    public void add(String value) {
        store.add(value);
    }

    public void addFromInput() {
        store.add(consoleInput.ask());
    }

    public void print() {
        for (String value : store.getAll()) {
            System.out.println(value);
        }
    }
}
