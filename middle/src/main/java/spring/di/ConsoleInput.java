package spring.di;

import java.util.Scanner;

public class ConsoleInput {
    private Scanner scanner = new Scanner(System.in);

    public String ask() {
        System.out.println("Введите вопрос");
        return scanner.nextLine();
    }
}
