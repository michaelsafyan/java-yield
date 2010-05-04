// Michael Safyan; Copyright (C) 2010; Simplified BSD License.
package com.google.code.jyield;

/**
 * A trivial implementation of a GeneratorToIterableConverter.
 * @author Michael Safyan (michaelsafyan@gmail.com)
 */
public class TrivialInterfaceConverter<T> implements GeneratorToIterableConverter<T>, IterableToGeneratorConverter<T>
{
    public Iterable<T> convertGeneratorToIterable(final Generator<T> generator) {
        return YieldUtils.toIterable(generator);
    }

    public Generator<T> convertIterableToGenerator(final Iterable<T> iterable) {
        return YieldUtils.toGenerator(iterable);
    }
}
