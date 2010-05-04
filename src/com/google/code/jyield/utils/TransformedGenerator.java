// Michael Safyan; Copyright (C) 2010; Simplified BSD License.
package com.google.code.jyield.utils;
import com.google.code.jyield.Generator;
import com.google.code.jyield.Yieldable;

/**
 * Applies a transformation to a generator object.
 * @author Michael Safyan (michaelsafyan@gmail.com)
 */
public class TransformedGenerator<T> implements Generator<T>
{
    /**
     * Applies the given transformation to the given iterable.
     * @param generator An generator object.
     * @param transformation The transformation to apply to each element.
     */
    public TransformedGenerator(Generator<T> generator, Transformation<T> transformation){
        if ( generator == null ){
            throw new IllegalArgumentException("Generator cannot be null.");
        }
        if ( transformation == null ){
            throw new IllegalArgumentException("Transformation cannot be null.");
        }
        _transformation = transformation;
        _generator = generator;
    }

    public void generate(final Yieldable<T> yieldable) {
        _generator.generate
        (
                new Yieldable<T>()
                {
                    public void yield(T obj) {
                        yieldable.yield(_transformation.transform(obj));
                    }
                }
        );
    }

    private Generator<T> _generator = null;
    private Transformation<T> _transformation = null;
}
