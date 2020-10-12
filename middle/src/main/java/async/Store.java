package async;

import java.util.List;

public interface Store extends AutoCloseable {
    boolean save(User user);

    List<User> getAll();

    User findByName(String name);
}
