import edu.princeton.cs.algs4.StdOut;
//import jdk.nashorn.internal.objects.ArrayIterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

// NOTE: All required exceptions have already been added to the code

public class DoublyLinkedList<T> implements ILinkedList<T>, Iterable<T>{
    private Node<T> front;
    private Node<T> back;
    private int len = 0;
    // Create a list
    //DoublyLinkedList<T> list = new DoublyLinkedList<>();

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

        if (this.isEmpty()) {
            front = n;
            back = n;
        } else {
            n.setNext(this.front);
            front = n;
        }
        len++;
    }

    public void insertBack(T item) {
        Node<T> n = new Node<>();
        n.setData(item);

        if (this.isEmpty()) {
            front = n;
            back = n;
        } else {
            back.setNext(n);
            n.setPrev(back);
            back = n;
        }
        len++;
    }

    public void insertAt(T item, int position) throws NoSuchElementException {
        Node<T> n = new Node<>();
        Node<T> x = new Node<>();
        n.setData(item);
        x = back;
        if (position >= this.len()) {
            if (back != null) {
                back.setNext(n);
                n.setPrev(back);
                n.setNext(front);
                front.setPrev(n);
                back = n;
            } else if (isEmpty()) {
                front = n;
                back = n;
                back.setPrev(front);
                back.setNext(front);
                front.setNext(back);
                front.setPrev(back);
            }
        }
        else {
            n.setData(item);
            x.setData(getFront());
            x.setNext(front.next);
            if (this.isEmpty()) {
                front = n;
                back = n;
                back.setPrev(front);
                back.setNext(front);
                front.setNext(back);
                front.setPrev(back);
            } else {
                for (int i = 0; i < position; i++) {
                    x = x.getNext();
                }
                if (position == 0) {
                    n.setNext(front);
                    front.setPrev(n);
                    front = n;
                } else if (position == (len() - 1)) {
                    n.setPrev(back);
                    back.setNext(n);
                    back = n;
                } else {
                    n.setNext(x);
                    n.setPrev(x.getPrev());
                    x.getPrev().setNext(n);
                    x.setPrev(n);
                }

            }
        }
        len++;
    }

/*
        n.setData(item);
        x.setData(getFront());
        x.setNext(front.next);
        if (this.isEmpty()) {
            front = n;
            back = n;
            back.setPrev(front);
            back.setNext(front);
            front.setNext(back);
            front.setPrev(back);
        } else {
            for (int i = 0; i < position; i++) {
                x = x.getNext();
            }
            if (position == 0) {
                n.setNext(front);
                front.setPrev(n);
                front = n;
            } else if (position == (len() - 1)) {
                n.setPrev(back);
                back.setNext(n);
                back = n;
            } else {
                n.setNext(x);
                n.setPrev(x.getPrev());
                x.getPrev().setNext(n);
                x.setPrev(n);
            }

        }
        len++;
    }*/

    public T getFront() throws NoSuchElementException {
        if(isEmpty()){ throw new NoSuchElementException("List is empty");}
        return this.front.getData();
    }

    public T getBack() throws NoSuchElementException {
        if(isEmpty()){ throw new NoSuchElementException("List is empty");}
        return this.back.getData();
    }

    public T getAt(int position) throws NoSuchElementException {
        if(isEmpty()){ throw new NoSuchElementException("List is empty");}
        if(position >= len){
            throw new NoSuchElementException("position " + Integer.toString(position) + " does not exist in the list");
        } else {
            int i = 0;
            Node<T> traversal = new Node<>();
            traversal = front;

            while (i < position){
                // go through our list
                traversal = traversal.getNext();
                i++;
            }
            return traversal.getData();
        }
    }

    public void removeFront() throws NoSuchElementException {
        if(isEmpty()){ throw new NoSuchElementException("List is empty");}
        if (len() == 1) {
            front = null;
            back = null;
        } else {
            front.getNext().setPrev(null);
            front = front.getNext();
        }
        len--;
    }

    public void removeBack() throws NoSuchElementException {
        if(isEmpty()){ throw new NoSuchElementException("List is empty");}
        if (len() == 1) {
            front = null;
            back = null;
        } else {
            back.getPrev().setNext(null);
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
        } else {
            for (int i = 0; i < position; i++) {
                n = n.getNext();
            }
            if (n == back) {
                n.getPrev().setNext(null);
                back = n.getPrev();
            } else if (n == front) {
                n.getNext().setPrev(null);
                front = n.getNext();
            } else{
                n.getPrev().setNext(n.getNext());
                n.getNext().setPrev(n.getPrev());
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
        Node<T> n = front;
        while( n != null){
            StdOut.print(n.getData() + " -> ");
            n = n.getNext();
        }
        StdOut.println("back");
    }

    public void printReverse() {
        StdOut.print("back -> ");
        Node<T> n = back;
        while( n != null){
            StdOut.print(n.getData() + " -> ");
            n = n.getPrev();
        }
        StdOut.println("front");
    }

    public void printDataAt(int position) {
        if(position >= len()){
            throw new NoSuchElementException();
        }
        Node<T> looper = front;
        for (int i = 0; i < position; i++) {
            looper = looper.getNext();
        }
        StdOut.print(looper.getData());
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

        public boolean hasNext()  { return current != null; }

        public T next() {
            if (!hasNext()) throw new NoSuchElementException();
            T item = current.getData();
            current = current.getNext();
            return item;
        }
    }

    // if you wish to write specific test code without running Main.java and using StdIn you can use this file's main
    // method to write test code. After you have written your test code you can run it by hilighting this file in the
    // project explorer on the left hand side of your screen, right clicking on it, and selecting `Rebuild`. Once it has
    // built you can run it by right clicking on the file again selecting `Run with arguments`
    public static void main(String[] args){

    }
}
