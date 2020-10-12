package async;

import java.util.List;

public class OutputToSql implements Output {
    private final SqlStore store;

    public OutputToSql(SqlStore store) {
        this.store = store;
    }

    @Override
    public void print(List<User> users) {
        for (int i = 0; i < users.size(); i += 1000) {
            store.saveCountry(users.get(i).getCountry());
            for (int j = i; j < i + 1000; j++) {
                store.save(users.get(j));
            }
        }
    }
}

