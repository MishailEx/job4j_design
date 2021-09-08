package ru.job4j.collection.set;

import ru.job4j.collection.SimpleArrayList;

import java.util.Iterator;

public class SimpleSet<T> implements Set<T> {

    private SimpleArrayList<T> set = new SimpleArrayList<>(50);

    @Override
    public boolean add(T value) {
        Iterator<T> iterator = iterator();
        if (set.size() == 0) {
            set.add(value);
            return true;
        }
        while (iterator.hasNext()) {
            T a = iterator.next();
            if (a == value || a.equals(value)) {
                return false;
            }
        }
        set.add(value);
        return true;
    }

    @Override
    public boolean contains(T value) {
        Iterator<T> iterator = iterator();
        while (iterator.hasNext()) {
            T a = iterator.next();
            if (a == value || a.equals(value)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return set.iterator();
    }


}