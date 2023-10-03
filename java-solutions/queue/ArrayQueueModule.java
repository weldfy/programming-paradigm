package queue;
/*
Model: queue[1]..queue[size]
Inv:   size >= 0
       1 <= i <= size : queue[i] != null
*/
public class ArrayQueueModule {

    private static Object[] elements = new Object[2];
    private static int first = 0;
    private static int size = 0;

    private static void ensureCapacity(int newSize) {
        if (newSize == elements.length) {
            Object[] newElements = new Object[elements.length * 2];
            if (first + size < elements.length) {
                System.arraycopy(elements, first, newElements, 0, size);
            } else {
                System.arraycopy(elements, first, newElements, 0, elements.length - first);
                System.arraycopy(elements, 0, newElements,
                        elements.length - first, size - elements.length + first);
            }
            elements = newElements;
            first = 0;
        }
    }
    /*
    PRE:  newElement != null
    POST: size = size' + 1
          queue[size'] = newElement
          осталльные элементы очереди остались не изменены
    */
    public static void enqueue(Object newElement) {
        ensureCapacity(size + 1);
        elements[(first + size) % elements.length] = newElement;
        size++;
    }
    /*
    PRE:  size > 0
    POST: R = element[1]
          элементы очереди остались не изменены
    */
    public static Object element() {
        return elements[first];
    }
    /*
    PRE:  size > 0
    POST: size = size' - 1
          R = queue'[1]
          2 <= i <= size : queue[i - 1] = queue[i]
    */
    public static Object dequeue() {
        assert size > 0;
        size--;
        Object R = elements[first];
        elements[first] = null;
        first = (first + 1) % elements.length;
        return R;
    }
    /*
     PRE:  true
     POST: R = size
           элементы очереди не были изменены
    */
    public static int size() {
        return size;
    }
    /*
    PRE:  true
    POST: R = (size == 0)
          элементы очереди не были изменены
    */
    public static boolean isEmpty() {
        return size == 0;
    }
    /*
    PRE:  true
    POST: size = 0
    */
    public static void clear() {
        while (!isEmpty()) {
            dequeue();
        }
        first = 0;
    }
    /*
    PRE:  true
    POST: R = queue
    */
    public static Object[] toArray() {
        Object[] a = new Object[size];
        for (int i = 0; i < size; i++) {
            a[i] = elements[(first + i) % elements.length];
        }
        return a;
    }
    /*
    PRE:  newElement != null
    POST: size = size' + 1
          1 <= i <= size' : queue[i] = queue[i + 1]
          queue[1] = newElement
    */
    public static void push(Object newElement) {
        ensureCapacity(size + 1);
        first = (first - 1 + elements.length) % elements.length;
        elements[first] = newElement;
        size++;
    }
    /*
    PRE:  size > 0
    POST: R = queue[size]
    */
    public static Object peek() {
        assert size > 0;
        return elements[(first + size - 1) % elements.length];
    }
    /*
    PRE:  size > 0
    POST: R = queue'[size']
          size = size' - 1
          остальные элементы очереди не были изменены
    */
    public static Object remove() {
        assert size > 0;
        Object R = elements[(first + size - 1) % elements.length];
        size--;
        elements[(first + size) % elements.length] = null;
        return R;
    }
}