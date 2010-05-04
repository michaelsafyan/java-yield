// Michael Safyan; Copyright (C) 2010; Simplified BSD License.
package com.google.code.jyield;

/**
 * Converts an Iterable object to a generator.
 * @author Michael Safyan
 */
public interface IterableToGeneratorConverter<T>
{
    /**
     * Converts an iterable object to a corresponding generator.
     * @param iterable An iterable object.
     * @return An equivalent generator.
     */
    public Generator<T> convertIterableToGenerator(Iterable<T> iterable);
}
