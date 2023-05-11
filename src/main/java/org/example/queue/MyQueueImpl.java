package org.example.queue;

import java.util.NoSuchElementException;

public class MyQueueImpl<T> implements MyQueue<T> {

    private T[] buffer;
    private static final int BUFFER_SIZE = 100;

    private int front;
    private int rear;
    private int size;


    public MyQueueImpl() {
        buffer = (T[]) new Object[BUFFER_SIZE];
        front = 0;
        rear = -1;
        size = 0;
    }

    @Override
    public void put(T item) {
        if (isFull()) {
            throw new IllegalStateException("Queue is full");
        }
        rear = (rear + 1) % BUFFER_SIZE;
        buffer[rear] = item;
        size++;
    }

    @Override
    public T get() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        T item = buffer[front];
        front = (front + 1) % BUFFER_SIZE;
        size--;
        return item;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean isFull() {
        return size == BUFFER_SIZE;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void clear() {
        front = 0;
        rear = -1;
        size = 0;
    }
}
