// Interface for immutable lists.

import java.util.*;
import java.util.function.Predicate;
import java.util.function.Function;
import java.util.function.Consumer;
import java.util.function.BiFunction;

interface LList<A> {
  boolean isEmpty();
  A head();
  LList<A> tail();
  LList<A> take(int n); 
  LList<A> drop(int n);
  void forEach(Consumer<A> c);
  LList<A> filter(Predicate<A> p);
  <B> LList<B> map(Function<A,B> f);
  <B,C> LList<C> zipWith(LList<B> bs, BiFunction<A,B,C> f);
} 
