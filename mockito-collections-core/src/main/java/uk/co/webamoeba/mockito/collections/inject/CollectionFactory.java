package uk.co.webamoeba.mockito.collections.inject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.SortedSet;
import java.util.concurrent.ConcurrentLinkedQueue;

import uk.co.webamoeba.mockito.collections.exception.MockitoCollectionsException;
import uk.co.webamoeba.mockito.collections.util.HashOrderedSet;
import uk.co.webamoeba.mockito.collections.util.HashOrderedSetBackedSortedSet;

/**
 * Factory used to create {@link Collection Collections} of the specified type.
 * 
 * @author James Kennard
 */
public class CollectionFactory {

	/**
	 * Creates a new {@link Collection} of the specified type and populates with the specified contents. If the contents
	 * is <code>null</code> the new {@link Collection} will be empty.
	 * 
	 * @param collectionClass
	 *            The type of {@link Collection} to create
	 * @param contents
	 *            The initial contents of the {@link Collection}, this is optional, <code>null</code> is an acceptable
	 *            value
	 * @return A new {@link Collection} of the specified type
	 */
	// FIXME returned Collection should guarantee order in which elements are inserted is preserved on retrieval
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T extends Collection<Object>> T createCollection(Class<T> collectionClass, Collection<?> contents) {
		T collection;
		if (collectionClass.equals(SortedSet.class)) {
			collection = (T) new HashOrderedSetBackedSortedSet();
		} else if (collectionClass.equals(Set.class) || collectionClass.equals(Collection.class)) {
			collection = (T) new HashOrderedSet(contents != null ? contents.size() : 0);
		} else if (collectionClass.equals(List.class)) {
			collection = (T) new ArrayList(contents != null ? contents.size() : 0);
		} else if (collectionClass.equals(Queue.class)) {
			collection = (T) new ConcurrentLinkedQueue();
		} else {
			try {
				collection = collectionClass.newInstance();
			} catch (Exception e) {
				throw new MockitoCollectionsException("Could not create collection of type " + collectionClass, e);
			}
			if (Set.class.isAssignableFrom(collectionClass)) {
				System.out
						.println("[WARN] Mockito-Collections cannot gaurantee order of elements in injected collection of type "
								+ collectionClass);
			}
		}
		if (contents != null) {
			collection.addAll(contents);
		}
		return collection;
	}
}