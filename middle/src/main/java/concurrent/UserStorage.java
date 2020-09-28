package concurrent;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.List;

@ThreadSafe
public class UserStorage {
    @GuardedBy("this")
    private final List<User> storage;

    public UserStorage() {
        this.storage = new ArrayList<>();
    }

    public synchronized List<User> getStorage() {
        return storage;
    }

    public synchronized boolean add(User user) {
        return storage.add(user);
    }

    public synchronized boolean update(User user) {
        int num = storage.indexOf(user);
        if (num != -1) {
            storage.set(num, user);
            return true;
        }
        return false;
    }

    public synchronized boolean delete(User user) {
        return storage.remove(user);
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        User fromUser = null;
        User toUser = null;
        for (User user : storage) {
            if (user.getId() == fromId) {
                fromUser = user;
            }
            if (user.getId() == toId) {
                toUser = user;
            }
        }
        if (fromUser != null && toUser != null) {
            if (fromUser.getAmount() >= amount) {
                fromUser.setAmount(fromUser.getAmount() - amount);
                toUser.setAmount(toUser.getAmount() + amount);
                return true;
            }
        }
        return false;
    }
}
