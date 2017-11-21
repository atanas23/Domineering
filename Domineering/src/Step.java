// Functions for lazy list recursion step.

import java.util.function.Supplier;

interface Step<A,B> {
  B apply(A a, Supplier<LList<A>> as, Supplier<B> b);
}
