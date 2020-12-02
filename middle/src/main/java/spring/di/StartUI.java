package spring.di;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class StartUI {
    @Autowired
    private Store store;

    @Autowired
    private ConsoleInput consoleInput;

    public void add(String value) {
        store.add(value);
    }

    public void addFromInput() {
        store.add(consoleInput.ask());
    }

    public void print() {
        System.out.println("Начал");
        for (String value : store.getAll()) {
            System.out.println(value);
        }
    }
}
