package ru.job4j.it;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

public class EvenIterator implements Iterator<Integer> {
    private final int[] data;
    private int tmp = 0;

    public EvenIterator(int[] data) {
        this.data = data;
    }


    @Override
    public boolean hasNext() {
        for (int i = tmp; i < data.length; i++) {
            if (data[i] % 2 == 0) {
                tmp = i;
                return true;
            }
        }
        return false;
    }

    @Override
    public Integer next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return data[tmp++];
    }
}
