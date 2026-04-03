package uoc.ds.pr.util;

import edu.uoc.ds.adt.sequential.Stack;
import edu.uoc.ds.traversal.Iterator;

public class StackLinkedList<E> implements Stack<E> {

    private Node<E> top;
    private int size;

    private static class Node<E> {
        private final E elem;
        private final Node<E> next;

        private Node(E elem, Node<E> next) {
            this.elem = elem;
            this.next = next;
        }
    }

    private static class StackIterator<E> implements Iterator<E> {
        private Node<E> current;

        private StackIterator(Node<E> start) {
            this.current = start;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public E next() {
            E elem = current.elem;
            current = current.next;
            return elem;
        }
    }

    public StackLinkedList() {
        this.top = null;
        this.size = 0;
    }

    @Override
    public void push(E elem) {
        top = new Node<>(elem, top);
        size++;
    }

    @Override
    public E pop() {
        if (top == null) {
            return null;
        }
        E elem = top.elem;
        top = top.next;
        size--;
        return elem;
    }

    @Override
    public E peek() {
        return (top == null ? null : top.elem);
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<E> values() {
        return new StackIterator<>(top);
    }
}