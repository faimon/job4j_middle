package async;

import static org.junit.Assert.*;

import org.junit.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class SqlStoreTest {
    public Connection init() {
        try (InputStream in = SqlStore
                .class.getClassLoader()
                .getResourceAsStream("app.properties")) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("driver-class-name"));
            return DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("username"),
                    config.getProperty("password")

            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Test
    public void whenSave() throws Exception {
        try (SqlStore store = new SqlStore(ConnectionRollback.create(init()))) {
            store.saveCountry("Russia");
            store.save(new User("Alex", "Ivanov", "Russia", 111));
            assertThat(store.findByName("Alex").getFirstName(), is("Alex"));
        }
    }

    @Test
    public void whenGetAll() throws Exception {
        try (SqlStore store = new SqlStore(ConnectionRollback.create(init()))) {
            store.saveCountry("Russia");
            store.save(new User("Ivan", "Ivanov", "Russia", 100));
            store.save(new User("Sasha", "Ivanov", "Russia", 40));
            assertThat(store.getAll().get(0).getFirstName(), is("Ivan"));
            assertThat(store.getAll().get(1).getFirstName(), is("Sasha"));
        }
    }
}