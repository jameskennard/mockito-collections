package uk.co.webamoeba.mockito.collections.inject;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * A Set of {@link CollectionOfMocksField CollectionOfMocksFields}.
 * 
 * @author James Kennard
 */
public class CollectionOfMocksFieldSet implements Iterable<CollectionOfMocksField<Collection<Object>, Object>> {

	private Set<CollectionOfMocksField<Collection<Object>, Object>> collectionOfMocksFields = new HashSet<CollectionOfMocksField<Collection<Object>, Object>>();

	@SuppressWarnings("rawtypes")
	public CollectionOfMocksFieldSet(CollectionOfMocksField... collections) {
		for (CollectionOfMocksField collectionOfMocksField : collections) {
			add(collectionOfMocksField);
		}
	}

	@SuppressWarnings("rawtypes")
	public CollectionOfMocksFieldSet(Collection<CollectionOfMocksField> collections) {
		for (CollectionOfMocksField collectionOfMocksField : collections) {
			add(collectionOfMocksField);
		}
	}

	/**
	 * Adds an {@link CollectionOfMocksField} to the {@link CollectionOfMocksFieldSet}. In the event that there is a
	 * matching {@link CollectionOfMocksField} an {@link IllegalArgumentException} will be thrown. Note that it is not
	 * possible to remove an {@link CollectionOfMocksField} after it has been added.
	 * 
	 * @param collectionOfMocksField
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void add(CollectionOfMocksField collectionOfMocksField) {
		if (hasMatchingInjectableCollection(collectionOfMocksField)) {
			throw new IllegalArgumentException("There is more than one CollectionOfMocks of type of collection "
					+ collectionOfMocksField.getTypeOfCollection() + " and type of element "
					+ collectionOfMocksField.getTypeOfElements());
		}
		this.collectionOfMocksFields.add(collectionOfMocksField);
	}

	/**
	 * @return The number of {@link CollectionOfMocksField InjectableCollections} held within this
	 *         {@link CollectionOfMocksFieldSet}.
	 */
	public int size() {
		return collectionOfMocksFields.size();
	}

	/**
	 * Returns an iterator allowing the caller to iterate over the {@link CollectionOfMocksField InjectableCollections}
	 * held within this {@link CollectionOfMocksFieldSet}.
	 * 
	 * @return An iterator
	 */
	public Iterator<CollectionOfMocksField<Collection<Object>, Object>> iterator() {
		return collectionOfMocksFields.iterator();
	}

	public Set<CollectionOfMocksField<Collection<Object>, Object>> asSet() {
		return new HashSet<CollectionOfMocksField<Collection<Object>, Object>>(collectionOfMocksFields);
	}

	@SuppressWarnings("rawtypes")
	private boolean hasMatchingInjectableCollection(CollectionOfMocksField collectionOfMocksField) {
		for (CollectionOfMocksField existing : collectionOfMocksFields) {
			if (collectionOfMocksField.matches(existing)) {
				return true;
			}
		}
		return false;
	}
}
