package uk.co.webamoeba.mockito.collections.inject;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.SortedSet;

import org.junit.Test;

import uk.co.webamoeba.mockito.collections.exception.MockitoCollectionsException;

/**
 * @author James Kennard
 */
@SuppressWarnings("unchecked")
public class CollectionFactoryTest {

	private CollectionFactory factory = new CollectionFactory();

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
		shouldCreateCollection(SortedSet.class, Arrays.<Object> asList("ABC", "DEF", "GHI"));
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
	public void shouldCreateCollectionGivenSpecificType() {
		shouldCreateCollection(LinkedList.class);
	}

	@Test
	public void shouldCreateCollectionGivenSpecificTypeAndContents() {
		shouldCreateCollectionGivenContents(LinkedList.class);
	}

	@Test(expected = MockitoCollectionsException.class)
	public void shouldFailToCreateCollectionGivenCannotInstantiateSpecificType() {
		shouldCreateCollection(Queue.class);
	}

	@Test(expected = MockitoCollectionsException.class)
	public void shouldFailToCreateCollectionGivenCannotInstantiateSpecificTypeAndContents() {
		shouldCreateCollectionGivenContents(Queue.class);
	}

	private <T extends Collection<Object>> void shouldCreateCollection(Class<T> clazz) {
		shouldCreateCollection(clazz, null);
	}

	private <T extends Collection<Object>> void shouldCreateCollectionGivenContents(Class<T> clazz) {
		Collection<Object> contents = Arrays.<Object> asList("Some Contents", 'A', 12L, 3.0f);
		shouldCreateCollection(clazz, contents);
	}

	private <T extends Collection<Object>> void shouldCreateCollection(Class<T> clazz, Collection<Object> contents) {
		// Given
		Class<T> collectionClass = clazz;

		// When
		Collection<?> collection = factory.createCollection(collectionClass, contents);

		// Then
		assertTrue(collectionClass.isAssignableFrom(collection.getClass()));
		if (contents != null) {
			assertTrue(collection.containsAll(contents));
		}
	}
}
