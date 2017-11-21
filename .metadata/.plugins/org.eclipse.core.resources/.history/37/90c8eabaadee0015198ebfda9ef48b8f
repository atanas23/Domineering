// An immutable lazy list with a head and a tail supplier.
// See the class ../laziness/Lazy for an explanation of the idea used here.

import java.util.*;
import java.util.function.Supplier;
import java.util.function.Predicate;
import java.util.function.Function;
import java.util.function.Consumer;
import java.util.function.BiFunction;

public class Cons<A> implements LList<A> {
    
  private final A head;
  private final Supplier<LList<A>> supplier; 
  private LList<A> tail; // We don't make this final.

  public Cons(A head,  Supplier<LList<A>> supplier) {
    assert(supplier != null);  
    this.head = head;
    this.tail = null; // We don't know it yet.
    this.supplier = supplier;
  }

  // Convenience constructor (requires careful handling of nullity).
  // Should be used with care to avoid infinite loops:
  public Cons(A head,  LList<A> tail) {
    assert(tail != null);  
    this.head = head;
    this.tail = tail;
    this.supplier = null; // There is no supplier here.
  }

  public A head() {
    return head;
  }

  // Method that computes and returns the tail, if it isn't already
  // computed, before returning it.  Nobody should use the variable
  // tail, except this method with the same name, not even the other
  // methods in this class:
  public LList<A> tail() {
    if (tail == null) {
      assert(supplier != null);
      tail = supplier.get();
      assert(tail != null);
    }
    return tail;
  }
  
  public boolean isEmpty() {
    return false;
  }

  public LList<A> take(int n) {
    assert(n >= 0);
    return (n == 0 || isEmpty() 
            ? new Nil<A>() 
            : new Cons<A>(head, () -> tail().take(n-1)));
  }

  public LList<A> drop(int n) {
    assert(n >= 0);
    return (n == 0 || isEmpty() 
            ? this 
            : tail().drop(n-1));
  }

  public void forEach(Consumer<A> c) {
    for (LList<A> as = this; !as.isEmpty(); as = as.tail()) 
      c.accept(as.head());

    // // Old implementation:
    // c.accept(head);
    // tail().forEach(c);
  }

  public LList<A> filter(Predicate<A> p) {
    return (p.test(head) 
            ? new Cons<A>(head, () -> tail().filter(p))
            : tail().filter(p));
  }

  public <B> LList<B> map(Function<A,B> f) {
    return new Cons<B>(f.apply(head), () -> tail().map(f));
  }

  public <B,C> LList<C> zipWith(LList<B> bs, BiFunction<A,B,C> f) {
    return(bs.isEmpty() 
           ? new Nil<C>() 
           : new Cons<C>(f.apply(head, bs.head()), 
                         () -> tail().zipWith(bs.tail(), f)));
  }

  public String toString() {
    return "Cons(" + head.toString() + "," + tail().toString() + ")";
  }
}
