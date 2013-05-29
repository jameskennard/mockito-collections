package uk.co.webamoeba.mockito.collections.inject;

import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.withSettings;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.SortedSet;
import java.util.concurrent.ConcurrentLinkedQueue;

import uk.co.webamoeba.mockito.collections.exception.MockitoCollectionsException;
import uk.co.webamoeba.mockito.collections.util.HashOrderedSetBackedSortedSet;
import uk.co.webamoeba.mockito.collections.util.OrderedSet;

/**
 * Factory used to create {@link Collection Collections} of the specified type.
 * 
 * @author James Kennard
 */
public class CollectionFactory {

	/**
	 * Creates a new {@link Collection} of the specified type and populates with the specified contents. If the contents
	 * is <code>null</code> the new {@link Collection} will be empty. The returned {@link Collection} will retain the
	 * order of the elements where ever possible. The order of the elements is guaranteed for all interfaces, this
	 * includes interfaces that would not normally guarantee order in their own right, for example {@link Set}.
	 * 
	 * @param collectionClass
	 *            The type of {@link Collection} to create
	 * @param contents
	 *            The initial contents of the {@link Collection}, this is optional, <code>null</code> is an acceptable
	 *            value
	 * @return A new {@link Collection} of the specified type
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T extends Collection<Object>> T createCollection(Class<T> collectionClass, OrderedSet<?> contents) {
		final T collection;
		if (collectionClass.equals(SortedSet.class)) {
			collection = (T) new HashOrderedSetBackedSortedSet();
		} else if (collectionClass.equals(Set.class) || collectionClass.equals(Collection.class)) {
			collection = (T) new OrderedSet(getInitialCapacity(contents));
		} else if (collectionClass.equals(List.class)) {
			collection = (T) new ArrayList(getInitialCapacity(contents));
		} else if (collectionClass.equals(Queue.class)) {
			collection = (T) new ConcurrentLinkedQueue();
		} else if (collectionClass.isInterface()) {
			collection = createCollectionFromUnknownInterface(collectionClass, contents);
		} else if (Modifier.isAbstract(collectionClass.getModifiers())) {
			throw new MockitoCollectionsException("Could not create collection of type " + collectionClass
					+ ", the type is abstract");
		} else {
			collection = createCollectionUsingReflection(collectionClass, contents);
		}
		if (contents != null && collection != null) {
			collection.addAll(contents);
		}
		return collection;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private <T> T createCollectionFromUnknownInterface(Class<T> collectionClass, Collection<?> contents) {
		final Collection<Object> spiedCollection;
		if (SortedSet.class.isAssignableFrom(collectionClass)) {
			spiedCollection = new HashOrderedSetBackedSortedSet();
		} else if (List.class.isAssignableFrom(collectionClass)) {
			spiedCollection = new ArrayList(getInitialCapacity(contents));
		} else if (Queue.class.isAssignableFrom(collectionClass)) {
			spiedCollection = new ConcurrentLinkedQueue();
		} else {
			spiedCollection = new OrderedSet(getInitialCapacity(contents));
		}
		return (T) mock(
				spiedCollection.getClass(),
				withSettings().spiedInstance(spiedCollection).defaultAnswer(CALLS_REAL_METHODS)
						.extraInterfaces(collectionClass));
	}

	private <T extends Collection<Object>> T createCollectionUsingReflection(Class<T> collectionClass,
			Collection<?> contents) {
		final T collection;
		try {
			Constructor<T> constructor = getDefaultConstructor(collectionClass);
			if (constructor != null) {
				collection = constructor.newInstance();
			} else {
				constructor = getInitialCapacityConstructor(collectionClass);
				if (constructor != null) {
					collection = constructor.newInstance(getInitialCapacity(contents));
				} else {
					throw new MockitoCollectionsException(
							"Could not create collection of type "
									+ collectionClass
									+ " could not find a default constructor"
									+ " or a constructor with an integer initial capacity"
									+ " (this can happen if there are available constructors but the class is a non-static nested class/inner class"
									+ " or if the available constructors are not visible)");
				}
			}
		} catch (InvocationTargetException e) {
			// Occurs if the invoked constructor throws an exception
			throw new MockitoCollectionsException("Could not create collection of type " + collectionClass + "\n"
					+ e.getMessage(), e);
		} catch (InstantiationException e) {
			// Should never happen (we have protection against abstract classes)
			throw new MockitoCollectionsException("Could not create collection of type " + collectionClass + "\n"
					+ e.getMessage(), e);
		} catch (IllegalAccessException e) {
			throw new MockitoCollectionsException("Could not create collection of type " + collectionClass + "\n"
					+ e.getMessage(), e);
		}
		if (Set.class.isAssignableFrom(collectionClass)) {
			System.out
					.println("[WARN] Mockito-Collections cannot gaurantee order of elements in injected collection of type "
							+ collectionClass);
		}
		return collection;
	}

	private <T extends Collection<Object>> Constructor<T> getDefaultConstructor(Class<T> collectionClass) {
		Constructor<T> constructor = null;
		try {
			constructor = collectionClass.getDeclaredConstructor();
		} catch (SecurityException e) {
			System.out
					.println("[WARN] Mockito-Collections SecurityManager prevented access to default constructor for "
							+ collectionClass);
		} catch (NoSuchMethodException e) {
			System.out.println("[WARN] Mockito-Collections there is no default constructor for " + collectionClass);
		}
		return constructor;
	}

	private <T extends Collection<Object>> Constructor<T> getInitialCapacityConstructor(Class<T> collectionClass) {
		Constructor<T> constructor = null;
		try {
			constructor = collectionClass.getDeclaredConstructor(Integer.TYPE);
		} catch (SecurityException e) {
			System.out
					.println("[WARN] Mockito-Collections SecurityManager prevented access to default constructor for "
							+ collectionClass);
		} catch (NoSuchMethodException e) {
			System.out.println("[WARN] Mockito-Collections there is no initial capacity constructor for "
					+ collectionClass);
		}
		return constructor;
	}

	/**
	 * Gets the initialCapacity for new {@link Collection Collections} created with the contents from the provided
	 * {@link Collection}. It is anticipated that the {@link Collection} is unlikely to change in size, and thus it
	 * makes sense to use the starting number of elements as the initial capacity.
	 * 
	 * @param contents
	 * @return The initialCapacity for new {@link Collection Collections} created with the contents from the provided
	 *         {@link Collection}
	 */
	private int getInitialCapacity(Collection<?> contents) {
		return contents != null ? contents.size() : 0;
	}
}