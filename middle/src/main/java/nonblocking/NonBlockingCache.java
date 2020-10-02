package nonblocking;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class NonBlockingCache {
    private Map<Integer, Base> map = new ConcurrentHashMap<>();

    public void add(Base model) {
        if (map.get(model.getId()) != null) {
            throw new OptimisticException("Error add, this model already exists, use update()");
        }
        map.put(model.getId(), model);
    }

    public void update(Base model) {
        map.computeIfPresent(model.getId(), (key, current) -> {
            if (current.getVersion() != model.getVersion()) {
                throw new OptimisticException("Error update, different versions of model");
            }
            model.setVersion(model.getVersion() + 1);
            return model;
        });
    }

    public void delete(Base model) {
        map.remove(model.getId());
    }

    public Base get(int id) {
        return map.get(id);
    }
}
