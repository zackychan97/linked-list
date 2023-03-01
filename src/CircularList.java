import edu.princeton.cs.algs4.StdOut;

import java.util.NoSuchElementException;
import java.util.Iterator;

// NOTE: All required exceptions have already been added to the code
// HINT: This code will be _almost_ identical to the DoublyLinkedList code. Slight modifications to the insert/remove methods
//       will need to be done
public class CircularList<T> implements ILinkedList<T>, Iterable<T>{
    private Node<T> front;
    private Node<T> back;

    private int len;

/*
    private Node<T> getNodeAtPosition(int pos) throws NoSuchElementException {
        if (pos >= len()) {
            throw new NoSuchElementException("requested position greater then length of list");
        }

        return null;
    }

 */

    public void insertFront(T item) {
        Node<T> n = new Node<>();
        n.setData(item);

        if (isEmpty()) {
            front = n;
            back = n;
        } else{
            front.setPrev(n);
            n.setNext(front);
            front = n;
        }
        len++;
    }

    public void insertBack(T item) {
        Node<T> n = new Node<>();
        n.setData(item);

        if (isEmpty()) {
            front = n;
            back = n;
        } else{
            back.setNext(n);
            n.setPrev(back);
            n.setNext(front);
            back = n;
        }
        len++;
    }

    public void insertAt(T item, int position) throws NoSuchElementException {
        Node<T> n = new Node<>();
        n.setData(item);
        Node<T> x = new Node<>();
        x = front;
        if (position >= this.len()) {
            //throw new NoSuchElementException("requested position greater then length of list");
            if (isEmpty()) {
                front = n;
                back = n;
            } else {
                n.setNext(front);
                n.setPrev(back);
                back.setNext(n);
                front.setPrev(n);
                back = n;
            }
        } else if (this.isEmpty()) {
            front = n;
            back = n;
        } else {
            for (int i = 0; i <= position; i++) {
                x = x.getNext();
            }
            if (position == 0) {
                n.setNext(front);
                n.setPrev(back);
                front.setPrev(n);
                front = n;
                back.setNext(front);
            } else if (position == (len() - 1)) {
                n.setNext(back);
                back.getPrev().setNext(n);
                back.setPrev(n);
            } else{
                n.setNext(x.getPrev());
                n.setPrev(x.getPrev().getPrev());
            }
        }
        len++;
    }

    public T getFront() throws NoSuchElementException {
        if(isEmpty()){ throw new NoSuchElementException("List is empty");}
        return front.getData();
    }

    public T getBack() throws NoSuchElementException {
        if(isEmpty()){ throw new NoSuchElementException("List is empty");}
        return back.getData();
    }

    public T getAt(int position) throws NoSuchElementException {
        Node<T> x = new Node<>();
        x = front;

        if(isEmpty()){ throw new NoSuchElementException("List is empty");}
        if(position >= len){
            throw new NoSuchElementException("position " + Integer.toString(position) + " does not exist in the list");
        }
        for (int i = 0; i < position; i++) {
            x = x.getNext();
        }
        return x.getData();
    }

    public void removeFront() throws NoSuchElementException {
        if(isEmpty()){ throw new NoSuchElementException("List is empty");}

        if (len() == 1) {
            front = null;
            back = null;
        } else {
            back.setNext(front.getNext());
            front = front.getNext();
            front.setPrev(back);
        }
        len--;
    }

    public void removeBack() throws NoSuchElementException {
        if(isEmpty()){ throw new NoSuchElementException("List is empty");}

        if (len() == 1) {
            front = null;
            back = null;
        } else {
            back.getPrev().setNext(front);
            front.setPrev(back.getPrev());
            back = back.getPrev();
        }
        len--;
    }

    public void removeAt(int position) throws NoSuchElementException {
        Node<T> n = new Node<>();
        n = front;

        if(isEmpty()){ throw new NoSuchElementException("List is empty");}
        else if(position >= len()){ throw new NoSuchElementException("Position greater then list length");}
        else if (len() == 1 && position == 0) {
            front = null;
            back = null;
        }
        else{
            for (int i = 0; i < position; i++) {
                n = n.getNext();
            }
            if (n.getNext() == front) {
                back.getPrev().setNext(front);
                front.setPrev(back.getPrev());
                back = back.getPrev();
            } else if (n.getPrev() == back) {
                back.setNext(n.getNext());
                front.getNext().setPrev(back);
                front = front.getNext();
            }
        }
        len--;
    }

    public int len() {
        return len;
    }

    public boolean isEmpty() {
        if (front == null && back == null) {
            return true;
        }
        return false;
    }

    public void print() {
        StdOut.print("front -> ");
        if(isEmpty()){
            StdOut.println("back");
        } else {
            Node<T> n = front;
            int i = 0;
            while( i < len()) {
                StdOut.print(n.getData() + " -> ");
                n = n.getNext();
                i++;
            }
            StdOut.println("back");
        }
    }

    public void printReverse() {
        StdOut.print("back -> ");
        if(isEmpty()){
            StdOut.println("front");
        } else {
            Node<T> n = back;
            int i = 0;
            while( i < len()) {
                StdOut.print(n.getData() + " -> ");
                n = n.getPrev();
                i++;
            }
            StdOut.println("front");
        }
    }

    public void printDataAt(int position) {
        if(position >= len()){
            throw new NoSuchElementException("position is greater then the length of the list");
        }

        Node<T> n = new Node<>();
        n = front;
        for (int i = 0; i < position; i++) {
            n = n.getNext();
        }
        StdOut.println(n.getData());
    }

    /* The iterator has already been completed for you, do not modify this function */
    public Iterator<T> iterator() {
        return new LinkedIterator(front);
    }

    /* The iterator has already been completed for you, do not modify this class or any functions inside of it */
    private class LinkedIterator implements Iterator<T> {
        private Node<T> current;

        public LinkedIterator(Node<T> first) {
            current = first;
        }

        public boolean hasNext()  {
            return current.getNext() != front;
        }

        public T next() {
            if (!hasNext()) throw new NoSuchElementException();
            T item = current.getData();
            current = current.getNext();
            return item;
        }
    }

    /* this method is fully implemented, do not modify */
    public boolean isCircularForward(){
        Node<T> n = front;
        if(isEmpty()){
            return true;
        }
        do {
            n = n.getNext();
            if(n == null){
                return false;
            }
        } while(n != front);
        return true;
    }

    /* this method is fully implemented, do not modify */
    public boolean isCircularBackward(){
        if(isEmpty()){
            return true;
        }
        Node<T> n = front.prev;
        do {
            n = n.getPrev();
            if(n == null){
                return false;
            }
        } while(n != front.prev);
        return true;
    }

    // if you wish to write specific test code without running Main.java and using StdIn you can use this file's main
    // method to write test code. After you have written your test code you can run it by hilighting this file in the
    // project explorer on the left hand side of your screen, right clicking on it, and selecting `Rebuild`. Once it has
    // built you can run it by right clicking on the file again selecting `Run with arguments`
    public static void main(String[] args){

    }
}
