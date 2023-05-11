import org.example.queue.MyQueue;
import org.example.queue.MyQueueImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class MyQueueImplTest {


    private MyQueue<Integer> queue;

    @BeforeEach
    public void setUp() {
        queue = new MyQueueImpl<>();
    }

    @Test
    public void testPut() {
        for (int i = 1; i <= 5; i++) {
            queue.put(i);
        }
        assertEquals(5, queue.getSize());
    }

    @Test
    public void testIsFull() {
        assertFalse(queue.isFull());
        for (int i = 1; i <= 100; i++) {
            queue.put(i);
        }
        assertTrue(queue.isFull());
    }

    @Test
    public void testGet() {
        assertThrows(NoSuchElementException.class, queue::get);

        for (int i = 1; i <= 5; i++) {
            queue.put(i);
        }

        for (int i = 1; i <= 5; i++) {
            assertEquals(i, queue.get());
        }

        assertThrows(NoSuchElementException.class, queue::get);
    }

    @Test
    public void testIsEmpty() {
        assertTrue(queue.isEmpty());
        queue.put(1);
        assertFalse(queue.isEmpty());
        queue.get();
        assertTrue(queue.isEmpty());
    }

    @Test
    public void testGetSize() {
        assertEquals(0, queue.getSize());
        queue.put(1);
        assertEquals(1, queue.getSize());
        queue.get();
        assertEquals(0, queue.getSize());
    }

    @Test
    public void testClear() {
        queue.put(1);
        queue.put(2);
        queue.clear();
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.getSize());
    }



}
