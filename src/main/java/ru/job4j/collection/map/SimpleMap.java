package ru.job4j.collection.map;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class SimpleMap<K, V> implements Map<K, V> {

    private static final float LOAD_FACTOR = 0.75f;

    private int capacity = 8;

    private int count = 0;

    private int modCount = 0;

    private MapEntry<K, V>[] table = new MapEntry[capacity];

    @Override
    public boolean put(K key, V value) {
        if (count >= capacity * LOAD_FACTOR) {
            expand();
        }
        int indexKey = 0;
        if (key != null) {
            indexKey = indexFor(hash(key.hashCode()));
        }
        if (table[indexKey] != null) {
            return false;
        }
        table[indexKey] = new MapEntry<>(key, value);
        count++;
        modCount++;
        return true;
    }

    private int hash(int hashCode) {
        return Objects.hash(hashCode);
    }

    private int indexFor(int hash) {
        return hash % capacity;
    }

    private void expand() {
        capacity = capacity * 2;
        MapEntry<K, V>[] newTable = new MapEntry[capacity];
        MapEntry<K, V>[] oldTable = table;
        for (int j = 0; j < oldTable.length; j++) {
            MapEntry<K, V> e = oldTable[j];
            if (e != null) {
                int i = indexFor(hash(e.key.hashCode()));
                newTable[i] = e;
            }
        }
        table = newTable;
    }

    @Override
    public V get(K key) {
        int indexKey = 0;
        if (key != null) {
            indexKey = indexFor(hash(key.hashCode()));
        }
        if (count != 0) {
            if (table[indexKey] == null) {
                return null;
            }
            return table[indexKey].value;
        }
        return null;
    }

    @Override
    public boolean remove(K key) {
        if (count != 0) {
            int indexKey = 0;
            if (key != null) {
                indexKey = indexFor(hash(key.hashCode()));
            }
            MapEntry<K, V> temp = table[indexKey];
            if (temp != null) {
                table[indexKey] = null;
                modCount++;
                count--;
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<K> iterator() {
        return new Iterator<K>() {
            private int point = 0;
            private int index = 0;
            int expectedModCount = modCount;

            @Override
            public boolean hasNext() {
                return point < count;
            }

            @Override
            public K next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                if (expectedModCount < modCount) {
                    throw new ConcurrentModificationException();
                }
                for (; index < table.length; index++) {
                    if (table[index] != null) {
                        point++;
                        break;
                    }
                }
                return table[index++].key;
            }
        };

    }

    private static class MapEntry<K, V> {

        K key;
        V value;

        public MapEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }
    }

}