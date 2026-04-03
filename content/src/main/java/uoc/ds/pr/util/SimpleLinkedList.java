package uoc.ds.pr.util;

import edu.uoc.ds.traversal.Iterator;

public class SimpleLinkedList<E> {

    private static class Node<E> {
        private E value;
        private Node<E> next;

        private Node(E value) {
            this.value = value;
        }
    }

    private Node<E> head;
    private Node<E> tail;
    private int size;

    public void add(E value) {
        Node<E> newNode = new Node<>(value);

        if (head == null) {
            head = newNode;
            tail = newNode;
        }
        else {
            tail.next = newNode;
            tail = newNode;
        }

        size++;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public Iterator<E> values() {
        return iterator();
    }

    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private Node<E> current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public E next() {
                E value = current.value;
                current = current.next;
                return value;
            }
        };
    }
}