package async;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ParseTxtFile implements Parse {
    @Override
    public List<User> parse(Path file) {
        List<User> userList = new ArrayList<>();
        try (BufferedReader in = new BufferedReader(new FileReader(file.toString()))) {
            in.readLine(); // skip first line
            String[] info;
            String line;
            while ((line = in.readLine()) != null) {
                info = line.split("  ");
                userList.add(new User(info[0], info[1], info[2], Integer.parseInt(info[3])));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userList;
    }
}
