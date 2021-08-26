package ru.job4j.generics;

import java.util.HashMap;
import java.util.Map;

public final class MemStore<T extends Base> implements Store<T> {
    private final Map<String, T> mem = new HashMap<>();

    @Override
    public void add(T model) {
        mem.put(model.getId(), model);
    }

    @Override
    public boolean replace(String id, T model) {
        if (mem.get(id) != null) {
            mem.put(id, model);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        for (String num : mem.keySet()) {
            if (num.contains(id)) {
                mem.remove(num);
                return true;
            }
        }
        return false;
    }

    @Override
    public T findById(String id) {
        for (String num : mem.keySet()) {
            if (num.contains(id)) {
                return mem.get(id);
            }
        }
        return null;
    }
}
