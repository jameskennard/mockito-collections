package uk.co.webamoeba.mockito.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import uk.co.webamoeba.mockito.collections.exception.MockitoCollectionsException;

public class CollectionFactory {

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public <T extends Collection<?>> T createCollection(Class<T> collectionClass) {
	T collection;
	if (collectionClass.equals(SortedSet.class)) {
	    collection = (T) new TreeSet();
	} else if (collectionClass.equals(Set.class) || collectionClass.equals(Collection.class)) {
	    collection = (T) new HashSet();
	} else if (collectionClass.equals(List.class)) {
	    collection = (T) new ArrayList();
	} else {
	    try {
		collection = collectionClass.newInstance();
	    } catch (Exception e) {
		throw new MockitoCollectionsException("Could not create collection of type " + collectionClass, e);
	    }
	}
	return collection;
    }

}
