package uk.co.webamoeba.mockito.collections.inject;

import static org.junit.Assert.assertFalse;
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
	public void shouldMatchesGivenSelf() {
		// Given
		Set<Object> value = Collections.emptySet();
		Class<Set> typeOfCollection = Set.class;
		Class<Object> typeOfElements = Object.class;
		CollectionOfMocksField collectionOfMocksField = new CollectionOfMocksField(value, typeOfCollection, typeOfElements);

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
		CollectionOfMocksField otherInjectableCollection = new CollectionOfMocksField(Collections.emptyList(), List.class,
				Number.class);

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
