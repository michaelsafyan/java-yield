// Michael Safyan; Copyright (C) 2010; Simplified BSD License.
package com.google.code.jyield.utils;
import com.google.code.jyield.Generator;
import com.google.code.jyield.Yieldable;
import java.util.Iterator;

/**
 * A simple implementation of a range object.
 * @author Michael Safyan (michaelsafyan@gmail.com)
 */
public class Range implements Iterable<Integer>, Generator<Integer>
{
    /**
     * Constructs a range that starts with zero and contains the number
     * of elements specified; if a negative number is specified, then
     * the range goes in the reverse direction. In other words,
     * this specifies the range [0,..,number-1] in the case where
     * a positive number is given, and the range [0,...,number+1] in the
     * case where the number specified is a negative number. For example,
     * Range(5) is {0,1,2,3,4}, while Range(-5) is {0,-1,-2,-3,-4}.
     *
     * @param number The number of elements, or (if the reverse range), the negative number of elements.
     */
    public Range(int number){
        _start = 0;
        _finish = number;
        if ( number < 0 ){    
            _step = -1;
        }else{
            _step = 1;
        }
    }

    /**
     * Constructs a range that begins with the given start point and goes
     * either forwards or backwards by one, stopping before the given endpoint.
     * @param start The first elemenent, inclusive.
     * @param end The last element, exclusive.
     */
    public Range(int start, int end){
        _start = start;
        _finish = end;
        if ( _start <= _finish ){
            _step = 1;
        }else{
            _step = -1;
        }
    }

    /**
     * Constructs a range with the given endpoints and the given step size. The
     * step must be non-zero and must be in the direction such that the
     * end point will eventually be reached; in other words, the step
     * must be positive if end > start, while the step must be negative
     * if end<start. This is used to ensure that a range is finite.
     * 
     * @param start The start of the range, inclusive.
     * @param end The end of the range, exclusive.
     * @param step The value by which to step.
     */
    public Range(int start, int end, int step){
        if ( (start<end) && (step<=0) ){
            throw new IllegalArgumentException("Step must be >=1 for start < end.");
        }else if ( (start>end) && (step>=0) ){
            throw new IllegalArgumentException("Step must be <=-1 for start > end.");
        }else if ( step == 0 ){
            throw new IllegalArgumentException("Step may not be 0.");
        }
        _start = start;
        _finish = end;
        _step = step;
    }

    /**
     * Gets the element that would be at the given offset, assuming that
     * the range continued on infinitely. This does not perform any bounds
     * checking, so you may want to check the result against contains().
     * @param idx An offset into the range/sequence.
     * @return The value that would appear at that index in the absence of bounds checking.
     */
    public int getElement(int idx){
        return _start+idx*_step;
    }

    /**
     * Reports the first element, inclusive in the sequence.
     * @return The first element, inclusive in the sequence.
     */
    public int getStart(){
        return _start;
    }

    /**
     * Reports the last element, exclusive in the sequence.
     * @return The last element, exclusive in the sequence.
     */
    public int getFinish(){
        return _finish;
    }

    /**
     * Reports the size of the step between consecutive elements. Note
     * that this value can be negative when going from high values to low ones.
     * @return The step size used.
     */
    public int getStep(){
        return _step;
    }

    /**
     * Determines if the given value is in the range.
     * @param value An integer.
     * @return True if the value lies in the range.
     */
    public boolean contains(int value){
        if ( _step > 0 ){
            return ((value>=_start)&&(value<_finish));
        }else{
            return ((value<=_start)&&(value>_finish));
        }
    }

    public void generate(Yieldable<Integer> yieldable) {
        if ( _step > 0 ){
            for ( int i = _start; i < _finish; i+= _step){
                yieldable.yield(i);
            }
        }else {
            for ( int i = _start; i > _finish; i+= _step){
                yieldable.yield(i);
            }
        }
    }

    public Iterator<Integer> iterator() {
        return new RangeIterator();
    }

    private class RangeIterator implements Iterator<Integer>
    {
        public RangeIterator(){
            _val = _start;
        }

        public boolean hasNext() {
            return ((_step>0)&&(_val<_finish))||((_step<0)&&(_val>_finish));
        }

        public Integer next() {
            int result = _val;
            _val += _step;
            return result;
        }

        public void remove() {
            throw new UnsupportedOperationException("Not supported.");
        }

        private int _val = 0;
    }

    private int _start;
    private int _finish;
    private int _step;
}
