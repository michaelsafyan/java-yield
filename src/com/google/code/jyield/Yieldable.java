// Michael Safyan; Copyright (C) 2010; Simplified BSD License.
package com.google.code.jyield;

/**
 * An object to which it is possible to yield a result. This
 * interface may also be used as a generic visitor interface.
 * @author Michael Aaron Safyan (michaelsafyan@gmail.com)
 */
public interface Yieldable<T>
{
    /**
     * Submits the object to the yieldable instance, enabling it to be processed.
     * @param obj The object to be yielded/submitted.
     */
    public void yield(T obj);
}
