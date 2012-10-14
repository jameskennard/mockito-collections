package uk.co.webamoeba.mockito.collections.inject;

import java.util.Collection;

import uk.co.webamoeba.mockito.collections.util.HashOrderedSet;
import uk.co.webamoeba.mockito.collections.util.OrderedSet;

/**
 * The default implementation of {@link InjectableSelectionStrategy}.
 * 
 * @author James Kennard
 */
public class DefaultInjectableSelectionStrategy implements InjectableSelectionStrategy {

	@SuppressWarnings("unchecked")
	public <T> OrderedSet<T> getInjectables(OrderedSet<Object> injectables, Class<T> injectableClass) {
		HashOrderedSet<T> matchingInjectables = new HashOrderedSet<T>();
		for (Object object : injectables) {
			if (injectableClass.isAssignableFrom(object.getClass())) {
				matchingInjectables.add((T) object);
			}
		}
		return matchingInjectables;
	}

	@SuppressWarnings("unchecked")
	public <C extends Collection<E>, E> InjectableCollection<C, E> getInjectableCollection(
			InjectableCollectionSet injectableCollectionSet, Class<C> typeOfCollection, Class<E> typeOfElements) {
		if (injectableCollectionSet == null) {
			throw new IllegalArgumentException("injectableCollections must not be null");
		}
		if (typeOfCollection == null) {
			throw new IllegalArgumentException("typeOfCollection must not be null");
		}
		if (typeOfElements == null) {
			throw new IllegalArgumentException("typeOfElements must not be null");
		}

		InjectableCollection<C, E> injectableCollection = null;
		for (InjectableCollection<?, ?> candidate : injectableCollectionSet) {
			if (candidate.getTypeOfCollection().equals(typeOfCollection)
					&& candidate.getTypeOfElements().equals(typeOfElements)) {
				if (injectableCollection != null) {
					throw new IllegalArgumentException(
							"There is more than one InjectableCollection of type of collection " + typeOfCollection
									+ " and type of element " + typeOfElements
									+ ". There may be other offending duplicates.");
				}
				injectableCollection = (InjectableCollection<C, E>) candidate;
			}
		}
		return injectableCollection;
	}
}
