package async;

import java.io.*;
import java.net.Socket;

public class Client {
    public void getData() {
        try (Socket socket = new Socket("localhost", 9000)) {
            System.out.println("Клиент начал принимать данные");
            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                String data;
                while ((data = in.readLine()) != null) {
                    System.out.println(data);
                }
                System.out.println("Клиент закончил прием данных");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
