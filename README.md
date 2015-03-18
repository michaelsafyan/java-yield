# Java Yield (java-yield)
This project, initially authored in 2010 and automatically exported from http://code.google.com/p/java-yield, provides a mechanism for writing simpler iterators in Java using a syntax resembling the `yield` keyword provided by Python.

Instead of implementing the more complicated [`Iterable<>`](http://docs.oracle.com/javase/7/docs/api/java/lang/Iterable.html) / [`Iterator<>`](http://docs.oracle.com/javase/7/docs/api/java/util/Iterator.html) interfaces, using this project, one can implement a much simpler [`Generator<>`](https://github.com/michaelsafyan/java-yield/blob/master/src/com/google/code/jyield/Generator.java) interface and then apply [`YieldUtils.toIterable(generator)`](https://github.com/michaelsafyan/java-yield/blob/master/src/com/google/code/jyield/YieldUtils.java) to obtain an `Iterable<>` from the generator. The utility works with both finite and infinite/streaming data.

## Example
Consider a function with this signature:

    public static Iterable<Integer> multipliedBy(Iterable<Integer> sequence, int multiplier);
    
... that needs to produce one (possibly streaming) sequence from another by applying a simple transform. (Note that this particular example would be better solved using the transform capabilities of [`FluentIterable`](http://docs.guava-libraries.googlecode.com/git/javadoc/com/google/common/collect/FluentIterable.html) or [`Iterables`](http://docs.guava-libraries.googlecode.com/git/javadoc/com/google/common/collect/Iterables.html) from the  [Guava](https://github.com/google/guava) collections, but this example generally illustrates the capabilities of this library).

Using `java-yield`, this function could be implemented fairly simply as:

    public static Iterable<Integer> multipliedBy(
        final Iterable<Integer> sequence, final int multiplier) {
      return YieldUtils.toIterable(new Generator<Integer>() {
          @Overrride
          public void generate(Yieldable<Integer> yieldable) {
             for (Integer value : sequence) {
                yieldable.yield((Integer) (multiplier * ((int) value)));
             }
          }
      });
    }

By contrast, implementing this without this library would look something like this:

    public static Iterable<Integer> multipliedBy(
        final Iterable<Integer> sequence, final int multiplier) {
      return new Iterable<Integer>() {
          @Override
          public Iterator<Integer> iterator() {
             return new MultiplierIterator(sequence.iterator(), multiplier);
          }
      };
    }
    
    private static class MultiplierIterator implements Iterator<Integer> {
      private final Iterator<Integer> inputIterator;
      private final int multiplier;
      
      public MultiplierIterator(Iterator<Integer> inputIterator, int multiplier) {
        this.inputIterator = inputIterator;
        this.multiplier = multiplier;
      }
      
      @Override
      public boolean hasNext() {
        return inputIterator.hasNext();
      }

      @Override
      public Integer next(){
        return (Integer) (multiplier * ((int) (inputIterator.next())));
      }

      public void remove(){
        throw new UnsupportedOperationException("This iterator is immutable.");
      }
    }
    
As should be obvious from the example above, this code eliminates a lot of boiilerplate in writing custom Iterables.
