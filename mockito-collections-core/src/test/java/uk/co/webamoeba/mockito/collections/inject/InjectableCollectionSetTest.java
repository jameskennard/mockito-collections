package uk.co.webamoeba.mockito.collections.inject;

import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.junit.Test;

/**
 * @author James Kennard
 */
public class InjectableCollectionSetTest {

	@Test
	@SuppressWarnings("rawtypes")
	public void shouldConstructGivenVarArg() {
		// Given
		InjectableCollection injectableCollection1 = mock(InjectableCollection.class);

		// When
		InjectableCollectionSet set = new InjectableCollectionSet(injectableCollection1);

		// Then
		assertTrue(set.size() == 1);
		assertTrue(set.asSet().contains(injectableCollection1));
	}

	@Test
	@SuppressWarnings("rawtypes")
	public void shouldConstructGivenCollection() {
		// Given
		InjectableCollection injectableCollection1 = mock(InjectableCollection.class);
		Collection<InjectableCollection> collection = Collections.singleton(injectableCollection1);

		// When
		InjectableCollectionSet set = new InjectableCollectionSet(collection);

		// Then
		assertTrue(set.size() == 1);
		assertTrue(set.asSet().contains(injectableCollection1));
	}

	@Test(expected = IllegalArgumentException.class)
	@SuppressWarnings("rawtypes")
	public void shouldFailToConstructGivenMatchingInjectableCollections() {
		// Given
		InjectableCollection injectableCollection1 = mock(InjectableCollection.class);
		InjectableCollection injectableCollection2 = mock(InjectableCollection.class);
		given(injectableCollection1.matches(injectableCollection2)).willReturn(true);
		given(injectableCollection2.matches(injectableCollection1)).willReturn(true);
		Collection<InjectableCollection> collection = Arrays.asList(injectableCollection1, injectableCollection2);

		// When
		new InjectableCollectionSet(collection);

		// Then
		// Exception thrown
	}

	@Test
	@SuppressWarnings("rawtypes")
	public void shouldConstructGivenNonMatchingInjectableCollections() {
		// Given
		InjectableCollection injectableCollection1 = mock(InjectableCollection.class);
		InjectableCollection injectableCollection2 = mock(InjectableCollection.class);
		given(injectableCollection1.matches(injectableCollection2)).willReturn(false);
		given(injectableCollection2.matches(injectableCollection1)).willReturn(false);
		Collection<InjectableCollection> collection = Arrays.asList(injectableCollection1, injectableCollection2);

		// When
		InjectableCollectionSet set = new InjectableCollectionSet(collection);

		// Then
		assertTrue(set.size() == 2);
		assertTrue(set.asSet().contains(injectableCollection1));
		assertTrue(set.asSet().contains(injectableCollection2));
	}

	@Test
	@SuppressWarnings("rawtypes")
	public void shouldAddNonMatchingInjectableCollection() {
		// Given
		InjectableCollection injectableCollection1 = mock(InjectableCollection.class);
		InjectableCollection injectableCollection2 = mock(InjectableCollection.class);
		given(injectableCollection1.matches(injectableCollection2)).willReturn(false);
		given(injectableCollection2.matches(injectableCollection1)).willReturn(false);
		InjectableCollectionSet set = new InjectableCollectionSet(injectableCollection1);

		// When
		set.add(injectableCollection2);

		// Then
		assertTrue(set.asSet().size() == 2);
		assertTrue(set.asSet().contains(injectableCollection1));
		assertTrue(set.asSet().contains(injectableCollection2));
	}

	@Test(expected = IllegalArgumentException.class)
	@SuppressWarnings("rawtypes")
	public void shouldFailToAddMatchingInjectableCollection() {
		// Given
		InjectableCollection injectableCollection1 = mock(InjectableCollection.class);
		InjectableCollection injectableCollection2 = mock(InjectableCollection.class);
		given(injectableCollection1.matches(injectableCollection2)).willReturn(true);
		given(injectableCollection2.matches(injectableCollection1)).willReturn(true);
		InjectableCollectionSet set = new InjectableCollectionSet(injectableCollection1);

		// When
		set.add(injectableCollection2);

		// Then
		// Exception Thrown
	}
}
