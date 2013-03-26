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
public class CollectionOfMocksFieldSetTest {

	@Test
	@SuppressWarnings("rawtypes")
	public void shouldConstructGivenVarArg() {
		// Given
		CollectionOfMocksField mocksField1 = mock(CollectionOfMocksField.class);

		// When
		CollectionOfMocksFieldSet set = new CollectionOfMocksFieldSet(mocksField1);

		// Then
		assertTrue(set.size() == 1);
		assertTrue(set.asSet().contains(mocksField1));
	}

	@Test
	@SuppressWarnings("rawtypes")
	public void shouldConstructGivenCollection() {
		// Given
		CollectionOfMocksField mocksField1 = mock(CollectionOfMocksField.class);
		Collection<CollectionOfMocksField> collection = Collections.singleton(mocksField1);

		// When
		CollectionOfMocksFieldSet set = new CollectionOfMocksFieldSet(collection);

		// Then
		assertTrue(set.size() == 1);
		assertTrue(set.asSet().contains(mocksField1));
	}

	@Test(expected = IllegalArgumentException.class)
	@SuppressWarnings("rawtypes")
	public void shouldFailToConstructGivenMatchingMockCollections() {
		// Given
		CollectionOfMocksField mocksField1 = mock(CollectionOfMocksField.class);
		CollectionOfMocksField mocksField2 = mock(CollectionOfMocksField.class);
		given(mocksField1.matches(mocksField2)).willReturn(true);
		given(mocksField2.matches(mocksField1)).willReturn(true);
		Collection<CollectionOfMocksField> collection = Arrays.asList(mocksField1, mocksField2);

		// When
		new CollectionOfMocksFieldSet(collection);

		// Then
		// Exception thrown
	}

	@Test
	@SuppressWarnings("rawtypes")
	public void shouldConstructGivenNonMatchingMockCollections() {
		// Given
		CollectionOfMocksField mocksField1 = mock(CollectionOfMocksField.class);
		CollectionOfMocksField mocksField2 = mock(CollectionOfMocksField.class);
		given(mocksField1.matches(mocksField2)).willReturn(false);
		given(mocksField2.matches(mocksField1)).willReturn(false);
		Collection<CollectionOfMocksField> collection = Arrays.asList(mocksField1, mocksField2);

		// When
		CollectionOfMocksFieldSet set = new CollectionOfMocksFieldSet(collection);

		// Then
		assertTrue(set.size() == 2);
		assertTrue(set.asSet().contains(mocksField1));
		assertTrue(set.asSet().contains(mocksField2));
	}

	@Test
	@SuppressWarnings("rawtypes")
	public void shouldAddNonMatchingMockCollection() {
		// Given
		CollectionOfMocksField mocksField1 = mock(CollectionOfMocksField.class);
		CollectionOfMocksField mocksField2 = mock(CollectionOfMocksField.class);
		given(mocksField1.matches(mocksField2)).willReturn(false);
		given(mocksField2.matches(mocksField1)).willReturn(false);
		CollectionOfMocksFieldSet set = new CollectionOfMocksFieldSet(mocksField1);

		// When
		set.add(mocksField2);

		// Then
		assertTrue(set.asSet().size() == 2);
		assertTrue(set.asSet().contains(mocksField1));
		assertTrue(set.asSet().contains(mocksField2));
	}

	@Test(expected = IllegalArgumentException.class)
	@SuppressWarnings("rawtypes")
	public void shouldFailToAddMatchingMockCollection() {
		// Given
		CollectionOfMocksField mocksField1 = mock(CollectionOfMocksField.class);
		CollectionOfMocksField mocksField2 = mock(CollectionOfMocksField.class);
		given(mocksField1.matches(mocksField2)).willReturn(true);
		given(mocksField2.matches(mocksField1)).willReturn(true);
		CollectionOfMocksFieldSet set = new CollectionOfMocksFieldSet(mocksField1);

		// When
		set.add(mocksField2);

		// Then
		// Exception Thrown
	}
}
