package uk.co.webamoeba.mockito.collections.inject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.Collections;
import java.util.Set;

import org.junit.Test;

import uk.co.webamoeba.mockito.collections.util.HashOrderedSet;
import uk.co.webamoeba.mockito.collections.util.OrderedSet;

/**
 * @author James Kennard
 */
@SuppressWarnings("unchecked")
public class InjectionDetailsTest {

	@Test
	public void shouldGetInjectCollections() {
		// Given
		Object injectCollections = "Some InjectCollections";
		Set<Object> setOfInjectCollections = Collections.singleton(injectCollections);
		OrderedSet<Object> injectables = new HashOrderedSet<Object>();
		InjectableCollectionSet injectableCollectionSet = mock(InjectableCollectionSet.class);
		InjectionDetails details = new InjectionDetails(setOfInjectCollections, injectables, injectableCollectionSet);

		// When
		Set<Object> actualInjectCollections = details.getInjectCollections();

		// Then
		assertEquals(setOfInjectCollections, actualInjectCollections);
	}

	@Test
	public void shouldGetInjectables() {
		// Given
		Set<Object> injectCollections = mock(Set.class);
		Object injectable = "Some Injectable";
		OrderedSet<Object> injectables = new HashOrderedSet<Object>(Collections.singleton(injectable)); // FIXME
																										// remember that
																										// order is
																										// important
																										// when
		// making assertions
		InjectableCollectionSet injectableCollectionSet = mock(InjectableCollectionSet.class);
		InjectionDetails details = new InjectionDetails(injectCollections, injectables, injectableCollectionSet);

		// When
		OrderedSet<Object> actualInjectables = details.getInjectables();

		// Then
		assertTrue(injectables != actualInjectables);
		assertEquals(injectables, actualInjectables);
	}

	@Test
	public void shouldGetInjectableCollectionSet() {
		// Given
		Set<Object> injectCollections = mock(Set.class);
		OrderedSet<Object> injectables = new HashOrderedSet<Object>();
		InjectableCollectionSet injectableCollectionSet = mock(InjectableCollectionSet.class);
		InjectionDetails details = new InjectionDetails(injectCollections, injectables, injectableCollectionSet);

		// When
		InjectableCollectionSet actualInjectableCollectionSet = details.getInjectableCollectionSet();

		// Then
		assertSame(injectableCollectionSet, actualInjectableCollectionSet);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldFailToConstructGivenNullInjectCollections() {
		// Given
		Set<Object> injectCollections = null;
		OrderedSet<Object> injectables = mock(OrderedSet.class);
		InjectableCollectionSet injectableCollectionSet = mock(InjectableCollectionSet.class);

		// When
		new InjectionDetails(injectCollections, injectables, injectableCollectionSet);

		// Then
		// Exception thrown
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldFailToConstructGivenNullInjectables() {
		// Given
		Set<Object> injectCollections = Collections.emptySet();
		OrderedSet<Object> injectables = null;
		InjectableCollectionSet injectableCollectionSet = mock(InjectableCollectionSet.class);

		// When
		new InjectionDetails(injectCollections, injectables, injectableCollectionSet);

		// Then
		// Exception thrown
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldFailToConstructGivenNullInjectableCollections() {
		// Given
		Set<Object> injectCollections = Collections.emptySet();
		OrderedSet<Object> injectables = mock(OrderedSet.class);
		InjectableCollectionSet injectableCollectionSet = null;

		// When
		new InjectionDetails(injectCollections, injectables, injectableCollectionSet);

		// Then
		// Exception thrown
	}
}
