// Empty list.


import java.lang.IllegalArgumentException;
import java.util.*;
import java.util.function.Predicate;
import java.util.function.Function;
import java.util.function.Consumer;
import java.util.function.BiFunction;

public class Nil<A> implements LList<A> {

  public boolean isEmpty() {
    return true;
  }

  public A head() {
    throw new IllegalArgumentException();
  }

  public LList<A> tail() {
    throw new IllegalArgumentException();
  }

  public LList<A> take(int n) {
    return this;
  }

  public LList<A> drop(int n) {
    return this;
  }

  public void forEach(Consumer<A> c) {
  }

  public LList<A> filter(Predicate<A> p) {
    return this;
  }

  public <B> LList<B> map(Function<A,B> f) {
    return new Nil<B>();
  }

  public <B,C> LList<C> zipWith(LList<B> bs, BiFunction<A,B,C> zipper) {
    return new Nil<C>();
  }

  public String toString() { 
    return "Nil"; 
  }
}
