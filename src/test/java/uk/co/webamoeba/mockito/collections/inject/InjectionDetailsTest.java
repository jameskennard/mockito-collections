package uk.co.webamoeba.mockito.collections.inject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;

import java.util.Collections;
import java.util.Set;

import org.junit.Test;

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
		Set<Object> injectables = mock(Set.class);
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
		Set<Object> injectables = Collections.singleton(injectable);
		InjectableCollectionSet injectableCollectionSet = mock(InjectableCollectionSet.class);
		InjectionDetails details = new InjectionDetails(injectCollections, injectables, injectableCollectionSet);

		// When
		Set<Object> actualInjectables = details.getInjectables();

		// Then
		assertEquals(injectables, actualInjectables);
	}

	@Test
	public void shouldGetInjectableCollectionSet() {
		// Given
		Set<Object> injectCollections = mock(Set.class);
		Set<Object> injectables = Collections.emptySet();
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
		Set<Object> injectables = Collections.emptySet();
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
		Set<Object> injectables = null;
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
		Set<Object> injectables = Collections.emptySet();
		InjectableCollectionSet injectableCollectionSet = null;

		// When
		new InjectionDetails(injectCollections, injectables, injectableCollectionSet);

		// Then
		// Exception thrown
	}
}
