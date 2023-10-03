package queue;

public abstract class AbstractQueue implements Queue {
    private int size = 0;
    public Object element() {
        assert size > 0;
        return doElement();
    }
    public int size() {
        return size;
    }
    public boolean isEmpty() {
        return size == 0;
    }
    public Object peek() {
        assert size > 0;
        return doPeek();
    }
    public Object dequeue() {
        assert size > 0;
        Object R = element();
        newSize(size - 1);
        doDequeue();

        return R;
    }
    public void clear() {
        doClear();
        newSize(0);
    }
    public Object[] toArray() {
        return doToArray();
    }
    public void enqueue(Object newElement) {
        doEnqueue(newElement);
        newSize(size + 1);
    }

    private boolean contains(Object element, boolean del) {
        int n = size;
        boolean answer = false;
        for (int i = 0; i < n; i++) {
            Object x = dequeue();
            if (x.equals(element)) {
                answer = true;
            }
            if (!x.equals(element) || !del) {
                enqueue(x);
            } else {
                del = false;
            }
        }
        return answer;
    }
    void newSize(int newSize) {
        size = newSize;
    }
    public boolean contains(Object element) {
        return contains(element, false);
    }
    public boolean removeFirstOccurrence(Object element) {
        return contains(element, true);
    }
    protected abstract Object doElement();
    protected abstract Object doPeek();
    protected abstract void doDequeue();
    protected abstract void doEnqueue(Object newElement);
    protected abstract Object[] doToArray();
    protected abstract void doClear();
}