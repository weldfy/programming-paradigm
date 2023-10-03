package queue;
/*
Model: queue[1]..queue[size]
Inv:   size >= 0
       1 <= i <= size : queue[i] != null
*/
public class ArrayQueueADT {

    private Object[] elements = new Object[2];
    private int first = 0;
    private int size = 0;

    private static void ensureCapacity(ArrayQueueADT queue, int newSize) {
        if (newSize == queue.elements.length) {
            Object[] newElements = new Object[queue.elements.length * 2];
            if (queue.first + queue.size < queue.elements.length) {
                System.arraycopy(queue.elements, queue.first, newElements, 0, queue.size);
            } else {
                System.arraycopy(queue.elements, queue.first,
                        newElements, 0, queue.elements.length - queue.first);
                System.arraycopy(queue.elements, 0, newElements,
                        queue.elements.length - queue.first, queue.size - queue.elements.length + queue.first);
            }
            queue.elements = newElements;
            queue.first = 0;
        }
    }
    /*
    PRE:  newElement != null
    POST: size = size' + 1
          queue[size'] = newElement
          осталльные элементы очереди остались не изменены
    */
    public static void enqueue(ArrayQueueADT queue, Object newElement) {
        ensureCapacity(queue,queue.size + 1);
        queue.elements[(queue.first + queue.size) % queue.elements.length] = newElement;
        queue.size++;
    }
    /*
    PRE:  size > 0
    POST: R = element[1]
          элементы очереди остались не изменены
    */
    public static Object element(ArrayQueueADT queue) {
        return queue.elements[queue.first];
    }
    /*
    PRE:  size > 0
    POST: size = size' - 1
          R = queue'[1]
          2 <= i <= size : queue[i - 1] = queue[i]
    */
    public static Object dequeue(ArrayQueueADT queue) {
        assert queue.size > 0;
        queue.size--;
        Object R = queue.elements[queue.first];
        queue.elements[queue.first] = null;
        queue.first = (queue.first + 1) % queue.elements.length;
        return R;
    }
    /*
     PRE:  true
     POST: R = size
           элементы очереди не были изменены
    */
    public static int size(ArrayQueueADT queue) {
        return queue.size;
    }
    /*
    PRE:  true
    POST: R = (size == 0)
          элементы очереди не были изменены
    */
    public static boolean isEmpty(ArrayQueueADT queue) {
        return queue.size == 0;
    }
    /*
    PRE:  true
    POST: size = 0
    */
    public static void clear(ArrayQueueADT queue) {
        while (!isEmpty(queue)) {
            dequeue(queue);
        }
        queue.first = 0;
    }
    /*
    PRE:  true
    POST: R = queue
    */
    public static Object[] toArray(ArrayQueueADT queue) {
        Object[] a = new Object[queue.size];
        for (int i = 0; i < queue.size; i++) {
            a[i] = queue.elements[(queue.first + i) % queue.elements.length];
        }
        return a;
    }
    /*
    PRE:  newElement != null
    POST: size = size' + 1
            1 <= i <= size' : queue[i] = queue[i + 1]
    queue[1] = newElement
    */
    public static void push(ArrayQueueADT queue, Object newElement) {
        ensureCapacity(queue,queue.size + 1);
        queue.first = (queue.first - 1 + queue.elements.length) % queue.elements.length;
        queue.elements[queue.first] = newElement;
        queue.size++;
    }
    /*
    PRE:  size > 0
    POST: R = queue[size]
    */
    public static Object peek(ArrayQueueADT queue) {
        assert queue.size > 0;
        return queue.elements[(queue.first + queue.size - 1) % queue.elements.length];
    }
    /*
    PRE:  size > 0
    POST: R = queue'[size']
          size = size' - 1
          остальные элементы очереди не были изменены
    */
    public static Object remove(ArrayQueueADT queue) {
        assert queue.size > 0;
        Object R = queue.elements[(queue.first + queue.size - 1) % queue.elements.length];
        queue.size--;
        queue.elements[(queue.first + queue.size) % queue.elements.length] = null;
        return R;
    }
}