// Michael Safyan; Copyright (C) 2010; Simplified BSD License.
package com.google.code.jyield;

/**
 * A class that can convert a generator to an iterable object.
 * @author Michael Safyan (michaelsafyan@gmail.com)
 */
public interface GeneratorToIterableConverter<T>
{
    /**
     * Converts a generator to an iterable object.
     * @param generator A generator object.
     * @return An iterable for that generator.
     */
    Iterable<T> convertGeneratorToIterable(Generator<T> generator);
}
