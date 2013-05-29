package uk.co.webamoeba.mockito.collections.inject;

import java.util.Collection;

import uk.co.webamoeba.mockito.collections.util.OrderedSet;

/**
 * The default implementation of {@link MockSelectionStrategy}.
 * 
 * @author James Kennard
 */
public class DefaultMockSelectionStrategy implements MockSelectionStrategy {

	@SuppressWarnings("unchecked")
	public <T> OrderedSet<T> selectMocks(OrderedSet<Object> mocks, Class<T> mockClass) {
		OrderedSet<T> matchingMocks = new OrderedSet<T>();
		for (Object object : mocks) {
			if (mockClass.isAssignableFrom(object.getClass())) {
				matchingMocks.add((T) object);
			}
		}
		return matchingMocks;
	}

	@SuppressWarnings("unchecked")
	public <C extends Collection<E>, E> CollectionOfMocksField<C, E> getCollectionOfMocksField(
			CollectionOfMocksFieldSet collectionOfMocksFieldSet, Class<C> typeOfCollection, Class<E> typeOfElements) {
		if (collectionOfMocksFieldSet == null) {
			throw new IllegalArgumentException("collectionOfMocksFieldSet must not be null");
		}
		if (typeOfCollection == null) {
			throw new IllegalArgumentException("typeOfCollection must not be null");
		}
		if (typeOfElements == null) {
			throw new IllegalArgumentException("typeOfElements must not be null");
		}

		CollectionOfMocksField<C, E> collectionOfMocksField = null;
		for (CollectionOfMocksField<?, ?> candidate : collectionOfMocksFieldSet) {
			if (candidate.getTypeOfCollection().equals(typeOfCollection)
					&& candidate.getTypeOfElements().equals(typeOfElements)) {
				if (collectionOfMocksField != null) {
					throw new IllegalArgumentException(
							"There is more than one CollectionOfMocksField of type of collection " + typeOfCollection
									+ " and type of element " + typeOfElements
									+ ". There may be other offending duplicates.");
				}
				collectionOfMocksField = (CollectionOfMocksField<C, E>) candidate;
			}
		}
		return collectionOfMocksField;
	}
}
