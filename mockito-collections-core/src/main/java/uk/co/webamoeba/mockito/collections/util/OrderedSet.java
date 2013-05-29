package uk.co.webamoeba.mockito.collections.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Implementation of {@link Set} that maintains the order in which the items were inserted. Equality is defined by both
 * contents and ordering. That is to say, given two {@link OrderedSet OrderedSets} with the same elements but in a
 * different order, they will not be considered equal.
 * 
 * @author James Kennard
 * @param <E>
 */
public class OrderedSet<E> implements Set<E> {

	private LinkedHashSet<E> set;

	public OrderedSet() {
		this.set = new LinkedHashSet<E>();
	}

	public OrderedSet(Collection<E> set) {
		this.set = new LinkedHashSet<E>(set);
	}

	public OrderedSet(int initialCapacity) {
		this.set = new LinkedHashSet<E>(initialCapacity);
	}

	public Object[] toArray() {
		return set.toArray();
	}

	public boolean removeAll(Collection<?> c) {
		return set.removeAll(c);
	}

	public <T> T[] toArray(T[] a) {
		return set.toArray(a);
	}

	public Iterator<E> iterator() {
		return set.iterator();
	}

	public int size() {
		return set.size();
	}

	public boolean isEmpty() {
		return set.isEmpty();
	}

	public boolean contains(Object o) {
		return set.contains(o);
	}

	public boolean add(E o) {
		return set.add(o);
	}

	public boolean remove(Object o) {
		return set.remove(o);
	}

	public void clear() {
		set.clear();
	}

	@Override
	public Object clone() {
		return set.clone();
	}

	public boolean containsAll(Collection<?> c) {
		return set.containsAll(c);
	}

	public boolean addAll(Collection<? extends E> c) {
		return set.addAll(c);
	}

	public boolean retainAll(Collection<?> c) {
		return set.retainAll(c);
	}

	@Override
	public String toString() {
		return set.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || !getClass().equals(obj.getClass())) {
			return false;
		}
		return set.equals(obj);
	}

	@Override
	public int hashCode() {
		return 31 * 1 + ((set == null) ? 0 : set.hashCode());
	}
}
