package ru.job4j.collection.map;

import org.junit.Test;
import ru.job4j.iterator.ListUtils;

import java.util.*;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

public class SimpleMapTest {

    @Test
    public void whenAddNullAndNotNull() {
        SimpleMap<Integer, Integer> a = new SimpleMap<>();
        assertTrue(a.put(null, 25));
        assertTrue(a.put(4, 5));
    }

    @Test
    public void whenAddRepeatKey() {
        SimpleMap<Integer, Integer> a = new SimpleMap<>();
        assertTrue(a.put(null, 25));
        assertTrue(a.put(4, 5));
        assertFalse(a.put(null, 23));
        assertFalse(a.put(4, 7));
    }

    @Test
    public void whenGetExistingKey() {
        SimpleMap<Integer, Integer> a = new SimpleMap<>();
        assertTrue(a.put(6, 25));
        assertTrue(a.put(null, 30));
        assertTrue(a.put(4, 5));
        int rsl = a.get(6);
        int rsl2 = a.get(null);
        assertThat(rsl, is(25));
        assertThat(rsl2, is(30));

    }

    @Test
    public void whenGetNonExistentKey() {
        SimpleMap<Integer, Integer> a = new SimpleMap<>();
        assertTrue(a.put(6, 25));
        assertTrue(a.put(4, 5));
        assertNull(a.get(8));
    }

    @Test
    public void whenRemove() {
        SimpleMap<Integer, Integer> a = new SimpleMap<>();
        assertTrue(a.put(6, 25));
        assertTrue(a.remove(6));
    }

    @Test
    public void whenRemoveThanTableClear() {
        SimpleMap<Integer, Integer> a = new SimpleMap<>();
        assertFalse(a.remove(6));
    }

    @Test
    public void whenRemoveNonExistentKey() {
        SimpleMap<Integer, Integer> a = new SimpleMap<>();
        assertTrue(a.put(6, 25));
        assertFalse(a.remove(8));
    }

    @Test(expected = ConcurrentModificationException.class)
    public void whenAddNumberAfterCreateIterator() {
        SimpleMap<Integer, Integer> a = new SimpleMap<>();
        Iterator<Integer> iterator = a.iterator();
        a.put(6, 7);
        iterator.next();
    }

    @Test(expected = NoSuchElementException.class)
    public void whenTableClear() {
        SimpleMap<Integer, Integer> a = new SimpleMap<>();
        Iterator<Integer> iterator = a.iterator();
        iterator.next();
    }
}