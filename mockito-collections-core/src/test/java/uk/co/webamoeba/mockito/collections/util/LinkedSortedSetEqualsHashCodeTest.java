package uk.co.webamoeba.mockito.collections.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class LinkedSortedSetEqualsHashCodeTest {

	@Test
	public void shouldEqualGivenReflexive() {
		LinkedSortedSet<Object> set = new LinkedSortedSet<Object>();
		boolean equals = set.equals(set);
		assertTrue(equals);
	}

	@Test
	public void shouldNotEqualGivenNull() {
		LinkedSortedSet<Object> set = new LinkedSortedSet<Object>();
		boolean equals = set.equals(null);
		assertFalse(equals);
	}

	@Test
	public void shouldNotEqualGivenSubClass() {
		LinkedSortedSet<Object> set = new LinkedSortedSet<Object>();
		LinkedSortedSet<?> subClassedSet = mock(LinkedSortedSet.class);

		boolean equals = set.equals(subClassedSet);

		assertFalse(equals);
	}

	@Test
	public void shouldEqualGivenSameContents() {
		List<String> values = Arrays.asList("One", "Two");
		LinkedSortedSet<String> set = new LinkedSortedSet<String>();
		LinkedSortedSet<String> otherSet = new LinkedSortedSet<String>();
		set.addAll(values);
		otherSet.addAll(values);

		boolean equals = set.equals(otherSet);

		assertTrue(equals);
	}

	@Test
	public void shouldHaveSameHashcodeGivenSameContents() {
		List<String> values = Arrays.asList("One", "Two");
		LinkedSortedSet<String> set = new LinkedSortedSet<String>();
		LinkedSortedSet<String> otherSet = new LinkedSortedSet<String>();
		set.addAll(values);
		otherSet.addAll(values);

		int distance = set.hashCode() - otherSet.hashCode();

		assertEquals(0, distance);
	}

	@Test
	public void shouldNotEqualGivenDifferentContents() {
		LinkedSortedSet<String> set = new LinkedSortedSet<String>();
		LinkedSortedSet<String> otherSet = new LinkedSortedSet<String>();
		set.addAll(Arrays.asList("One", "Two"));
		otherSet.addAll(Arrays.asList("Three", "Four"));

		boolean equals = set.equals(otherSet);

		assertFalse(equals);
	}
}
