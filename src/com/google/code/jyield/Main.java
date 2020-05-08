// Michael Safyan; Copyright (C) 2010; Simplified BSD License.
package com.google.code.jyield;

/**
 * An example program that demonstrates the use of Yieldable and Generator.
 *
 * @author Michael Aaron Safyan (michaelsafyan&#64;gmail.com)
 */
public class Main {
  public static void main(String[] args){
    GeneratorToIterableConverter<Integer> converter =
        new TrivialInterfaceConverter<Integer>();
    Generator<Integer> example = new Generator<Integer>(){
      @Override
      public void generate(Yieldable<Integer> yieldable) {
          int count=0;
          // Although this runs forever, the "yield" operation hands off
          // control to the other threads, and thus the for-loop below
          // prints out the yielded content even though this never returns.
          while (true){
              yieldable.yield(count++);
          }
      }
    };
    for (int i : converter.convertGeneratorToIterable(example)) {
      System.out.println(i);
    }
  }
}
