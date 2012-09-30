package uk.co.webamoeba.mockito.collections.util;

import java.util.Set;

/**
 * Similar to a {@link Set} except that when iterating over the {@link Set} the order is guaranteed to be the same as
 * the order in which the items were inserted.
 * 
 * @author James Kennard
 * @param <E>
 */
public interface OrderedSet<E extends Object> extends Set<E> {

}
