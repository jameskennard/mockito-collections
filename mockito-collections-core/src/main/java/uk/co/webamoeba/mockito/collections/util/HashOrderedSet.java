package uk.co.webamoeba.mockito.collections.util;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class HashOrderedSet<E> extends AbstractSet<E> implements OrderedSet<E> {

	/**
	 * {@link HashMap} that is used to back the {@link OrderedSet}. Note that we only take advantage of the keys in the
	 * {@link HashMap}, the values are always <code>null</code>.
	 */
	private HashMap<E, Object> map;

	/**
	 * The first {@link Bucket} in the {@link OrderedSet}. If this is <code>null</code> the {@link OrderedSet} will be
	 * empty. If there is only one element in the {@link OrderedSet} this {@link Bucket} will also be the
	 * {@link #lastBucket}.
	 */
	private Bucket firstBucket;

	/**
	 * The last {@link Bucket} in the {@link OrderedSet}. If this is <code>null</code> the {@link OrderedSet} will be
	 * empty. If there is only one element in the {@link OrderedSet} this {@link Bucket} will also be the
	 * {@link #firstBucket}.
	 */
	private Bucket lastBucket;

	public HashOrderedSet() {
		map = new HashMap<E, Object>();
	}

	@Override
	public Iterator<E> iterator() {
		return new BucketIterator(firstBucket);
	}

	@Override
	public boolean add(E e) {
		if (map.containsKey(e)) {
			return false;
		}

		if (lastBucket == null) {
			assert firstBucket == null;
			firstBucket = new Bucket(e);
			lastBucket = firstBucket;
		} else {
			lastBucket = lastBucket.addNext(e);
		}
		map.put(e, null);
		return true;
	}

	@Override
	public int size() {
		return map.size();
	}

	@Override
	public boolean isEmpty() {
		return firstBucket == null;
	}

	@Override
	public boolean contains(Object o) {
		return map.containsKey(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return map.keySet().containsAll(c);
	}

	@Override
	public void clear() {
		map.clear();
		firstBucket = null;
		lastBucket = null;
	}

	private class Bucket {

		private Bucket next;

		private Bucket previous;

		private E contents;

		public Bucket(E contents) {
			this.contents = contents;
		}

		public Bucket addNext(E nextContents) {
			if (this.next != null) {
				throw new IllegalStateException("Attempted to add next bucket, but the next bucket is already defined");
			}
			this.next = new Bucket(nextContents);
			this.next.previous = this;
			return this.next;
		}

		public boolean hasNext() {
			return next != null;
		}

		public boolean hasPrevious() {
			return previous != null;
		}

		public Bucket getNext() {
			return next;
		}

		public Bucket getPrevious() {
			return previous;
		}

		public E getContents() {
			return contents;
		}

	}

	private class BucketIterator implements Iterator<E> {

		private Bucket last;

		private boolean repeatLast = true;

		public BucketIterator(Bucket startBucket) {
			this.last = startBucket;
		}

		public boolean hasNext() {
			return (repeatLast && last != null) || (last != null && last.hasNext());
		}

		public E next() {
			if (last == null || (!repeatLast && last.getNext() == null)) {
				throw new NoSuchElementException("No more elements");
			}
			last = repeatLast ? last : last.getNext();
			repeatLast = false;
			return last.getContents();
		}

		public void remove() {
			Bucket orignialFirstBucket = firstBucket;
			Bucket orignialLastBucket = lastBucket;

			if (last.hasPrevious()) {
				last.getPrevious().next = last.next;
			} else {
				assert last == orignialFirstBucket;
				if (last.hasNext()) {
					firstBucket = last.getNext();
				} else {
					assert last == orignialLastBucket;
					firstBucket = null;
				}
			}

			if (last.hasNext()) {
				last.getNext().previous = last.previous;
			} else {
				assert last == orignialLastBucket;
				if (last.hasPrevious()) {
					lastBucket = last.getPrevious();
				} else {
					assert last == orignialFirstBucket;
					lastBucket = null;
				}
			}

			map.remove(last.getContents());
		}

	}
}
