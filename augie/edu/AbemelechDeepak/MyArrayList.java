package augie.edu.AbemelechDeepak;

import java.util.Iterator;

public class MyArrayList<E>{

    // Our own implmentation of ArrayList from scratch
    
    // Stores the elements in an array of type E
    private E[] elements;

    // Intital size of the array
    private int size;
    
    // Default constructor
    public MyArrayList(){
        // Set the size to 0
        size = 0;
        // Set the array to a new array of type E with a size of 10

        // Use class cast
        elements = (E[]) new Object[10];
    }

    public MyArrayList(int initialCapacity){
        // Set the size to 0
        size = 0;
        // Set the array to a new array of type E with a size of initialCapacity
        elements = (E[]) new Object[initialCapacity];
    }

    // Add an element to the array
    public void add(E element){
        // If the size is equal to the length of the array
        if(size == elements.length){
            // Create a new array of type E with a size of 3 times the length of the old array
            E[] newElements = (E[]) new Object[elements.length * 3];
            // Copy the elements from the old array to the new array
            System.arraycopy(elements, 0, newElements, 0, elements.length);
            // Set the old array to the new array
            elements = newElements;
        }
        // Add the element to the array
        elements[size] = element;
        // Increment the size
        size++;
    }

    // Remove an element from the array
    public void remove(E element){
        // Loop through the array
        for(int i = 0; i < size; i++){
            // If the element is found
            if(elements[i].equals(element)){
                // Shift the elements to the left
                for(int j = i; j < size - 1; j++){
                    elements[j] = elements[j + 1];
                }
                // Decrement the size
                size--;
                // Break out of the loop
                break;
            }
        }
    }

    // Get the element at a specific index
    public E get(int index){
        // If the index is out of bounds
        if(index < 0 || index >= size){
            // Throw an exception
            throw new IndexOutOfBoundsException();
        }
        // Return the element at the index
        return elements[index];
    }

    // Set the element at a specific index
    public void set(int index, E element){
        // If the index is out of bounds
        if(index < 0 || index >= size){
            // Throw an exception
            throw new IndexOutOfBoundsException();
        }
        // Set the element at the index
        elements[index] = element;
    }

    // Get the size of the array
    public int size(){
        // Return the size
        return size;
    }

    // Check if the array is empty
    public boolean isEmpty(){
        // Return true if the size is 0
        return size == 0;
    }

    // Check if the array contains an element
    public boolean contains(E element){
        // Loop through the array
        for(int i = 0; i < size; i++){
            // If the element is found
            if(elements[i].equals(element)){
                // Return true
                return true;
            }
        }
        // Return false
        return false;
    }

    // Clear the array
    public void clear(){
        // Set the size to 0
        size = 0;
    }

    // Create a iterator to loop through the array
    public Iterator<E> iterator(){
        // Return a new iterator
        return new Iterator<E>(){
            // Index of the iterator
            private int index = 0;

            // Check if there is a next element
            public boolean hasNext(){
                // Return true if the index is less than the size
                return index < size;
            }

            // Get the next element
            public E next(){
                // Return the element at the index and increment the index
                return elements[index++];
            }
        };
    }

    
}
