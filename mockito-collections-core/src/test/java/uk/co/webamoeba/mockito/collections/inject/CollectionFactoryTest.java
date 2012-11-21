package uk.co.webamoeba.mockito.collections.inject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NavigableSet;
import java.util.Queue;
import java.util.Set;
import java.util.SortedSet;
import java.util.concurrent.ArrayBlockingQueue;

import org.junit.Test;
import org.mockito.internal.creation.MockSettingsImpl;
import org.mockito.internal.util.MockUtil;

import uk.co.webamoeba.mockito.collections.exception.MockitoCollectionsException;
import uk.co.webamoeba.mockito.collections.util.HashOrderedSet;
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
		// TODO, is this okay?
		shouldCreateCollection(SortedSet.class, new HashOrderedSet<Object>(Arrays.<Object> asList("ABC", "DEF", "GHI")));
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
	public void shouldCreateCollectionGivenSpecificTypeWithDefaultConstructor() {
		shouldCreateCollection(LinkedList.class);
	}

	@Test
	public void shouldCreateCollectionGivenSpecificTypeAndContentsAndTypeHasDefaultConstructor() {
		shouldCreateCollectionGivenContents(LinkedList.class);
	}

	@Test
	public void shouldCreateCollectionGivenSpecificTypeWithInitialCapcityConstructor() {
		shouldCreateCollection(InitialCapacityArrayList.class);
	}

	@Test
	public void shouldCreateCollectionGivenSubInterfaceOfSet() {
		Collection<Object> collection = shouldCreateCollectionGivenContents(OrderedSet.class);
		assertIsMockWithSpiedIntanceOf(collection, OrderedSet.class);
	}

	@Test
	public void shouldCreateCollectionGivenSubInterfaceOfSortedSet() {
		Collection<Object> collection = shouldCreateCollectionGivenContents(NavigableSet.class);
		assertIsMockWithSpiedIntanceOf(collection, SortedSet.class);
	}

	@Test
	public void shouldCreateCollectionGivenSubInterfaceOfList() {
		Collection<Object> collection = shouldCreateCollectionGivenContents(ExtendedListInterface.class);
		assertIsMockWithSpiedIntanceOf(collection, List.class);
	}

	@Test
	public void shouldCreateCollectionGivenSubInterfaceOfQueue() {
		Collection<Object> collection = shouldCreateCollectionGivenContents(Deque.class);
		assertIsMockWithSpiedIntanceOf(collection, Queue.class);
	}

	@Test
	public void shouldCreateCollectionGivenUnusualSubInterfaceOfCollection() {
		Collection<Object> collection = shouldCreateCollectionGivenContents(ExtendedCollectionInterface.class);
		assertIsMockWithSpiedIntanceOf(collection, OrderedSet.class);
	}

	@Test
	public void shouldCreateCollectionGivenHasInitialCapacityConstructor() {
		shouldCreateCollectionGivenContents(ArrayBlockingQueue.class);
	}

	@Test(expected = MockitoCollectionsException.class)
	public void shouldCreateCollectionGivenAbstractImplementationOfCollection() {
		shouldCreateCollectionGivenContents(AbstractCollection.class);
	}

	@Test(expected = MockitoCollectionsException.class)
	public void shouldFailToCreateCollectionGivenCannotInstantiateSpecificType() {
		shouldCreateCollection(ArrayListWithNoDefaultOrInitialCapacityConstructor.class);
	}

	@Test(expected = MockitoCollectionsException.class)
	public void shouldFailToCreateCollectionGivenCannotInstantiateSpecificTypeAndContents() {
		shouldCreateCollectionGivenContents(ArrayListWithNoDefaultOrInitialCapacityConstructor.class);
	}

	@Test(expected = MockitoCollectionsException.class)
	public void shouldFailToCreateCollectionGivenNonStaticNestedClassOrInnerClass() {
		shouldCreateCollectionGivenContents(InnerArrayList.class);
	}

	private <T extends Collection<Object>> Collection<Object> shouldCreateCollection(Class<T> clazz) {
		return shouldCreateCollection(clazz, null);
	}

	private <T extends Collection<Object>> Collection<Object> shouldCreateCollectionGivenContents(Class<T> clazz) {
		OrderedSet<Object> contents = new HashOrderedSet<Object>(
				Arrays.<Object> asList("Some Contents", 'A', 12L, 3.0f));
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

	@SuppressWarnings("rawtypes")
	private void assertIsMockWithSpiedIntanceOf(Collection<?> collection, Class<? extends Collection> spiedInstanceClass) {
		MockSettingsImpl mockSettings = mockUtil.getMockHandler(collection).getMockSettings();
		assertTrue(spiedInstanceClass.isInstance(mockSettings.getSpiedInstance()));
	}

	public interface ExtendedListInterface<T> extends List<T> {
	}

	public interface ExtendedCollectionInterface<T> extends Collection<T> {
	}

	public static class InitialCapacityArrayList<E extends Object> extends ArrayList<E> {

		private static final long serialVersionUID = 1L;

		public InitialCapacityArrayList(int initialCapacity) {
			super(initialCapacity);
		}
	}

	public static class ArrayListWithNoDefaultOrInitialCapacityConstructor<E extends Object> extends ArrayList<E> {

		private static final long serialVersionUID = 1L;

		public ArrayListWithNoDefaultOrInitialCapacityConstructor(String someMarvellouseConstructor) {
		}
	}

	public class InnerArrayList<E extends Object> extends ArrayList<E> {

		private static final long serialVersionUID = 1L;

		public InnerArrayList() {
		}
	}

	// TODO add test for non static inner class
}
