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

/**
 * @author James Kennard
 */
public class DefaultInjectableSelectionStrategyTest {

	private DefaultInjectableSelectionStrategy strategy = new DefaultInjectableSelectionStrategy();

	@Test
	public void shouldGetInjectables() {
		// Given
		Object injectable1 = mock(InputStream.class);
		Object injectable2 = mock(OutputStream.class);
		Object injectable3 = mock(FileInputStream.class);
		Set<Object> injectables = new HashSet<Object>(Arrays.asList(injectable1, injectable2, injectable3));

		// When
		Set<InputStream> actualInjectables = strategy.getInjectables(injectables, InputStream.class);

		// Then
		assertEquals(2, actualInjectables.size());
		assertTrue(actualInjectables.contains(injectable1));
		assertTrue(actualInjectables.contains(injectable3));
	}

	@Test
	public void shouldGetInjectablesGivenNoMatchingInjectables() {
		// Given
		Object injectable1 = mock(InputStream.class);
		Object injectable2 = mock(OutputStream.class);
		Set<Object> injectables = new HashSet<Object>(Arrays.asList(injectable1, injectable2));

		// When
		Set<FileOutputStream> actualInjectables = strategy.getInjectables(injectables, FileOutputStream.class);

		// Then
		assertTrue(actualInjectables.isEmpty());
	}

	@Test
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void shouldGetInjectableCollectionGivenTwoTypesOfCollection() {
		// Given
		InjectableCollection injectableCollection1 = new InjectableCollection<Set, InputStream>(Collections.emptySet(),
				Set.class, InputStream.class);
		InjectableCollection injectableCollection2 = new InjectableCollection<TreeSet, InputStream>(
				mock(TreeSet.class), TreeSet.class, InputStream.class);
		Set<InjectableCollection<Collection<Object>, Object>> injectableCollections = new HashSet<InjectableCollection<Collection<Object>, Object>>(
				Arrays.<InjectableCollection<Collection<Object>, Object>> asList(injectableCollection1,
						injectableCollection2));
		InjectableCollectionSet set = mock(InjectableCollectionSet.class);
		given(set.iterator()).willReturn(injectableCollections.iterator());

		Class<Set> typeOfCollection = Set.class;
		Class<InputStream> typeOfElements = InputStream.class;

		// When
		InjectableCollection<Set, InputStream> actualInjectableCollection = strategy.getInjectableCollection(set,
				typeOfCollection, typeOfElements);

		// Then
		assertSame(injectableCollection1, actualInjectableCollection);
	}

	@Test
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void shouldGetInjectableCollectionGivenTwoTypesOfElement() {
		// Given
		InjectableCollection injectableCollection1 = new InjectableCollection<List, FileOutputStream>(
				Collections.emptyList(), List.class, FileOutputStream.class);
		InjectableCollection injectableCollection2 = new InjectableCollection<List, OutputStream>(
				Collections.emptyList(), List.class, OutputStream.class);
		Set<InjectableCollection<Collection<Object>, Object>> injectableCollections = new HashSet<InjectableCollection<Collection<Object>, Object>>(
				Arrays.<InjectableCollection<Collection<Object>, Object>> asList(injectableCollection1,
						injectableCollection2));
		InjectableCollectionSet set = mock(InjectableCollectionSet.class);
		given(set.iterator()).willReturn(injectableCollections.iterator());

		Class<List> typeOfCollection = List.class;
		Class<OutputStream> typeOfElements = OutputStream.class;

		// When
		InjectableCollection<List, OutputStream> actualInjectableCollection = strategy.getInjectableCollection(set,
				typeOfCollection, typeOfElements);

		// Then
		assertSame(injectableCollection2, actualInjectableCollection);
	}

	@Test
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void shouldGetInjectableCollectionGivenNoMatchingInjectableCollection() {
		// Given
		InjectableCollection injectableCollection = new InjectableCollection<List, FileOutputStream>(
				Collections.emptyList(), List.class, FileOutputStream.class);
		Set<InjectableCollection<Collection<Object>, Object>> injectableCollections = Collections
				.<InjectableCollection<Collection<Object>, Object>> singleton(injectableCollection);
		InjectableCollectionSet set = mock(InjectableCollectionSet.class);
		given(set.iterator()).willReturn(injectableCollections.iterator());
		Class<Collection> typeOfCollection = Collection.class;
		Class<EventListener> typeOfElements = EventListener.class;

		// When
		InjectableCollection<Collection, EventListener> actualInjectableCollection = strategy.getInjectableCollection(
				set, typeOfCollection, typeOfElements);

		// Then
		assertNull(actualInjectableCollection);
	}

	@Test(expected = IllegalArgumentException.class)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void shouldFailToGetInjectableCollectionGivenNullInjectableCollectionSet() {
		// Given
		InjectableCollectionSet set = null;
		Class<Collection> typeOfCollection = Collection.class;
		Class<EventListener> typeOfElements = EventListener.class;

		// When
		strategy.getInjectableCollection(set, typeOfCollection, typeOfElements);

		// Then
		// Exception Thrown
	}

	@Test(expected = IllegalArgumentException.class)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void shouldFailToGetInjectableCollectionGivenNullTypeOfCollection() {
		// Given
		InjectableCollectionSet set = mock(InjectableCollectionSet.class);
		Class<Collection> typeOfCollection = null;
		Class<EventListener> typeOfElements = EventListener.class;

		// When
		strategy.getInjectableCollection(set, typeOfCollection, typeOfElements);

		// Then
		// Exception Thrown
	}

	@Test(expected = IllegalArgumentException.class)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void shouldFailToGetInjectableCollectionGivenNullTypeOfElements() {
		// Given
		InjectableCollectionSet set = null;
		Class<Collection> typeOfCollection = Collection.class;
		Class<EventListener> typeOfElements = null;

		// When
		strategy.getInjectableCollection(set, typeOfCollection, typeOfElements);

		// Then
		// Exception Thrown
	}
}
