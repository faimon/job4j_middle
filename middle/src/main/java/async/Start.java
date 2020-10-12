package async;

import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Start {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ParseTxtFile file = new ParseTxtFile();
        List<User> userList = file.parse(Paths.get("/Users/andreykatanov/Downloads/source.txt"));
        CompletableFuture saveToDisk = CompletableFuture.runAsync(
                () -> {
                    System.out.println("Запись на диск началась...");
                    new OutputToFile("./middle/src/main/java/async/parsedFiles/").print(userList);
                    System.out.println("Запись на диск закончилась...");
                }
        );
        CompletableFuture saveToSqlDB = CompletableFuture.runAsync(
                () -> {
                    System.out.println("Запись в бд началась...");
                    SqlStore store = new SqlStore(SqlStore.init());
                    new OutputToSql(store).print(userList);
                    System.out.println("Запись в бд закончилась...");
                }
        );
        CompletableFuture serverAndClient = CompletableFuture.runAsync(
                () -> {
                    Server server = new Server();
                    Client client = new Client();
                    System.out.println("Сервер начал передавать данные");
                    server.print(userList);
                    System.out.println("Клиент начал принимать данные");
                    client.getData();
                    System.out.println("Клиент принял все данные");
                }
        );
        saveToDisk.get();
        serverAndClient.get();
        saveToSqlDB.get();
    }
}
