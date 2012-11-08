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
public class InjectableCollectionTest {

	@Test
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void shouldMatchesGivenSelf() {
		// Given
		Set<Object> value = Collections.emptySet();
		Class<Set> typeOfCollection = Set.class;
		Class<Object> typeOfElements = Object.class;
		InjectableCollection injectableCollection = new InjectableCollection(value, typeOfCollection, typeOfElements);

		// When
		boolean matches = injectableCollection.matches(injectableCollection);

		// Then
		assertTrue(matches);
	}

	@Test
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void shouldMatchesGivenEqualTypeOfCollection() {
		// Given
		Set value = Collections.emptySet();
		InjectableCollection injectableCollection = new InjectableCollection(value, Set.class, Object.class);
		InjectableCollection otherInjectableCollection = new InjectableCollection(value, Set.class, String.class);

		// When
		boolean matches = injectableCollection.matches(otherInjectableCollection);

		// Then
		assertFalse(matches);
	}

	@Test
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void shouldMatchesGivenEqualTypeOfElement() {
		// Given
		InjectableCollection injectableCollection = new InjectableCollection(Collections.emptySet(), Set.class,
				Number.class);
		InjectableCollection otherInjectableCollection = new InjectableCollection(Collections.emptyList(), List.class,
				Number.class);

		// When
		boolean matches = injectableCollection.matches(otherInjectableCollection);

		// Then
		assertFalse(matches);
	}

	@Test
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void shouldMatchesGivenEqualTypeOfCollectionAndEqualTypeOfElement() {
		// Given
		Set<Object> value = Collections.emptySet();
		InjectableCollection injectableCollection = new InjectableCollection(value, Set.class, Number.class);
		InjectableCollection otherInjectableCollection = new InjectableCollection(value, Set.class, Number.class);

		// When
		boolean matches = injectableCollection.matches(otherInjectableCollection);

		// Then
		assertTrue(matches);
	}
}
