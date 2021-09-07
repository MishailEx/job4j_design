package ru.job4j.collection;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ForwardLinked<T> implements Iterable<T> {
    private Node<T> head;

    public void add(T value) {
        Node<T> node = new Node<T>(value, null);
        if (head == null) {
            head = node;
            return;
        }
        Node<T> tail = head;
        while (tail.next != null) {
            tail = tail.next;
        }
        tail.next = node;
    }

    public void addFirst(T value) {
        Node<T> node = new Node<T>(value, null);
        if (head == null) {
            head = node;
            return;
        }
        Node<T> tail = node;
        tail.next = head;
        head = tail;
    }

    public T deleteFirst() {
        Node<T> node = head;
        if (head == null) {
            throw new NoSuchElementException();
        }
        if (head.next != null) {
            head = head.next;
            head.next = null;
        } else {
            head = null;
        }
        return node.value;
    }

    public boolean revert() {
        Node<T> next = head;
        Node<T> previous = null;
        if (head != null && head.next != null) {
            while (next != null) {
                next = head.next;
                head.next = previous;
                previous = head;
                head = next;
            }
            head = previous;
            return true;
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            Node<T> node = head;

            @Override
            public boolean hasNext() {
                return node != null;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                T value = node.value;
                node = node.next;
                return value;
            }
        };
    }

    private static class Node<T> {
        T value;
        Node<T> next;

        public Node(T value, Node<T> next) {
            this.value = value;
            this.next = next;
        }
    }
}