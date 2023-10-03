package queue;

public class Node {
    Node right;
    Object x;

    public Node (Object x) {
        this.x = x;
        this.right = null;
    }
    public Node () {
        this.x = null;
        this.right = null;
    }
    public Node (Object x, Node node) {
        this.x = x;
        this.right = node;
    }
}