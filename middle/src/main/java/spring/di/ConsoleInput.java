package spring.di;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
@Scope("prototype")
public class ConsoleInput {
    private Scanner scanner = new Scanner(System.in);

    public String ask() {
        System.out.println("Введите вопрос");
        return scanner.nextLine();
    }
}
