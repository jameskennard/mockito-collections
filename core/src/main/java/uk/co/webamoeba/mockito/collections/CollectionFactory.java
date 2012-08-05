package uk.co.webamoeba.mockito.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import uk.co.webamoeba.mockito.collections.exception.MockitoCollectionsException;

/**
 * Factory used to create {@link Collection Collections} of the specified type.
 * 
 * @author James Kennard
 */
public class CollectionFactory {

    /**
     * Creates a new {@link Collection} of the specified type and populate with the specified contents. If the contents
     * is <code>null</code> the new {@link Collection} will be empty.
     * 
     * @param collectionClass
     *            The type of {@link Collection} to create
     * @param contents
     *            The initial contents of the {@link Collection}, this is optional, <code>null</code> is an acceptable
     *            value
     * @return A new {@link Collection} of the specified type
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public <T extends Collection<Object>> T createCollection(Class<T> collectionClass, Collection<?> contents) {
	T collection;
	if (collectionClass.equals(SortedSet.class)) {
	    collection = (T) new TreeSet();
	} else if (collectionClass.equals(Set.class) || collectionClass.equals(Collection.class)) {
	    collection = (T) new HashSet(contents != null ? contents.size() : 0);
	} else if (collectionClass.equals(List.class)) {
	    collection = (T) new ArrayList(contents != null ? contents.size() : 0);
	} else {
	    try {
		collection = collectionClass.newInstance();
	    } catch (Exception e) {
		throw new MockitoCollectionsException("Could not create collection of type " + collectionClass, e);
	    }
	}
	if (contents != null) {
	    collection.addAll(contents);
	}
	return collection;
    }
}