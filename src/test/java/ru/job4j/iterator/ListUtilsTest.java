package ru.job4j.iterator;

import org.hamcrest.core.Is;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import static org.junit.Assert.assertThat;

public class ListUtilsTest {

    @Test
    public void whenAddBefore() {
        List<Integer> input = new ArrayList<>(Arrays.asList(1, 3));
        ListUtils.addBefore(input, 1, 2);
        assertThat(Arrays.asList(1, 2, 3), Is.is(input));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void whenAddBeforeWithInvalidIndex() {
        List<Integer> input = new ArrayList<>(Arrays.asList(1, 3));
        ListUtils.addBefore(input, 3, 2);
    }

    @Test
    public void whenAddAfterLast() {
        List<Integer> input = new ArrayList<>(Arrays.asList(0, 1, 2));
        ListUtils.addAfter(input, 2, 3);
        assertThat(Arrays.asList(0, 1, 2, 3), Is.is(input));
    }

    @Test
    public void whenRemoveIfNumberMore3() {
        List<Integer> input = new ArrayList<>(Arrays.asList(0, 1, 2, 5, 8));
        Predicate<Integer> filter = integer -> integer > 3;
        ListUtils.removeIf(input, filter);
        assertThat(Arrays.asList(0, 1, 2), Is.is(input));
    }

    @Test
    public void whenReplaceIfNumberMore5() {
        List<Integer> input = new ArrayList<>(Arrays.asList(9, 2, 5, 7, 2));
        Predicate<Integer> filter = integer -> integer > 5;
        ListUtils.replaceIf(input, filter, 0);
        assertThat(Arrays.asList(0, 2, 5, 0, 2), Is.is(input));
    }

    @Test
    public void whenRemoveAll() {
        List<Integer> input = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        List<Integer> input2 = new ArrayList<>(Arrays.asList(1, 3, 5));
        ListUtils.removeAll(input, input2);
        assertThat(Arrays.asList(2, 4), Is.is(input));
    }
}