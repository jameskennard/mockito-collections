package uk.co.webamoeba.mockito.collections.inject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.junit.Test;

/**
 * @author James Kennard
 */
public class CollectionOfMocksFieldTest {

	@Test
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void shouldInstantiate() {
		// Given
		Set<Object> value = Collections.emptySet();
		Class<Set> typeOfCollection = Set.class;
		Class<Number> typeOfElements = Number.class;

		// When
		CollectionOfMocksField collectionOfMocksField = new CollectionOfMocksField(value, typeOfCollection,
				typeOfElements);

		// Then
		assertSame(value, collectionOfMocksField.getValue());
		assertEquals(typeOfCollection, collectionOfMocksField.getTypeOfCollection());
		assertEquals(typeOfElements, collectionOfMocksField.getTypeOfElements());
	}

	@Test
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void shouldMatchesGivenSelf() {
		// Given
		Set<Object> value = Collections.emptySet();
		Class<Set> typeOfCollection = Set.class;
		Class<Object> typeOfElements = Object.class;
		CollectionOfMocksField collectionOfMocksField = new CollectionOfMocksField(value, typeOfCollection,
				typeOfElements);

		// When
		boolean matches = collectionOfMocksField.matches(collectionOfMocksField);

		// Then
		assertTrue(matches);
	}

	@Test
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void shouldMatchesGivenEqualTypeOfCollection() {
		// Given
		Set value = Collections.emptySet();
		CollectionOfMocksField collectionOfMocksField = new CollectionOfMocksField(value, Set.class, Object.class);
		CollectionOfMocksField otherInjectableCollection = new CollectionOfMocksField(value, Set.class, String.class);

		// When
		boolean matches = collectionOfMocksField.matches(otherInjectableCollection);

		// Then
		assertFalse(matches);
	}

	@Test
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void shouldMatchesGivenEqualTypeOfElement() {
		// Given
		CollectionOfMocksField collectionOfMocksField = new CollectionOfMocksField(Collections.emptySet(), Set.class,
				Number.class);
		CollectionOfMocksField otherInjectableCollection = new CollectionOfMocksField(Collections.emptyList(),
				List.class, Number.class);

		// When
		boolean matches = collectionOfMocksField.matches(otherInjectableCollection);

		// Then
		assertFalse(matches);
	}

	@Test
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void shouldMatchesGivenEqualTypeOfCollectionAndEqualTypeOfElement() {
		// Given
		Set<Object> value = Collections.emptySet();
		CollectionOfMocksField collectionOfMocksField = new CollectionOfMocksField(value, Set.class, Number.class);
		CollectionOfMocksField otherInjectableCollection = new CollectionOfMocksField(value, Set.class, Number.class);

		// When
		boolean matches = collectionOfMocksField.matches(otherInjectableCollection);

		// Then
		assertTrue(matches);
	}
}
