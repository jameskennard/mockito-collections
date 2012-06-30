package uk.co.webamoeba.mockito.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CollectionFactory {

	public <T extends Collection<?>> T createCollection(Class<T> collectionClass) {
		T collection;
		if (collectionClass.equals(Set.class) || collectionClass.equals(Collection.class)) {
			collection = (T)new HashSet();
		} else if (collectionClass.equals(List.class)) {
			collection = (T)new ArrayList();
		} else {
			try {
				collection = (T)collectionClass.newInstance();
			} catch (Exception e) {
				// FIXME throw a better type of exception
				throw new RuntimeException("Could not create collection of type " + collectionClass, e);
			}
		}
		return collection;
	}

}
