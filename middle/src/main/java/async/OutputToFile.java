package async;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class OutputToFile implements Output {
    private final String directory;

    public OutputToFile(String directory) {
        this.directory = directory;
    }

    @Override
    public void print(List<User> users) {
        for (int i = 0; i < users.size(); i += 1000) {
            try (PrintWriter out = new PrintWriter(new BufferedOutputStream(
                    new FileOutputStream(directory.concat(users.get(i).getCountry())
                            .concat(".txt"))))) {
                for (int j = i; j < i + 1000; j++) {
                    out.println(
                            users.get(j).getFirstName() + " " + users.get(j).getLastName() + " "
                                    + users.get(j).getCountry() + " " + users.get(j).getRating());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
