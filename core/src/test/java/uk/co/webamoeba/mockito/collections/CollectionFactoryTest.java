package uk.co.webamoeba.mockito.collections;

import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.Test;

public class CollectionFactoryTest {

	private CollectionFactory factory = new CollectionFactory();

	@Test
	public void shouldCreateCollectionGivenCollection() {
		// Given
		Class<Collection> collectionClass = Collection.class;

		// When
		Collection<?> collection = factory.createCollection(collectionClass);

		// Then
		assertTrue(collection instanceof Set);
	}

	@Test
	public void shouldCreateCollectionGivenSet() {
		// Given
		Class<Set> collectionClass = Set.class;

		// When
		Collection<?> collection = factory.createCollection(collectionClass);

		// Then
		assertTrue(collection instanceof Set);
	}

	@Test
	public void shouldCreateCollectionGivenList() {
		// Given
		Class<List> collectionClass = List.class;

		// When
		Collection<?> collection = factory.createCollection(collectionClass);

		// Then
		assertTrue(collection instanceof List);
	}

	@Test
	public void shouldCreateCollectionGivenSpecificType() {
		// Given
		Class<LinkedList> collectionClass = LinkedList.class;

		// When
		Collection<?> collection = factory.createCollection(collectionClass);

		// Then
		assertTrue(collection instanceof LinkedList);
	}
}
