// Michael Safyan; Copyright (C) 2010; Simplified BSD License.
package com.google.code.jyield;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * A utilities class for dealing with generators and iterators.
 * @author Michael Safyan (michaelsafyan@gmail.com)
 */
public class YieldUtils
{
    /**
     * Converts a generator to an Iterable.
     * @param <T> The type of iteratable/generator.
     * @param generator The generator to convert to an iterable.
     * @return The corresponding iterable for the given generator.
     */
    public static <T> Iterable<T> toIterable(final Generator<T> generator)
    {
        return new Iterable<T>()
            {
                public Iterator<T> iterator()
                {
                    final LinkedList<T> queue = new LinkedList<T>();
                    final Bool done = new Bool();
                    final Yieldable<T> yieldable = new Yieldable<T>()
                        {
                            public void yield(T obj){
                                synchronized(queue){
                                    queue.addLast(obj);
                                    queue.notifyAll();
                                }
                            }
                        };

                     final Iterator<T> result = new Iterator<T>()
                        {
                            public boolean hasNext() {
                                synchronized(queue){
                                    while(true){
                                        while ( (queue.isEmpty()) && (!done.getValue())){
                                            try {
                                                queue.wait();
                                            } catch (InterruptedException ex) {
                                                return false;
                                            }
                                        }
                                        if ( !queue.isEmpty() ){
                                            return true;
                                        }
                                        if ( done.getValue() ){
                                            return false;
                                        }
                                    }
                                }
                            }

                            public T next() {
                                synchronized(queue){
                                    while(true){
                                        while ( (queue.isEmpty()) && (!done.getValue())){
                                            try {
                                                queue.wait();
                                            } catch (InterruptedException ex) {
                                                throw new NoSuchElementException();
                                            }
                                        }
                                        if ( !queue.isEmpty() ){
                                            return queue.removeFirst();
                                        }
                                        if ( done.getValue() ){
                                            throw new NoSuchElementException();
                                        }
                                    }
                                }
                            }

                            public void remove() {
                                throw new UnsupportedOperationException("Not supported.");
                            }
                        };

                      final Thread worker = new Thread()
                        {
                            public void run(){
                                generator.generate(yieldable);
                                synchronized(queue){
                                    done.setValue(true);
                                    queue.notifyAll();
                                }
                            }
                        };

                      worker.start();
                      return result;
                }
            };
    }

    /**
     * Converts an iterable into an equivalent generator.
     * @param <T> The type of the generator/iterable.
     * @param iterable The iterable to convert to a generator.
     * @return An generator for the corresponding iterable.
     */
    public static <T> Generator<T> toGenerator(final Iterable<T> iterable)
    {
        return new Generator<T>()
        {
            public void generate(Yieldable<T> yieldable) {
                for ( T obj : iterable ){
                    yieldable.yield(obj);
                }
            }
        };
    }

    /**
     * Fills and returns the given collection with the elements of the iterable.
     * @param <T> The type of the collection and iterable.
     * @param iterable The source of the values.
     * @param collectiontofill The destination into which to write the values.
     * @return The collection that was filled.
     */
    public static <T> Collection<T> toCollection(Iterable<T> iterable, Collection<T> collectiontofill){
        for ( T item : iterable ){
            collectiontofill.add(item);
        }
        return collectiontofill;
    }

    /**
     * Fills and returns the given collection with the elements fo the given generator.
     * @param <T> The type of the collection and generator.
     * @param generator The source of the values.
     * @param collectiontofill The destination into which to write the values.
     * @return The collection that was filled.
     */
    public static <T> Collection<T> toCollection(Generator<T> generator, final Collection<T> collectiontofill){
        generator.generate
        (
                new Yieldable<T>()
                {
                    public void yield(T obj) {
                        collectiontofill.add(obj);
                    }
                }
        );
        return collectiontofill;
    }

    /**
     * Converts the given datasource to an array list.
     * @param <T> The type of the data.
     * @param iterable The source of the data.
     * @return An array list containing the elements.
     */
    public static <T> ArrayList<T> toArrayList(Iterable<T> iterable){
        ArrayList<T> result = new ArrayList<T>();
        toCollection(iterable,result);
        return result;
    }

    /**
     * Converts the given datasource to an array list.
     * @param <T> The type of the data.
     * @param generator The source of the data.
     * @return An array list containing the elements.
     */
    public static <T> ArrayList<T> toArrayList(Generator<T> generator){
        ArrayList<T> result = new ArrayList<T>();
        toCollection(generator,result);
        return result;
    }

    /**
     * Converts the given datasource to a linked list.
     * @param <T> The type of the data.
     * @param iterable The source of the data.
     * @return A linked list containing the elements.
     */
    public static <T> LinkedList<T> toLinkedList(Iterable<T> iterable){
        LinkedList<T> result = new LinkedList<T>();
        toCollection(iterable,result);
        return result;
    }

    /**
     * Converts the given datasource to a linked list.
     * @param <T> The type of the data.
     * @param generator The source of the data.
     * @return A linked list containing the elements.
     */
    public static <T> LinkedList<T> toLinkedList(Generator<T> generator){
        LinkedList<T> result = new LinkedList<T>();
        toCollection(generator,result);
        return result;
    }

    private static class Bool
    {
        public Bool(){ this(false); }
        public Bool(boolean val){ _value = val; }
        public boolean getValue(){ return _value; }
        public void setValue(boolean val){ _value = val; }
        private boolean _value;
    }
}
