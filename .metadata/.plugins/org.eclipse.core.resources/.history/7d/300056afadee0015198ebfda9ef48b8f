// Static lazy lists utilities for LList.

class LLists {

  public static <A extends Comparable<A>> A maximum(LList<A> as) {
    if (as.isEmpty())
      // The empty list has no maximum element:
      throw new IllegalArgumentException();

    A maximum = as.head(); // maximum element so far.
    as = as.tail();        // This won't change the callers list as.
                    
    while (!as.isEmpty()) {
      A candidateBigger = as.head(); 

      if (candidateBigger.compareTo(maximum) > 0)
        maximum = candidateBigger; // The candidate is bigger.

      as = as.tail();
    }
    
    return maximum;
  }

  // A drawback of the above method is that it will necessarily need
  // to scan the whole list as, nullifying laziness. 

  // If we have an upper bound beta for the elements of the list as,
  // sometimes we won't need to scan the whole list to determine its
  // maximum value.  We can "prune" the search.

  // Definition: an upper bound of a list is a number which is >= all
  // elements of the list. The upper bound need not occur in the list
  // itself.

  public static <A extends Comparable<A>> A boundedMaximum(LList<A> as, A beta) {
    // Pre-condition: beta is an upper bound of the list as.  
    //                (It need not be in the list, though.)
    //
    // Warning: We could use an assertion for the pre-condition, but
    // this would have the side-effect of fully evaluating the list,
    // and assertions should never have side-effects, in particular
    // not this side-effect in our case, as this would defeat the
    // whole point of this method.

    if (as.isEmpty())
      throw new IllegalArgumentException();

    A maximum = as.head(); 
    as = as.tail(); 
                    
    // If maximum becomes equal to beta, we may stop scanning the list as.
    while (maximum.compareTo(beta) < 0 && !as.isEmpty()) {
      A candidateBigger = as.head(); 

      if (candidateBigger.compareTo(maximum) > 0)
        maximum = candidateBigger;

      as = as.tail();
    }
    
    // We can now partially check our pre-condition without side-effects:
    assert(maximum.compareTo(beta) <= 0);

    // Post-condition: maximum is an element of the list as, and is an
    // upper bound of the list (so it is really the maximum element of
    // the list). We promise that the list as is only fully evaluated
    // to the extent it needs to for its maximum value to be
    // determined.
    return maximum;
  }

  // We do the same for minimum, but the lower bound named alpha.  No
  // need to repeat the comments. But we repeat the code (we could
  // avoid code repetition by using the opposite order on the type A
  // and calling the maximum methods - I leave this as an interesting
  // exercise for the interested students).

  public static <A extends Comparable<A>> A minimum(LList<A> as) {
    if (as.isEmpty())
      throw new IllegalArgumentException();

    A minimum = as.head(); 
    as = as.tail(); 
                    
    while (!as.isEmpty()) {
      A candidateSmaller = as.head(); 

      if (candidateSmaller.compareTo(minimum) < 0)
        minimum = candidateSmaller;

      as = as.tail();
    }
    
    return minimum;
  }

  public static <A extends Comparable<A>> A boundedMinimum(LList<A> as, A alpha) {
    if (as.isEmpty())
      throw new IllegalArgumentException();

    A minimum = as.head(); 
    as = as.tail(); 
                    
    while (minimum.compareTo(alpha) > 0 && !as.isEmpty()) {
      A candidateSmaller = as.head(); 

      if (candidateSmaller.compareTo(minimum) < 0)
        minimum = candidateSmaller;

      as = as.tail();
    }
    
    assert(minimum.compareTo(alpha) >=0);
    return minimum;
  }
}
