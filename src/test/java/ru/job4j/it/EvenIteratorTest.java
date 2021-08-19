package ru.job4j.it;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

import java.util.NoSuchElementException;

public class EvenIteratorTest {

    @Test
    public void whenRead4NumbersWhenReturn2number() {
        EvenIterator it = new EvenIterator(
                new int[] {1, 2, 3, 4}
        );
        assertThat(it.next(), is(2));
        assertThat(it.next(), is(4));
    }

    @Test
    public void whenRead5NumbersWhenReturn2number() {
        EvenIterator it = new EvenIterator(
                new int[] {4, 2, 3, 1, 1}
        );
        assertThat(it.next(), is(4));
        assertThat(it.next(), is(2));
    }

    @Test
    public void whenNoEvenElements() {
        EvenIterator it = new EvenIterator(
                new int[] {1, 3, 3, 5, 9}
        );
        assertThat(it.hasNext(), is(false));
    }

    @Test
    public void whenNoElements() {
        EvenIterator it = new EvenIterator(
                new int[] {}
        );
        assertThat(it.hasNext(), is(false));
    }

    @Test (expected = NoSuchElementException.class)
    public void whenNoNextElements() {
        EvenIterator it = new EvenIterator(
                new int[] {}
        );
        it.next();
    }
}