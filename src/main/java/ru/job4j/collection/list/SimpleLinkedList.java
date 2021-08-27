package ru.job4j.collection.list;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class SimpleLinkedList<E> implements List<E> {
    private class Node {
        private Node previous;
        private E value;
        private Node next;

        public Node(Node previous, E value, Node next) {
            this.previous = previous;
            this.value = value;
            this.next = next;
        }
    }
    private Node first;
    private Node last;
    private int size;
    private int modCount;

    @Override
    public void add(E value) {
        if (size == 0) {
            first = new Node(null, value, null);
            last = first;
        } else {
            Node secondLast = last;
            last = new Node(secondLast, value, null);
            secondLast.next = last;
        }
        size++;
        modCount++;
    }

    @Override
    public E get(int index) {
        Objects.checkIndex(index, size);
        Node node = first;
        for (int i = 0; i < index; i++) {
                node = node.next;
        }
        return node.value;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<>() {
            private int point = 0;
            final int expectedModCount = modCount;
            SimpleLinkedList<E>.Node node = first;
            SimpleLinkedList<E>.Node nodeIter;

            @Override
            public boolean hasNext() {
                return point < size;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                if (expectedModCount < modCount) {
                    throw new ConcurrentModificationException();
                }
                nodeIter = node;
                node = node.next;
                point++;
                return nodeIter.value;
            }
        };
    }
}