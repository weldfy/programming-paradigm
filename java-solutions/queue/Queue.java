package queue;

/*
Model: queue[1]..queue[size]
Inv:   size >= 0
       1 <= i <= size : queue[i] != null
*/
public interface Queue {

    /*
    PRE:  newElement != null
    POST: size = size' + 1
          queue[size'] = newElement
          осталльные элементы очереди остались не изменены
    */
    void enqueue(Object element);

    /*
    PRE:  size > 0
    POST: R = element[1]
          элементы очереди остались не изменены
    */
    Object element();

    /*
    PRE:  size > 0
    POST: size = size' - 1
          R = queue'[1]
          2 <= i <= size : queue[i - 1] = queue[i]
    */
    Object dequeue();

    /*
     PRE:  true
     POST: R = size
           элементы очереди не были изменены
    */
    int size();

    /*
    PRE:  true
    POST: R = (size == 0)
          элементы очереди не были изменены
    */
    boolean isEmpty();

    /*
    PRE:  true
    POST: size = 0
    */
    void clear();

    /*
    PRE:  true
    POST: R = queue
    */
    Object[] toArray();

    /*
    PRE:  newElement != null
    POST: size = size' + 1
          1 <= i <= size' : queue[i] = queue[i + 1]
          queue[1] = newElement
    */
    //void push(Object element);

    /*
    PRE:  size > 0
    POST: R = queue[size]
    */
    Object peek();


    /*
    PRE:  element != null
    POST: проверка существует ли X, такое что queue[X] = element
    */
    public boolean contains(Object element);
    /*
    PRE:  element != null
    POST: min X : queue[X] = element
          X < i <= size' : queue[i] = queue[i + 1]
          size' = size + 1
    */
    public boolean removeFirstOccurrence(Object element);
}