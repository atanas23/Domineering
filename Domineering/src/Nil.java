// Empty list.

import java.util.*;
import java.util.function.BiFunction;

public class Nil<A> implements LList<A> {

  public <B> B recursion(B base, Step<A,B> step) {
    return base;
  }

  public LList<A> take(int n) {
    return this;
  }

  public LList<A> drop(int n) {
    return this;
  }

  public <B,C> LList<C> zipWith(LList<B> bs, BiFunction<A,B,C> zipper) {
    return new Nil<C>();
  }

  public String toString() { 
    return _toString(); 
  }

  // See discussion in Cons.java for this:
  public boolean equals(Object object) {
    @SuppressWarnings("unchecked")
    LList<A> xs = (LList<A>)object; 
    return(xs.isEmpty());
  }

  public int hashCode() {
    assert(false) : "hash code for LLists is not implemented";
    return 0; 
  }
}
