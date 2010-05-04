// Michael Safyan; Copyright (C) 2010; Simplified BSD License.
package com.google.code.jyield.utils;
import java.util.Iterator;

/**
 * Applies a transformation to an iterable object.
 * @author Michael Safyan (michaelsafyan@gmail.com)
 */
public class TransformedIterable<T> implements Iterable<T>
{
    /**
     * Applies the given transformation to the given iterable.
     * @param iterable An iterable object.
     * @param transformation The transformation to apply to each element.
     */
    public TransformedIterable(Iterable<T> iterable, Transformation<T> transformation){
        if ( iterable == null ){
            throw new IllegalArgumentException("Iterable cannot be null.");
        }
        if ( transformation == null ){
            throw new IllegalArgumentException("Transformation cannot be null.");
        }
        _transformation = transformation;
        _iterable = iterable;
    }

    public Iterator<T> iterator() {
        final Iterator<T> iter = _iterable.iterator();
        return new Iterator<T>()
        {

            public boolean hasNext() {
                return iter.hasNext();
            }

            public T next() {
                return _transformation.transform(iter.next());
            }

            public void remove() {
                iter.remove();
            }
        };
    }

    private Iterable<T> _iterable = null;
    private Transformation<T> _transformation = null;
}
