package uk.co.webamoeba.mockito.collections.inject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EventListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;

import uk.co.webamoeba.mockito.collections.util.HashOrderedSet;
import uk.co.webamoeba.mockito.collections.util.OrderedSet;

/**
 * @author James Kennard
 */
public class DefaultMockSelectionStrategyTest {

	private DefaultMockSelectionStrategy strategy = new DefaultMockSelectionStrategy();

	@Test
	public void shouldGetMocks() {
		// Given
		InputStream mock1 = mock(InputStream.class);
		OutputStream mock2 = mock(OutputStream.class);
		InputStream mock3 = mock(FileInputStream.class);
		OrderedSet<Object> mocks = new HashOrderedSet<Object>(Arrays.<Object> asList(mock1, mock2, mock3));

		// When
		OrderedSet<InputStream> actualMocks = strategy.selectMocks(mocks, InputStream.class);

		// Then
		assertTrue(actualMocks.equals(new HashOrderedSet<InputStream>(Arrays.<InputStream> asList(mock1, mock3))));
	}

	@Test
	public void shouldGetMocksGivenNoMatchingMocks() {
		// Given
		Object mock1 = mock(InputStream.class);
		Object mock2 = mock(OutputStream.class);
		OrderedSet<Object> mocks = new HashOrderedSet<Object>(Arrays.asList(mock1, mock2));

		// When
		OrderedSet<FileOutputStream> actualMocks = strategy.selectMocks(mocks, FileOutputStream.class);

		// Then
		assertTrue(actualMocks.isEmpty());
	}

	@Test
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void shouldGetMockCollectionGivenTwoTypesOfCollection() {
		// Given
		CollectionOfMocksField mockCollection1 = new CollectionOfMocksField(Collections.emptySet(), Set.class,
				InputStream.class);
		CollectionOfMocksField mockCollection2 = new CollectionOfMocksField(mock(TreeSet.class), TreeSet.class,
				InputStream.class);
		Set<CollectionOfMocksField<Collection<Object>, Object>> mockCollections = new HashSet<CollectionOfMocksField<Collection<Object>, Object>>(
				Arrays.<CollectionOfMocksField<Collection<Object>, Object>> asList(mockCollection1, mockCollection2));
		CollectionOfMocksFieldSet set = mock(CollectionOfMocksFieldSet.class);
		given(set.iterator()).willReturn(mockCollections.iterator());

		Class<Set> typeOfCollection = Set.class;
		Class<InputStream> typeOfElements = InputStream.class;

		// When
		CollectionOfMocksField actualMockCollection = strategy.getCollectionOfMocksField(set, typeOfCollection,
				typeOfElements);

		// Then
		assertSame(mockCollection1, actualMockCollection);
	}

	@Test
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void shouldGetMockCollectionGivenTwoTypesOfElement() {
		// Given
		CollectionOfMocksField mockCollection1 = new CollectionOfMocksField(Collections.emptyList(), List.class,
				FileOutputStream.class);
		CollectionOfMocksField mockCollection2 = new CollectionOfMocksField(Collections.emptyList(), List.class,
				OutputStream.class);
		Set<CollectionOfMocksField<Collection<Object>, Object>> mockCollections = new HashSet<CollectionOfMocksField<Collection<Object>, Object>>(
				Arrays.<CollectionOfMocksField<Collection<Object>, Object>> asList(mockCollection1, mockCollection2));
		CollectionOfMocksFieldSet set = mock(CollectionOfMocksFieldSet.class);
		given(set.iterator()).willReturn(mockCollections.iterator());

		Class<List> typeOfCollection = List.class;
		Class<OutputStream> typeOfElements = OutputStream.class;

		// When
		CollectionOfMocksField actualMockCollection = strategy.getCollectionOfMocksField(set, typeOfCollection,
				typeOfElements);

		// Then
		assertSame(mockCollection2, actualMockCollection);
	}

	@Test
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void shouldGetMockCollectionGivenNoMatchingMockCollection() {
		// Given
		CollectionOfMocksField collectionOfMocksField = new CollectionOfMocksField(Collections.emptyList(), List.class,
				FileOutputStream.class);
		Set<CollectionOfMocksField<Collection<Object>, Object>> mockCollections = Collections
				.<CollectionOfMocksField<Collection<Object>, Object>> singleton(collectionOfMocksField);
		CollectionOfMocksFieldSet set = mock(CollectionOfMocksFieldSet.class);
		given(set.iterator()).willReturn(mockCollections.iterator());
		Class<Collection> typeOfCollection = Collection.class;
		Class<EventListener> typeOfElements = EventListener.class;

		// When
		CollectionOfMocksField actualMockCollection = strategy.getCollectionOfMocksField(set, typeOfCollection,
				typeOfElements);

		// Then
		assertNull(actualMockCollection);
	}

	@Test
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void shouldFailToGetMockCollectionGivenNullMockCollectionSet() {
		// Given
		CollectionOfMocksFieldSet set = null;
		Class<Collection> typeOfCollection = Collection.class;
		Class<EventListener> typeOfElements = EventListener.class;

		// When
		IllegalArgumentException exception = getCollectionOfMocksFieldAndIllegalArgumentExceptionThrown(set,
				typeOfCollection, typeOfElements);

		// Then
		assertEquals("collectionOfMocksFieldSet must not be null", exception.getMessage());
	}

	@Test
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void shouldFailToGetMockCollectionGivenNullTypeOfCollection() {
		// Given
		CollectionOfMocksFieldSet set = mock(CollectionOfMocksFieldSet.class);
		Class<Collection> typeOfCollection = null;
		Class<EventListener> typeOfElements = EventListener.class;

		// When
		IllegalArgumentException exception = getCollectionOfMocksFieldAndIllegalArgumentExceptionThrown(set,
				typeOfCollection, typeOfElements);

		// Then
		assertEquals("typeOfCollection must not be null", exception.getMessage());
	}

	@Test
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void shouldFailToGetMockCollectionGivenNullTypeOfElements() {
		// Given
		CollectionOfMocksFieldSet set = mock(CollectionOfMocksFieldSet.class);
		Class<Collection> typeOfCollection = Collection.class;
		Class<EventListener> typeOfElements = null;

		// When
		IllegalArgumentException exception = getCollectionOfMocksFieldAndIllegalArgumentExceptionThrown(set,
				typeOfCollection, typeOfElements);

		// Then
		assertEquals("typeOfElements must not be null", exception.getMessage());
	}

	private <C extends Collection<E>, E> IllegalArgumentException getCollectionOfMocksFieldAndIllegalArgumentExceptionThrown(
			CollectionOfMocksFieldSet set, Class<C> typeOfCollection, Class<E> typeOfElements) {
		try {
			strategy.getCollectionOfMocksField(set, typeOfCollection, typeOfElements);
		} catch (IllegalArgumentException e) {
			return e;
		}
		return null;
	}
}
