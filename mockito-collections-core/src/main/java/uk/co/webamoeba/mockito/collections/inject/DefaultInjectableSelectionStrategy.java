package uk.co.webamoeba.mockito.collections.inject;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * The default implementation of {@link InjectableSelectionStrategy}.
 * 
 * @author James Kennard
 */
public class DefaultInjectableSelectionStrategy implements InjectableSelectionStrategy {

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public <T> Set<T> getInjectables(Set<Object> injectables, Class<T> injectableClass) {
		Set<T> matchingInjectables = new HashSet<T>();
		for (Object object : injectables) {
			if (injectableClass.isAssignableFrom(object.getClass())) {
				matchingInjectables.add((T) object);
			}
		}
		return matchingInjectables;
	}

	/**
	 * {@inheritDoc}
	 */
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
