package ru.job4j.collection;

import ru.job4j.list.List;

import java.util.*;

public class SimpleArrayList<T> implements List<T> {

    private T[] container;
    private int size;
    private int modCount;

    public SimpleArrayList(int capacity) {
        this.container = (T[]) new Object[capacity];
    }

    @Override
    public void add(T value) {
        if (size == container.length - 1) {
            container =  Arrays.copyOf(container, container.length * 2);
        }
        container[size] = value;
        size++;
        modCount++;
    }

    @Override
    public T set(int index, T newValue) {
        Objects.checkIndex(index, size);
        T tmp = container[index];
        container[index] = newValue;
        modCount++;
        return tmp;
    }

    @Override
    public T remove(int index) {
        Objects.checkIndex(index, size);
        T tmp = container[index];
        System.arraycopy(container, index + 1, container, index,
                container.length - 1 - index);
        container[container.length - 1] = null;
        modCount++;
        size--;
        return tmp;
    }

    @Override
    public T get(int index) {
        Objects.checkIndex(index, size);
        return container[index];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private int point = 0;
            final int expectedModCount = modCount;
            @Override
            public boolean hasNext() {
                return  point < size;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                if (expectedModCount < modCount) {
                    throw new ConcurrentModificationException();
                }
                return container[point++];
            }
        };
    }
}