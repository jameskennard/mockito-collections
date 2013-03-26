package uk.co.webamoeba.mockito.collections.inject;

import java.util.Collection;
import java.util.Set;

import org.mockito.Mock;

import uk.co.webamoeba.mockito.collections.util.OrderedSet;

/**
 * Strategy that is intended to select {@link Mock} {@link Object Objects} that can be placed in a {@link Collection}
 * where the Generics on that Collection are described as a particular type.
 * 
 * @author James Kennard
 */
public interface MockSelectionStrategy {

	/**
	 * Selects the {@link Mock Mocks} from the provided {@link Set} of {@link Mock Mocks} that can be injected into a
	 * {@link Collection} where the generic type is of the specified type. In the case that there are no suitable
	 * {@link Mock Mocks}, this method will return an empty {@link Set}. <b>This method must never return
	 * <code>null</code></b>.
	 * 
	 * @param mocks
	 * @param mockClass
	 * @return
	 */
	public <T> OrderedSet<T> selectMocks(OrderedSet<Object> mocks, Class<T> mockClass);

	/**
	 * Gets the {@link CollectionOfMocksField} from the provided {@link CollectionOfMocksFieldSet} that matches the
	 * specified typeOfCollection and typeOfElements.
	 * 
	 * @param collectionOfMocksFieldSet
	 *            The {@link CollectionOfMocksField InjectableCollections} in which to look for a matching
	 *            {@link CollectionOfMocksField}.
	 * @param typeOfCollection
	 *            The type of {@link Collection} that we are looking for.
	 * @param typeOfElements
	 *            The type of elements within the {@link Collection} that we are looking for.
	 * @return
	 */
	public <C extends Collection<E>, E extends Object> CollectionOfMocksField<C, E> getCollectionOfMocksField(
			CollectionOfMocksFieldSet collectionOfMocksFieldSet, Class<C> typeOfCollection, Class<E> typeOfElements);
}
