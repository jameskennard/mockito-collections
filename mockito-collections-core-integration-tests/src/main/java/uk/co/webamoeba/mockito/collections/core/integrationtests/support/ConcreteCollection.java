package uk.co.webamoeba.mockito.collections.core.integrationtests.support;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class ConcreteCollection<T extends Object> implements Collection<T> {

	private List<T> collection = new ArrayList<T>();

	public int size() {
		return collection.size();
	}

	public boolean isEmpty() {
		return collection.isEmpty();
	}

	public boolean contains(Object o) {
		return collection.contains(o);
	}

	public Iterator<T> iterator() {
		return collection.iterator();
	}

	public Object[] toArray() {
		return collection.toArray();
	}

	public <A> A[] toArray(A[] a) {
		return collection.toArray(a);
	}

	public boolean add(T e) {
		return collection.add(e);
	}

	public boolean remove(Object o) {
		return collection.remove(o);
	}

	public boolean containsAll(Collection<?> c) {
		return collection.containsAll(c);
	}

	public boolean addAll(Collection<? extends T> c) {
		return collection.addAll(c);
	}

	public boolean removeAll(Collection<?> c) {
		return collection.removeAll(c);
	}

	public boolean retainAll(Collection<?> c) {
		return collection.retainAll(c);
	}

	public void clear() {
		collection.clear();
	}

	@Override
	public boolean equals(Object o) {
		return collection.equals(o);
	}

	@Override
	public int hashCode() {
		return collection.hashCode();
	}

}
