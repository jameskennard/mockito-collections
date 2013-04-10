package uk.co.webamoeba.mockito.collections.inject;

import java.util.Collection;

import org.mockito.InjectMocks;

import uk.co.webamoeba.mockito.collections.annotation.CollectionOfMocks;

/**
 * An {@link CollectionOfMocksField} describes a field annotated with {@link CollectionOfMocks}. The {@link #value} of
 * the field is a {@link Collection} that can be injected verbatim into a a field in an object annotated with
 * {@link InjectMocks}.
 * 
 * @author James Kennard
 */
public class CollectionOfMocksField<C extends Collection<?>, E extends Object> {

	/**
	 * The {@link Collection} that is of the specified {@link #typeOfCollection type} and contains elements of the
	 * specified {@link #typeOfElements type}.
	 */
	private C value;

	// TODO may be able to simplify, not sure we need to hold typeOfCollection and typeOfElements separately.
	private Class<C> typeOfCollection;

	private Class<E> typeOfElements;

	public CollectionOfMocksField(C value, Class<C> typeOfCollection, Class<E> typeOfElements) {
		this.value = value;
		this.typeOfCollection = typeOfCollection;
		this.typeOfElements = typeOfElements;
	}

	public C getValue() {
		return value;
	}

	public Class<C> getTypeOfCollection() {
		return typeOfCollection;
	}

	public Class<E> getTypeOfElements() {
		return typeOfElements;
	}

	/**
	 * Determines if this {@link CollectionOfMocksField} matches the provided {@link CollectionOfMocksField}. Two
	 * {@link CollectionOfMocksField InjectableCollections} are considered to match if they have the same
	 * {@link #getTypeOfCollection() type of collection} and same {@link #getTypeOfElements() type of elements}. This is
	 * not an {@link #equals(Object)} method, it does not check for equality of the contained {@link #getValue() value}.
	 * 
	 * @param collectionOfMocksField
	 * @return <code>true</code> if this {@link CollectionOfMocksField} matches the provided
	 *         {@link CollectionOfMocksField}.
	 */
	@SuppressWarnings("rawtypes")
	public boolean matches(CollectionOfMocksField collectionOfMocksField) {
		if (this == collectionOfMocksField) {
			return true;
		}
		return getTypeOfCollection().equals(collectionOfMocksField.getTypeOfCollection())
				&& getTypeOfElements().equals(collectionOfMocksField.getTypeOfElements());
	}
}
