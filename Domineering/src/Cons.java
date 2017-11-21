// An immutable lazy list with a head and a tail supplier (cf. the class Lazy).
//
// For the moment, we rely on the default methods provided in the
// interface LList to get a short implementation. A more ambitious
// implementation in terms of efficiency would define methods
// replacing the default ones.
//
// See the class Lazy for an explanation of the idea used here.

import java.util.*;
import java.util.function.Supplier;
import java.util.function.BiFunction;

public class Cons<A> implements LList<A> {
    
  private final A head;
  private final Supplier<LList<A>> supplier; 
  private LList<A> tail; 

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

  // Internal method, which computes the tail, if it isn't already
  // computed, before returning it.  Nobody should use the variable
  // tail, except this method with the same name, not even the other
  // methods in this class:
  private LList<A> _tail() {
    if (tail == null) {
      assert(supplier != null);
      tail = supplier.get();
      assert(tail != null);
    }
    return tail;
  }
  
  public <B> B recursion(B base, Step<A,B> step) {
    return step.apply(head, () -> _tail(), () -> _tail().recursion(base, step));
  }

  // It is not easy to define this using the above method:
  public <B,C> LList<C> zipWith(LList<B> bs, BiFunction<A,B,C> zipper) {
    return(bs.isEmpty() 
           ? new Nil<C>() 
           : new Cons<C>(zipper.apply(head, bs.head()), 
                         () -> _tail().zipWith(bs.tail(),zipper)));
  }

  // The following cannot be defined by default using recursion and
  // fold like everything else, because they are by induction on
  // natural numbers, rather than induction on lists:
  public LList<A> take(int n) {
    assert(n >= 0);
    return (n == 0 || isEmpty() ? new Nil<A>() : new Cons<A>(head, () -> _tail().take(n-1)));
  }

  public LList<A> drop(int n) {
    assert(n >= 0);
    return (n == 0 || isEmpty() ? this : _tail().drop(n-1));
  }

  // This completes the implementation of the interface LList<A>. We
  // now override the implementations of toString(), equals() and
  // (hence) hashCode(),

  public String toString() {
    return _toString();
  }

  // If this list and the argument are both infinite, the following
  // will never get back to its caller:
  public boolean equals(Object object) {

    @SuppressWarnings("unchecked")
    LList<A> xs = (LList<A>)object; // This can go wrong at run time.

    return(xs.isEmpty()
           ? false
           : this.head().equals(xs.head()) &&
             this.tail().equals(xs.tail()));
  }

  // (We could have defined equals() with two nested uses of
  // recursion(), with a helper method, like _toString(), in the
  // interface LList - I leave this an an interesting exercise for
  // you.)

  // Then we also need to override the hashCode. The reason is that if
  // two things are equal(), then they must have the same hashCode,
  // for hash techniques to work. (This is a so-called "contract".)
  //
  // I leave this unimplemented for the moment, but I am kind to
  // uncautious users of this, as follows:

  public int hashCode() {
    assert(false) : "hash code for LLists is not implemented";
    return 0; // Because we must return something.  
  }  

  // With assertions disabled, hash tables will work, but very
  // inefficiently, as we will always get a collision.
}
