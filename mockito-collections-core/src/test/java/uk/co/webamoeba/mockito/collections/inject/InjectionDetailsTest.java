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
		OrderedSet<Object> mocks = new HashOrderedSet<Object>();
		CollectionOfMocksFieldSet collectionOfMocksFieldSet = mock(CollectionOfMocksFieldSet.class);
		InjectionDetails details = new InjectionDetails(setOfInjectCollections, mocks, collectionOfMocksFieldSet);

		// When
		Set<Object> actualInjectCollections = details.getInjectCollections();

		// Then
		assertEquals(setOfInjectCollections, actualInjectCollections);
	}

	@Test
	public void shouldGetInjectables() {
		// Given
		Set<Object> injectCollections = mock(Set.class);
		Object mock = mock(Object.class);
		OrderedSet<Object> mocks = new HashOrderedSet<Object>(Collections.singleton(mock));
		// FIXME remember that order is important when making assertions
		CollectionOfMocksFieldSet collectionOfMocksFieldSet = mock(CollectionOfMocksFieldSet.class);
		InjectionDetails details = new InjectionDetails(injectCollections, mocks, collectionOfMocksFieldSet);

		// When
		OrderedSet<Object> actualInjectables = details.getMocks();

		// Then
		assertTrue(mocks != actualInjectables);
		assertEquals(mocks, actualInjectables);
	}

	@Test
	public void shouldGetInjectableCollectionSet() {
		// Given
		Set<Object> injectCollections = mock(Set.class);
		OrderedSet<Object> mocks = new HashOrderedSet<Object>();
		CollectionOfMocksFieldSet collectionOfMocksFieldSet = mock(CollectionOfMocksFieldSet.class);
		InjectionDetails details = new InjectionDetails(injectCollections, mocks, collectionOfMocksFieldSet);

		// When
		CollectionOfMocksFieldSet actualInjectableCollectionSet = details.getInjectableCollectionSet();

		// Then
		assertSame(collectionOfMocksFieldSet, actualInjectableCollectionSet);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldFailToConstructGivenNullInjectCollections() {
		// Given
		Set<Object> injectCollections = null;
		OrderedSet<Object> mocks = mock(OrderedSet.class);
		CollectionOfMocksFieldSet collectionOfMocksFieldSet = mock(CollectionOfMocksFieldSet.class);

		// When
		new InjectionDetails(injectCollections, mocks, collectionOfMocksFieldSet);

		// Then
		// Exception thrown
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldFailToConstructGivenNullInjectables() {
		// Given
		Set<Object> injectCollections = Collections.emptySet();
		OrderedSet<Object> mocks = null;
		CollectionOfMocksFieldSet collectionOfMocksFieldSet = mock(CollectionOfMocksFieldSet.class);

		// When
		new InjectionDetails(injectCollections, mocks, collectionOfMocksFieldSet);

		// Then
		// Exception thrown
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldFailToConstructGivenNullInjectableCollections() {
		// Given
		Set<Object> injectCollections = Collections.emptySet();
		OrderedSet<Object> mocks = mock(OrderedSet.class);
		CollectionOfMocksFieldSet collectionOfMocksFieldSet = null;

		// When
		new InjectionDetails(injectCollections, mocks, collectionOfMocksFieldSet);

		// Then
		// Exception thrown
	}
}
