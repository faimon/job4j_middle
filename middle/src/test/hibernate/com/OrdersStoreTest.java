package hibernate.com;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class OrdersStoreTest {
    private BasicDataSource pool = new BasicDataSource();

    @Before
    public void setUp() throws SQLException {
        pool.setDriverClassName("org.hsqldb.jdbcDriver");
        pool.setUrl("jdbc:hsqldb:mem:tests;sql.syntax_pgs=true");
        pool.setUsername("sa");
        pool.setPassword("");
        pool.setMaxTotal(2);
        StringBuilder builder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream("./db/update_001.sql")))
        ) {
            br.lines().forEach(line -> builder.append(line).append(System.lineSeparator()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        pool.getConnection().prepareStatement(builder.toString()).executeUpdate();
    }

    @After
    public void close() throws SQLException {
        pool.getConnection().prepareStatement("DROP TABLE orders").executeUpdate();
        pool.close();
    }

    @Test
    public void whenSaveOrderAndFindAllOneRowWithDescription() {
        OrdersStore store = new OrdersStore(pool);
        store.save(Order.of("name1", "description1"));
        List<Order> all = (List<Order>) store.findAll();
        assertThat(all.size(), is(1));
        assertThat(all.get(0).getDescription(), is("description1"));
        assertThat(all.get(0).getId(), is(1));
    }

    @Test
    public void whenFindByName() {
        OrdersStore store = new OrdersStore(pool);
        store.save(Order.of("task1", "desc1"));
        store.save(Order.of("task2", "desc2"));
        assertThat(store.findByName("task2").getDescription(), is("desc2"));
    }

    @Test
    public void whenUpdateOrder() {
        OrdersStore store = new OrdersStore(pool);
        store.save(Order.of("task1", "desc1"));
        store.save(Order.of("task2", "desc2"));
        Order order = new Order(1, "task10", "desc10", new Timestamp(System.currentTimeMillis()));
        store.update(order, 1);
        assertThat(store.findById(1).getDescription(), is("desc10"));
    }
}