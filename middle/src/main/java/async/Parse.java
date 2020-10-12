package async;

import java.nio.file.Path;
import java.util.List;

public interface Parse<T> {
    List<T> parse(Path file);
}
