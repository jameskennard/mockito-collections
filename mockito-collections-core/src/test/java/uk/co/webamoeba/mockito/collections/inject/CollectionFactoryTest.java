package uk.co.webamoeba.mockito.collections.inject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.SortedSet;

import org.junit.Test;
import org.mockito.internal.util.MockUtil;
import org.mockito.mock.MockCreationSettings;

import uk.co.webamoeba.mockito.collections.util.OrderedSet;

/**
 * @author James Kennard
 */
@SuppressWarnings("unchecked")
public class CollectionFactoryTest {

	private CollectionFactory factory = new CollectionFactory();

	private MockUtil mockUtil = new MockUtil();

	@Test
	public void shouldCreateCollectionGivenCollection() {
		shouldCreateCollection(Collection.class);
	}

	@Test
	public void shouldCreateCollectionGivenCollectionAndContents() {
		shouldCreateCollectionGivenContents(Collection.class);
	}

	@Test
	public void shouldCreateCollectionGivenSortedSet() {
		shouldCreateCollection(SortedSet.class);
	}

	@Test
	public void shouldCreateCollectionGivenSortedSetAndContents() {
		shouldCreateCollection(SortedSet.class, new OrderedSet<Object>(Arrays.<Object> asList("ABC", "DEF", "GHI")));
	}

	@Test
	public void shouldCreateCollectionGivenSet() {
		shouldCreateCollection(Set.class);
	}

	@Test
	public void shouldCreateCollectionGivenSetAndContents() {
		shouldCreateCollectionGivenContents(Set.class);
	}

	@Test
	public void shouldCreateCollectionGivenList() {
		shouldCreateCollection(List.class);
	}

	@Test
	public void shouldCreateCollectionGivenListAndContents() {
		shouldCreateCollectionGivenContents(List.class);
	}

	@Test
	public void shouldCreateCollectionGivenQueue() {
		shouldCreateCollection(Queue.class);
	}

	@Test
	public void shouldCreateCollectionGivenQueueAndContents() {
		shouldCreateCollectionGivenContents(Queue.class);
	}

	@Test
	public void shouldCreateCollectionGivenSubInterfaceOfSet() {
		Collection<Object> collection = shouldCreateCollectionGivenContents(ExtendedSetInterface.class);
		assertIsMockWithSpiedIntanceOf(collection, OrderedSet.class);
	}

	@Test
	public void shouldCreateCollectionGivenSubInterfaceOfList() {
		Collection<Object> collection = shouldCreateCollectionGivenContents(ExtendedListInterface.class);
		assertIsMockWithSpiedIntanceOf(collection, List.class);
	}

	@Test
	public void shouldCreateCollectionGivenSubInterfaceOfExtendedSortedeSet() {
		Collection<Object> collection = shouldCreateCollectionGivenContents(ExtendedSortedeSet.class);
		assertIsMockWithSpiedIntanceOf(collection, SortedSet.class);
	}

	@Test
	public void shouldCreateCollectionGivenSubInterfaceOfExtendedQueue() {
		Collection<Object> collection = shouldCreateCollectionGivenContents(ExtendedQueue.class);
		assertIsMockWithSpiedIntanceOf(collection, Queue.class);
	}

	@Test
	public void shouldCreateCollectionGivenUnusualSubInterfaceOfCollection() {
		Collection<Object> collection = shouldCreateCollectionGivenContents(ExtendedCollectionInterface.class);
		assertIsMockWithSpiedIntanceOf(collection, OrderedSet.class);
	}

	@Test
	@SuppressWarnings("rawtypes")
	public void shouldFailToCreateCollectionGivenAbstractImplementationOfCollection() {
		// Given
		Class<AbstractCollection> collectionClass = AbstractCollection.class;
		OrderedSet<Object> contents = null;

		// When
		Exception exception = createCollectionAndCatchException(collectionClass, contents);

		// Then
		assertTrue(exception.getMessage().contains("abstract"));
	}

	@Test
	@SuppressWarnings("rawtypes")
	public void shouldFailToCreateCollectionGivenCannotInstantiateSpecificType() {
		// Given
		Class<ArrayListWithNoDefaultOrInitialCapacityConstructor> collectionClass = ArrayListWithNoDefaultOrInitialCapacityConstructor.class;
		OrderedSet<Object> contents = null;

		// When
		Exception exception = createCollectionAndCatchException(collectionClass, contents);

		// Then
		assertTrue(exception.getMessage().contains("Could not create collection"));
		assertTrue(exception.getMessage().contains("do not know how to instantiate"));
	}

	private <T extends Collection<Object>> Collection<Object> shouldCreateCollection(Class<T> clazz) {
		return shouldCreateCollection(clazz, null);
	}

	private <T extends Collection<Object>> Collection<Object> shouldCreateCollectionGivenContents(Class<T> clazz) {
		OrderedSet<Object> contents = new OrderedSet<Object>(Arrays.<Object> asList("Some Contents", 'A', 12L, 3.0f));
		return shouldCreateCollection(clazz, contents);
	}

	private <T extends Collection<Object>> Collection<Object> shouldCreateCollection(Class<T> clazz,
			OrderedSet<Object> contents) {
		// Given
		Class<T> collectionClass = clazz;

		// When
		Collection<Object> collection = factory.createCollection(collectionClass, contents);

		// Then
		assertTrue(collectionClass.isAssignableFrom(collection.getClass()));
		if (contents != null) {
			assertEquals(contents.size(), collection.size());
			Iterator<Object> collectionIterator = collection.iterator();
			Iterator<Object> contentsIterator = contents.iterator();
			while (collectionIterator.hasNext()) {
				assertEquals(contentsIterator.next(), collectionIterator.next());
			}
		} else {
			assertTrue(collection.isEmpty());
		}
		return collection;
	}

	private <T extends Collection<Object>> Exception createCollectionAndCatchException(Class<T> collectionClass,
			OrderedSet<Object> contents) {
		try {
			factory.createCollection(collectionClass, contents);
		} catch (Exception e) {
			return e;
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	private void assertIsMockWithSpiedIntanceOf(Collection<?> collection, Class<? extends Collection> spiedInstanceClass) {
		MockCreationSettings mockSettings = mockUtil.getMockHandler(collection).getMockSettings();
		assertTrue(spiedInstanceClass.isInstance(mockSettings.getSpiedInstance()));
	}

	public interface ExtendedSetInterface<T> extends Set<T> {
	}

	public interface ExtendedListInterface<T> extends List<T> {
	}

	public interface ExtendedCollectionInterface<T> extends Collection<T> {
	}

	public static class ArrayListWithNoDefaultOrInitialCapacityConstructor<E extends Object> extends ArrayList<E> {

		private static final long serialVersionUID = 1L;

		public ArrayListWithNoDefaultOrInitialCapacityConstructor(String someMarvellouseConstructor) {
		}
	}

	public static interface ExtendedSortedeSet<E extends Object> extends SortedSet<E> {
	}

	public static interface ExtendedQueue<E extends Object> extends Queue<E> {
	}
}
