// Michael Safyan; Copyright (C) 2010; Simplified BSD License.
package com.google.code.jyield.utils;
import com.google.code.jyield.Yieldable;

/**
 * A yieldable object that prints values that it is given,
 * optionally forwarding the values along to another yieldable object.
 * @author Michael Safyan (michaelsafyan@gmail.com)
 */
public class PrintYieldable<T> implements Yieldable<T>
{
    /**
     * Constructs a PrintYieldable object that prints
     * the values it is given and then simply discards them.
     */
    public PrintYieldable(){
    }

    /**
     * Constructs a PrintYieldable object that prints the
     * values that it is given, then forwards them to the forwarder.
     * @param forwarder A non-null Yieldable to which to forward messages.
     */
    public PrintYieldable(Yieldable<T> forwarder){
        if ( forwarder == null ){
            throw new IllegalArgumentException("Forwarder cannot be null.");
        }
        _forwarder = forwarder;
    }
    
    public void yield(T obj) {
        System.out.println(obj);
        if ( _forwarder != null ){
            _forwarder.yield(obj);
        }
    }

    private Yieldable<T> _forwarder = null;
}
