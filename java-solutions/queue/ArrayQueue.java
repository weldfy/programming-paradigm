package queue;

import java.util.Arrays;

public class ArrayQueue extends AbstractQueue {
    private int first = 0;
    private Object[] elements = new Object[2];
    protected Object doElement() {
        return elements[first];
    }
    protected Object doPeek() {
        return elements[(first + size() - 1) % elements.length];
    }
    protected void doDequeue() {
        elements[first] = null;
        first = (first + 1) % elements.length;
    }
    public Object remove() {
        assert size() > 0;
        Object R = doPeek();
        newSize(size() - 1);
        elements[(first + size()) % elements.length] = null;
        return R;
    }
    protected Object[] doToArray() {
        Object[] array = new Object[size()];
        if (first + size() < elements.length) {
            System.arraycopy(elements, first, array, 0, size());
        } else {
            System.arraycopy(elements, first, array, 0, elements.length - first);
            System.arraycopy(elements, 0, array,
                    elements.length - first, size() - elements.length + first);
        }
        return array;
    }
    private void ensureCapacity(int newSize) {
        if (newSize == elements.length) {
            elements = doToArray();
            elements = Arrays.copyOf(elements, newSize * 2);
            first = 0;
        }
    }
    protected void doEnqueue(Object newElement) {
        ensureCapacity(size() + 1);
        elements[(first + size()) % elements.length] = newElement;
    }
    public void push(Object newElement) {
        ensureCapacity(size() + 1);
        first = (first - 1 + elements.length) % elements.length;
        elements[first] = newElement;
        newSize(size() + 1);
    }
    protected void doClear() {
        for (int i = 0; i < size(); i++) {
            doDequeue();
        }
    }
}