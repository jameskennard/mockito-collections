package uk.co.webamoeba.mockito.collections.inject;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import org.mockito.Mock;
import org.mockito.Spy;

import uk.co.webamoeba.mockito.collections.util.OrderedSet;

/**
 * Describes {@link Object}s that we want to inject into and be injected with {@link Collection}s of {@link Mock Mocks} and {@link Spy Spies}.
 * 
 * @author James Kennard
 */
public class InjectionDetails {

	private Set<Object> injectCollections;

	private OrderedSet<Object> mocks;

	private CollectionOfMocksFieldSet collectionOfMocksFieldSet;

	/**
	 * @param injectCollections
	 *            {@link Object}s into which we want to inject {@link Collection}s of mocks.
	 * @param mocksAndSpies
	 *            {@link Object}s that can be injected into {@link Collection}s in the injectCollections.
	 * @param collectionOfMocksFieldSet
	 *            {@link CollectionOfMocksFieldSet} containing {@link CollectionOfMocksField InjectableCollections} can
	 *            be injected into the injectCollections.
	 */
	public InjectionDetails(Set<Object> injectCollections, OrderedSet<Object> mocksAndSpies, CollectionOfMocksFieldSet collectionOfMocksFieldSet) {
		if (injectCollections == null) {
			throw new IllegalArgumentException("injectCollections must not be null");
		}
		if (mocksAndSpies == null) {
			throw new IllegalArgumentException("mocks must not be null");
		}

		if (collectionOfMocksFieldSet == null) {
			throw new IllegalArgumentException("collectionOfMocksFieldSet must not be null");
		}

		this.injectCollections = Collections.unmodifiableSet(injectCollections);
		this.mocks = new OrderedSet<Object>(mocksAndSpies);
		this.collectionOfMocksFieldSet = collectionOfMocksFieldSet;
	}

	/**
	 * @return The {@link Object}s into which we want to inject {@link Collection}s. If there are no mocks this method
	 *         will return an empty {@link Set}, this method will never return <code>null</code>. The returned
	 *         {@link Set} is unmodifiable.
	 */
	public Set<Object> getInjectCollections() {
		return injectCollections;
	}

	/**
	 * @return {@link Set} of {@link Object}s that can be injected into {@link Collection}s. If there are no mocks, this
	 *         method will return an empty {@link Set}, this method will never return <code>null</code>. The returned
	 *         {@link Set} is unmodifiable.
	 */
	public OrderedSet<Object> getMocksAndSpies() {
		return mocks;
	}

	public CollectionOfMocksFieldSet getInjectableCollectionSet() {
		return collectionOfMocksFieldSet;
	}
}
