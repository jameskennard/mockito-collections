package uk.co.webamoeba.mockito.collections.inject;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import uk.co.webamoeba.mockito.collections.util.HashOrderedSet;
import uk.co.webamoeba.mockito.collections.util.OrderedSet;

/**
 * Describes {@link Object}s that we want to inject into and be injected with {@link Collection}s of {@link Object}s.
 * <p>
 * An <i>injectCollections</i> is an {@link Object} into which we want to inject {@link Collection}s of injectables.<br/>
 * An <i>injectable</i> is an {@link Object} that can be injected into {@link Collection}s in injectCollections.<br />
 * An <i>injectableCollection</i> is a {@link Collection} that can be injected verbatim into {@link Collection}s in
 * injectCollections.<br />
 * </p>
 * 
 * @author James Kennard
 */
public class InjectionDetails {

	private Set<Object> injectCollections;

	private OrderedSet<Object> injectables;

	private InjectableCollectionSet injectableCollectionSet;

	/**
	 * @param injectCollections
	 *            {@link Object}s into which we want to inject {@link Collection}s of injectables.
	 * @param injectables
	 *            {@link Object}s that can be injected into {@link Collection}s in the injectCollections.
	 * @param injectableCollectionSet
	 *            {@link InjectableCollectionSet} containing {@link InjectableCollection InjectableCollections} can be
	 *            injected into the injectCollections.
	 */
	public InjectionDetails(Set<Object> injectCollections, OrderedSet<Object> injectables,
			InjectableCollectionSet injectableCollectionSet) {
		if (injectCollections == null) {
			throw new IllegalArgumentException("injectCollections must not be null");
		}
		if (injectables == null) {
			throw new IllegalArgumentException("injectables must not be null");
		}
		if (injectableCollectionSet == null) {
			throw new IllegalArgumentException("injectableCollectionSet must not be null");
		}

		this.injectCollections = Collections.unmodifiableSet(injectCollections);
		this.injectables = new HashOrderedSet<Object>(injectables);
		this.injectableCollectionSet = injectableCollectionSet;
	}

	/**
	 * @return The {@link Object}s into which we want to inject {@link Collection}s. If there are no injectables this
	 *         method will return an empty {@link Set}, this method will never return <code>null</code>. The returned
	 *         {@link Set} is unmodifiable.
	 */
	public Set<Object> getInjectCollections() {
		return injectCollections;
	}

	/**
	 * @return {@link Set} of {@link Object}s that can be injected into {@link Collection}s. If there are no
	 *         injectables, this method will return an empty {@link Set}, this method will never return
	 *         <code>null</code>. The returned {@link Set} is unmodifiable.
	 */
	public OrderedSet<Object> getInjectables() {
		return injectables;
	}

	public InjectableCollectionSet getInjectableCollectionSet() {
		return injectableCollectionSet;
	}
}
