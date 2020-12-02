package spring.di;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringDi {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan("spring.di");
        context.refresh();

        StartUI startUI = context.getBean(StartUI.class);
        startUI.add("Ivan1");
        startUI.addFromInput();
        startUI.print();
    }
}
