// Michael Safyan; Copyright (C) 2010; Simplified BSD License.
package com.google.code.jyield;

/**
 * A generator is some object that is able to construct one
 * or more objects and to submit them to the yieldable object.
 * @author Michael Safyan (michaelsafyan@gmail.com)
 */
public interface Generator<T>
{
    /**
     * A function that will repeatedly yield values to the
     * yieldable object, until there are no values remaining.
     * It is not required that the generate function ever terminate.
     * @param yieldable An object capable of receiving yields.
     */
    public void generate(Yieldable<T> yieldable);
}
