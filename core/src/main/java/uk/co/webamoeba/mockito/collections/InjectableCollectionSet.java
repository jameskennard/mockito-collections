package uk.co.webamoeba.mockito.collections;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * A Set of {@link InjectableCollection InjectableCollections}.
 * 
 * @author James Kennard
 */
public class InjectableCollectionSet implements Iterable<InjectableCollection<Collection<Object>, Object>> {

    private Set<InjectableCollection<Collection<Object>, Object>> injectableCollections = new HashSet<InjectableCollection<Collection<Object>, Object>>();

    @SuppressWarnings("rawtypes")
    public InjectableCollectionSet(InjectableCollection... collections) {
	for (InjectableCollection injectableCollection : collections) {
	    add(injectableCollection);
	}
    }

    @SuppressWarnings("rawtypes")
    public InjectableCollectionSet(Collection<InjectableCollection> collections) {
	for (InjectableCollection injectableCollection : collections) {
	    add(injectableCollection);
	}
    }

    /**
     * Adds an {@link InjectableCollection} to the {@link InjectableCollectionSet}. In the event that there is a
     * matching {@link InjectableCollection} an {@link IllegalArgumentException} will be thrown. Note that it is not
     * possible to remove an {@link InjectableCollection} after it has been added.
     * 
     * @param injectableCollection
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void add(InjectableCollection injectableCollection) {
	if (hasMatchingInjectableCollection(injectableCollection)) {
	    throw new IllegalArgumentException("There is more than one InjectableCollection of type of collection "
		    + injectableCollection.getTypeOfCollection() + " and type of element "
		    + injectableCollection.getTypeOfElements());
	}
	this.injectableCollections.add(injectableCollection);
    }

    /**
     * @return The number of {@link InjectableCollection InjectableCollections} held within this
     *         {@link InjectableCollectionSet}.
     */
    public int size() {
	return injectableCollections.size();
    }

    /**
     * Returns an iterator allowing the caller to iterate over the {@link InjectableCollection InjectableCollections}
     * held within this {@link InjectableCollectionSet}.
     * 
     * @return An iterator
     */
    public Iterator<InjectableCollection<Collection<Object>, Object>> iterator() {
	return injectableCollections.iterator();
    }

    public Set<InjectableCollection<Collection<Object>, Object>> asSet() {
	return new HashSet<InjectableCollection<Collection<Object>, Object>>(injectableCollections);
    }

    @SuppressWarnings("rawtypes")
    private boolean hasMatchingInjectableCollection(InjectableCollection injectableCollection) {
	for (InjectableCollection existing : injectableCollections) {
	    if (injectableCollection.matches(existing)) {
		return true;
	    }
	}
	return false;
    }
}
