package spring.di;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringDi {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(ConsoleInput.class);
        context.register(Store.class);
        context.register(StartUI.class);
        context.refresh();

        StartUI startUI = context.getBean(StartUI.class);
        startUI.add("Ivan");
        startUI.addFromInput();
        startUI.print();
    }
}
