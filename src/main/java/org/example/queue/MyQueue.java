package org.example.queue;

import java.util.LinkedList;

public interface MyQueue<T> {

        //добавление нового элемента к очереди, предыдущий последний становится предпоследним
        void put(T item);

        //извлечение очередного элемента из очереди, предыдущий следующий элемент
        //становится первым
        T get();

        //проверка если очередь пуста, т.е. нельзя применять операцию get
        boolean isEmpty();

        // проверка если очередь полностью заполнена, т.е. нельзя применять операцию put
        boolean isFull();

        int getSize();

        void clear();


    }




