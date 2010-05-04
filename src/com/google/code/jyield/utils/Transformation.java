// Michael Safyan; Copyright (C) 2010; Simplified BSD License.
package com.google.code.jyield.utils;

/**
 * A class that applies some transformation to the given object.
 * @author Michael Safyan (michaelsafyan@gmail.com)
 */
public interface Transformation<T>
{
    /**
     * Applies some transformation to the given object.
     * @param obj An object to transform.
     * @return The result of transforming the object.
     */
    public T transform(T obj);
}
