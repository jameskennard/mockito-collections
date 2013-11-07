package uk.co.webamoeba.mockito.collections.inject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import java.util.Collections;
import java.util.Set;

import org.junit.Test;

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
		OrderedSet<Object> mocks = new OrderedSet<Object>();
		CollectionOfMocksFieldSet collectionOfMocksFieldSet = mock(CollectionOfMocksFieldSet.class);
		InjectionDetails details = new InjectionDetails(setOfInjectCollections, mocks,  new OrderedSet<Object>(),collectionOfMocksFieldSet);

		// When
		Set<Object> actualInjectCollections = details.getInjectCollections();

		// Then
		assertEquals(setOfInjectCollections, actualInjectCollections);
	}

	@Test
	public void shouldGetMocks() {
		// Given
		Set<Object> injectCollections = mock(Set.class);
		Object mock = mock(Object.class);
		OrderedSet<Object> mocks = new OrderedSet<Object>(Collections.singleton(mock));
		CollectionOfMocksFieldSet collectionOfMocksFieldSet = mock(CollectionOfMocksFieldSet.class);
		InjectionDetails details = new InjectionDetails(injectCollections, mocks, new OrderedSet<Object>(), collectionOfMocksFieldSet);

		// When
		OrderedSet<Object> actualInjectables = details.getMocks();

		// Then
		assertTrue(mocks != actualInjectables);
		assertEquals(mocks, actualInjectables);
	}
	
	@Test
	public void shouldGetSpies() {
		// Given
		Set<Object> injectCollections = mock(Set.class);
		Object spy = spy(new Object());
		OrderedSet<Object> spies = new OrderedSet<Object>(Collections.singleton(spy));
		CollectionOfMocksFieldSet collectionOfMocksFieldSet = mock(CollectionOfMocksFieldSet.class);
		InjectionDetails details = new InjectionDetails(injectCollections, new OrderedSet<Object>(), spies, collectionOfMocksFieldSet);

		// When
		OrderedSet<Object> actualInjectables = details.getSpies();

		// Then
		assertTrue(spies != actualInjectables);
		assertEquals(spies, actualInjectables);
	}
	
	@Test
	public void shouldGetCollectionOfMocksFieldSet() {
		// Given
		Set<Object> injectCollections = mock(Set.class);
		OrderedSet<Object> mocks = new OrderedSet<Object>();
		CollectionOfMocksFieldSet collectionOfMocksFieldSet = mock(CollectionOfMocksFieldSet.class);
		InjectionDetails details = new InjectionDetails(injectCollections, mocks, new OrderedSet<Object>(), collectionOfMocksFieldSet);

		// When
		CollectionOfMocksFieldSet actualInjectableCollectionSet = details.getInjectableCollectionSet();

		// Then
		assertSame(collectionOfMocksFieldSet, actualInjectableCollectionSet);
	}
	
	@Test
	public void shouldFailToConstructGivenNullInjectCollections() {
		// Given
		Set<Object> injectCollections = null;
		OrderedSet<Object> mocks = mock(OrderedSet.class);
		OrderedSet<Object> spies = new OrderedSet<Object>();
		CollectionOfMocksFieldSet collectionOfMocksFieldSet = mock(CollectionOfMocksFieldSet.class);

		IllegalArgumentException exception = instantiateAndThrowIllegalArgumentException(injectCollections, mocks, spies,
				collectionOfMocksFieldSet);

		// Then
		assertEquals("injectCollections must not be null", exception.getMessage());
	}

	@Test
	public void shouldFailToConstructGivenNullMocks() {
		// Given
		Set<Object> injectCollections = Collections.emptySet();
		OrderedSet<Object> mocks = null;
		OrderedSet<Object> spies = new OrderedSet<Object>();
		CollectionOfMocksFieldSet collectionOfMocksFieldSet = mock(CollectionOfMocksFieldSet.class);

		IllegalArgumentException exception = instantiateAndThrowIllegalArgumentException(injectCollections, mocks, spies,
				collectionOfMocksFieldSet);

		// Then
		assertEquals("mocks must not be null", exception.getMessage());
	}

	@Test
	public void shouldFailToConstructGivenNullSpies() {
		// Given
		Set<Object> injectCollections = Collections.emptySet();
		OrderedSet<Object> mocks = new OrderedSet<Object>();
		OrderedSet<Object> spies = null;
		CollectionOfMocksFieldSet collectionOfMocksFieldSet = mock(CollectionOfMocksFieldSet.class);

		IllegalArgumentException exception = instantiateAndThrowIllegalArgumentException(injectCollections, mocks, spies,
				collectionOfMocksFieldSet);

		// Then
		assertEquals("spies must not be null", exception.getMessage());
	}

	@Test
	public void shouldFailToConstructGivenNullCollectionOfMocksFieldSet() {
		// Given
		Set<Object> injectCollections = Collections.emptySet();
		OrderedSet<Object> mocks = mock(OrderedSet.class);
		OrderedSet<Object> spies = mock(OrderedSet.class);
		CollectionOfMocksFieldSet collectionOfMocksFieldSet = null;

		// When
		IllegalArgumentException exception = instantiateAndThrowIllegalArgumentException(injectCollections, mocks, spies,
				collectionOfMocksFieldSet);

		// Then
		assertEquals("collectionOfMocksFieldSet must not be null", exception.getMessage());
	}
	
	private IllegalArgumentException instantiateAndThrowIllegalArgumentException(Set<Object> injectCollections,
			OrderedSet<Object> mocks, OrderedSet<Object> spies, CollectionOfMocksFieldSet collectionOfMocksFieldSet) {
		try {
			new InjectionDetails(injectCollections, mocks, spies, collectionOfMocksFieldSet);

		} catch (IllegalArgumentException e) {
			return e;
		}
		return null;
	}
}
