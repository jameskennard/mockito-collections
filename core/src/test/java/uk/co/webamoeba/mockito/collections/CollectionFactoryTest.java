package uk.co.webamoeba.mockito.collections;

import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Test;

import uk.co.webamoeba.mockito.collections.exception.MockitoCollectionsException;

public class CollectionFactoryTest {

    private CollectionFactory factory = new CollectionFactory();

    @Test
    @SuppressWarnings("rawtypes")
    public void shouldCreateCollectionGivenCollection() {
	// Given
	Class<Collection> collectionClass = Collection.class;

	// When
	Collection<?> collection = factory.createCollection(collectionClass);

	// Then
	assertTrue(collection instanceof Set);
    }

    @Test
    @SuppressWarnings("rawtypes")
    public void shouldCreateCollectionGivenSortedSet() {
	// Given
	Class<SortedSet> collectionClass = SortedSet.class;

	// When
	Collection<?> collection = factory.createCollection(collectionClass);

	// Then
	assertTrue(collection instanceof TreeSet);
    }

    @Test
    @SuppressWarnings("rawtypes")
    public void shouldCreateCollectionGivenSet() {
	// Given
	Class<Set> collectionClass = Set.class;

	// When
	Collection<?> collection = factory.createCollection(collectionClass);

	// Then
	assertTrue(collection instanceof Set);
    }

    @Test
    @SuppressWarnings("rawtypes")
    public void shouldCreateCollectionGivenList() {
	// Given
	Class<List> collectionClass = List.class;

	// When
	Collection<?> collection = factory.createCollection(collectionClass);

	// Then
	assertTrue(collection instanceof List);
    }

    @Test
    @SuppressWarnings("rawtypes")
    public void shouldCreateCollectionGivenSpecificType() {
	// Given
	Class<LinkedList> collectionClass = LinkedList.class;

	// When
	Collection<?> collection = factory.createCollection(collectionClass);

	// Then
	assertTrue(collection instanceof LinkedList);
    }

    @Test(expected = MockitoCollectionsException.class)
    @SuppressWarnings("rawtypes")
    public void shouldFailToCreateCollectionGivenCannotInstantiateSpecificType() {
	// Given
	Class<Queue> collectionClass = Queue.class;

	// When
	Collection<?> collection = factory.createCollection(collectionClass);

	// Then
	assertTrue(collection instanceof LinkedList);
    }
}
