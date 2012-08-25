package uk.co.webamoeba.mockito.collections;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

/**
 * Describes {@link Object}s that we want to inject into and be injected with {@link Collection}s of {@link Object}s.
 * <p>
 * An <i>injectee</i> is an {@link Object} into which we want to inject {@link Collection}s of injectables.<br/>
 * An <i>injectable</i> is an {@link Object} that can be injected into {@link Collection}s in injectees.<br />
 * An <i>injectableCollection</i> is a {@link Collection} that can be injected verbatim into {@link Collection}s in
 * injectees.<br />
 * </p>
 * 
 * @author James Kennard
 */
public class InjectionDetails {

    private Set<Object> injectees;

    private Set<Object> injectables;

    private InjectableCollectionSet injectableCollectionSet;

    /**
     * @param injectees
     *            {@link Object}s into which we want to inject {@link Collection}s of injectables.
     * @param injectables
     *            {@link Object}s that can be injected into {@link Collection}s in the injectees.
     * @param injectableCollectionSet
     *            {@link InjectableCollectionSet} containing {@link InjectableCollection InjectableCollections} can be
     *            injected into the injectees.
     */
    public InjectionDetails(Set<Object> injectees, Set<Object> injectables,
	    InjectableCollectionSet injectableCollectionSet) {
	if (injectees == null) {
	    throw new IllegalArgumentException("Injectees must not be null");
	}
	if (injectables == null) {
	    throw new IllegalArgumentException("Injectables must not be null");
	}
	if (injectableCollectionSet == null) {
	    throw new IllegalArgumentException("injectableCollectionSet must not be null");
	}

	this.injectees = Collections.unmodifiableSet(injectees);
	this.injectables = Collections.unmodifiableSet(injectables);
	this.injectableCollectionSet = injectableCollectionSet;
    }

    /**
     * @return The {@link Object}s into which we want to inject {@link Collection}s. If there are no injectables this
     *         method will return an empty {@link Set}, this method will never return <code>null</code>. The returned
     *         {@link Set} is unmodifiable.
     */
    public Set<Object> getInjectees() {
	return injectees;
    }

    /**
     * @return {@link Set} of {@link Object}s that can be injected into {@link Collection}s. If there are no
     *         injectables, this method will return an empty {@link Set}, this method will never return
     *         <code>null</code>. The returned {@link Set} is unmodifiable.
     */
    public Set<Object> getInjectables() {
	return injectables;
    }

    public InjectableCollectionSet getInjectableCollectionSet() {
	return injectableCollectionSet;
    }
}
