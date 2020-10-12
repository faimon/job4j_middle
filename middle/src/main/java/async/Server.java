package async;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Server implements Output {
    @Override
    public void print(List<User> users) {
        new Thread(() -> {
            try (ServerSocket server = new ServerSocket(9000)) {
                boolean isStart = true;
                while (isStart) {
                    Socket socket = server.accept();
                    try (PrintWriter writer = new PrintWriter(socket.getOutputStream())) {
                        System.out.println("Сервер начал передавать данные");
                        for (User user : users) {
                            writer.println(user.getFirstName() + " "
                                    + user.getLastName() + " " + user.getCountry()
                                    + " " + user.getRating());
                        }
                        System.out.println("Сервер закончил передачу данных");
                        isStart = false;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
