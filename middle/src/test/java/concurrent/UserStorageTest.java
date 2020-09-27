package concurrent;

import org.junit.Test;

import java.rmi.UnexpectedException;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.*;

public class UserStorageTest {

    @Test
    public void whenAdd() throws InterruptedException {
        UserStorage storage = new UserStorage();
        User user = new User(1, 100);
        storage.add(user);
        assertThat(storage.getStorage().get(0), is (user));
    }

    @Test
    public void whenTransfer() throws InterruptedException {
        UserStorage storage = new UserStorage();
        storage.add(new User(1, 100));
        storage.add(new User(2, 200));
        storage.transfer(1, 2, 50);
        assertThat(storage.getStorage().get(1).getAmount(), is (250));
    }

    @Test
    public void whenDelete() throws InterruptedException {
        UserStorage storage = new UserStorage();
        User user = new User(1, 100);
        storage.add(user);
        storage.delete(user);
        assertThat(storage.getStorage().size(), is(0));
    }

    @Test
    public void whenUpdate() throws InterruptedException {
        UserStorage storage = new UserStorage();
        User user = new User(1, 100);
        storage.add(user);
        user.setAmount(1111);
        storage.update(user);
        assertThat(storage.getStorage().get(0).getAmount(), is(1111));
    }

}