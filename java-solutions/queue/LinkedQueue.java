package queue;

public class LinkedQueue extends AbstractQueue {
    private Node first = new Node();
    private Node last = new Node();
    protected Object doElement() {
        return first.x;
    }
    protected Object doPeek() {
        return last.x;
    }
    protected void doDequeue() {
        if (size() == 0) {
            first = new Node();
            last = new Node();
            return;
        }
        first = first.right;
    }
    protected void doEnqueue(Object newElement) {
        Node newNode = new Node(newElement);
        if (size() == 0) {
            last = newNode;
            first = newNode;
            return;
        }
        last.right = newNode;
        last = newNode;
    }
    protected Object[] doToArray() {
        Object[] array = new Object[size()];
        Node element = first;
        for (int i = 0; i < size(); i++) {
            array[i] = element.x;
            element = element.right;
        }
        return array;
    }
    protected void doClear() {
        first = new Node();
        last = new Node();
    }
}