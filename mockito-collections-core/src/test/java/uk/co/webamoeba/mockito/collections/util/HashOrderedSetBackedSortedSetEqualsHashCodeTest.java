package uk.co.webamoeba.mockito.collections.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.Arrays;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

public class HashOrderedSetBackedSortedSetEqualsHashCodeTest {

	@Test
	public void shouldEqualGivenReflexive() {
		HashOrderedSetBackedSortedSet<Object> set = new HashOrderedSetBackedSortedSet<Object>();
		boolean equals = set.equals(set);
		assertTrue(equals);
	}

	@Test
	public void shouldNotEqualGivenNull() {
		HashOrderedSetBackedSortedSet<Object> set = new HashOrderedSetBackedSortedSet<Object>();
		boolean equals = set.equals(null);
		assertFalse(equals);
	}

	@Test
	public void shouldNotEqualGivenSubClass() {
		HashOrderedSetBackedSortedSet<Object> set = new HashOrderedSetBackedSortedSet<Object>();
		HashOrderedSetBackedSortedSet<?> subClassedSet = mock(HashOrderedSetBackedSortedSet.class);

		boolean equals = set.equals(subClassedSet);

		assertFalse(equals);
	}

	@Test
	public void shouldEqualGivenSameContents() {
		List<String> values = Arrays.asList("One", "Two");
		HashOrderedSetBackedSortedSet<String> set = new HashOrderedSetBackedSortedSet<String>();
		HashOrderedSetBackedSortedSet<String> otherSet = new HashOrderedSetBackedSortedSet<String>();
		set.addAll(values);
		otherSet.addAll(values);

		boolean equals = set.equals(otherSet);

		assertTrue(equals);
	}

	@Test
	public void shouldHaveSameHashcodeGivenSameContents() {
		List<String> values = Arrays.asList("One", "Two");
		HashOrderedSetBackedSortedSet<String> set = new HashOrderedSetBackedSortedSet<String>();
		HashOrderedSetBackedSortedSet<String> otherSet = new HashOrderedSetBackedSortedSet<String>();
		set.addAll(values);
		otherSet.addAll(values);

		int distance = set.hashCode() - otherSet.hashCode();

		assertEquals(0, distance);
	}

	@Test
	public void shouldNotEqualGivenDifferentContents() {
		HashOrderedSetBackedSortedSet<String> set = new HashOrderedSetBackedSortedSet<String>();
		HashOrderedSetBackedSortedSet<String> otherSet = new HashOrderedSetBackedSortedSet<String>();
		set.addAll(Arrays.asList("One", "Two"));
		otherSet.addAll(Arrays.asList("Three", "Four"));

		boolean equals = set.equals(otherSet);

		assertFalse(equals);
	}

	@Test
	@Ignore
	// FIXME requires fixing of the HashOrderedSet equals method
	public void shouldNotEqualGivenContentsInDifferentOrder() {
		HashOrderedSetBackedSortedSet<String> set = new HashOrderedSetBackedSortedSet<String>();
		HashOrderedSetBackedSortedSet<String> otherSet = new HashOrderedSetBackedSortedSet<String>();
		set.addAll(Arrays.asList("One", "Two"));
		otherSet.addAll(Arrays.asList("Two", "One"));

		boolean equals = set.equals(otherSet);

		assertFalse(equals);
	}
}
