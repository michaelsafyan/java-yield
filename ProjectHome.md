# Java Yield and Other Iteration Utilities for Java #
**NOTE**: Our [latest documentation](https://java-yield.googlecode.com/hg/docs/index.html) may be found at the link.
## Overview ##
This project is a simple implementation of yield, in addition to a variety of other iteration utilities for Java. For those of you not familiar with yield, some languages have a flow-of-control idiom called "yield" that allows a function to act as an iterator by returning multiple values:

```
    # This is some example Python code
    # defining an iterator that doubles each element
    def twice_iterator(iter):
        for x in iter:
            yield 2*x;


    # This is some example Python code that uses it:
    for x in twice_iterator([1,2,3,4,5]):
        print x;

    # The results are 2,4,6,8,10
```

This yield feature is actually quite cool, and implementing the equivalent Java for the above without it involves some extra boiler-plate:

```
// TwiceIterator.java
public class TwiceIterator<Integer> implements Iterator<Integer>
{
      public TwiceIterator(Iterator<Integer> iter){
            _iter = iter;
      }

      public boolean hasNext(){
             return _iter.hasNext();
      }

      public Integer next(){
             return 2*((int) _iter.next());
      }    

      public void remove(){
             _iter.remove();
      }

      private Iterator<Integer> _iter;
}

// TwiceIterable.java
public class TwiceIterable<Integer> implements Iterable<Integer>
{
      public TwiceIterable(Iterable<Integer> iterable){
             _iterable = iterable;
      }

      public Iterator<Integer> iterator(){
             return new TwiceIterator(_iterable.iterator());
      }     

      private Iterable<Integer> _iterable;
}

// Some code that uses it
List<Integer> original = Arrays.asList(1,2,3,4,5);
Iterable<Integer> twice = new TwiceIterable(original);
for ( int x : twice ){
    System.out.println(x);
}
```

Suffice it to say that this is quite tedious. With the Java Yield package, it is possible to write the equivalent as follows, using the "yield" idiom:

```
// TwiceGenerator.java
public class TwiceGenerator implements Generator<Integer>
{
     public TwiceGenerator(Iterable<Integer> original){
          _original = original;
     }

     public void generate(Yieldable<Integer> yieldable){
          for ( int x : _original ){
                yieldable.yield(2*x);
          }
     }

     private Iterable<Integer> _original;
}

// Some code that uses it
List<Integer> original = Arrays.asList(1,2,3,4,5);
for ( int x : YieldUtils.toIterable(new TwiceGenerator(original)) ){
    System.out.println(x);
}
```

Although there are existing packages out there that bring the "yield" idiom to Java, I felt that most of them were not good enough -- many of them completely fill a buffer and then simply iterate over the buffer. This implementation, on the other hand, makes it possible to do something like:

```
// Count forever
public class CountForever implements Generator<Integer>
{
    public void generate(Yieldable<Integer> yieldable){
           int x = 0;
           while (true){
                yieldable.yield(x++);
           }
    }
}

// Usage:
for ( int counter : YieldUtils.toIterable(new CountForever()) ){
    System.out.println(counter);
}
```

The example above will actually give output; the generation of values and their consumption is actually interleaved. While this is cool, it also means that there is multi-threading (and synchronization) going on, and the synchronization may be undesireable in certain cases. However, that is cool, too, as I have taken care to make the code dominated by interfaces rather than by concrete classes; suppose there is some generator class and you want to iterate over it really quickly without spawning any threads... then just use the old-fashioned visitor pattern like the following:

```
somegenerator.generate
(
     new Yieldable<T>()
     {
             public void yield(T obj){
                    // Do whatever processing you need to do on the element
             }
     }
);
```

By representing everything using interfaces, you are not tied to my multi-threaded implementation of YieldUtils.toIterable() for converting Generator interfaces to Iterable interfaces. Also, although the locking overhead is significant when producing and consuming values is quick, it is actually not bad at all and allows production and consumption of values to occur in parallel when production and consumption take a long time.

## Core Classes ##
  * [Generator](https://java-yield.googlecode.com/hg/docs/com/google/code/jyield/Generator.html) - An interface that is implemented by generating classes.
  * [Yieldable](https://java-yield.googlecode.com/hg/docs/com/google/code/jyield/Yieldable.html) - An interface representing any object to which values may be yielded.
  * [YieldUtils](https://java-yield.googlecode.com/hg/docs/com/google/code/jyield/YieldUtils.html) - Converts between Generator, Iterable, and other collections.

## Utilities ##
  * [Range](https://java-yield.googlecode.com/hg/docs/com/google/code/jyield/utils/Range.html) - A Python-like range object that can act as both a Generator and Iterable

```
// Prints 0,1,2,3,4
for ( int x : new Range(5) ){
    System.out.println(x);
}

// Prints 0,-1,-2,-3,-4
for ( int x : new Range(-5) ){
    System.out.println(x);
}

// Prints 3,4,5,6,7,8,9
for ( int x : new Range(3,10) ){
    System.out.println(x);
}

// Prints 10,9,8,7,6
for ( int x : new Range(10,5) ){
    System.out.println(x);
}

// Prints 0,2,4,6,8,10
for ( int x : new Range(0,11,2) ){
    System.out.println(x)
}
```