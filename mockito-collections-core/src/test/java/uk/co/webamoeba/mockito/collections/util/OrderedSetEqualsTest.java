package uk.co.webamoeba.mockito.collections.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

import org.junit.Test;

public class OrderedSetEqualsTest {

	@Test
	public void shouldEqualGivenSelf() {
		OrderedSet<Object> orderedSet = new OrderedSet<Object>();
		boolean equals = orderedSet.equals(orderedSet);
		assertTrue(equals);
	}

	@Test
	public void shouldHaveSameHashCodeGivenSelf() {
		OrderedSet<Object> orderedSet = new OrderedSet<Object>();
		int delta = orderedSet.hashCode() - orderedSet.hashCode();
		assertEquals(0, delta);
	}

	@Test
	public void shouldEqualGivenSelfWithContents() {
		OrderedSet<Object> orderedSet = new OrderedSet<Object>(Arrays.<Object> asList("ABC", 1L));
		boolean equals = orderedSet.equals(orderedSet);
		assertTrue(equals);
	}

	@Test
	public void shouldHaveSameHashCodeGivenSelfWithContents() {
		OrderedSet<Object> orderedSet = new OrderedSet<Object>(Arrays.<Object> asList("JKL", 4L));
		int delta = orderedSet.hashCode() - orderedSet.hashCode();
		assertEquals(0, delta);
	}

	@Test
	public void shouldEqualGivenBothEmpty() {
		OrderedSet<Object> orderedSet1 = new OrderedSet<Object>();
		OrderedSet<Object> orderedSet2 = new OrderedSet<Object>();

		boolean equals = orderedSet1.equals(orderedSet2);

		assertTrue(equals);
	}

	@Test
	public void shouldHaveSameHashCodeGivenBothEmpty() {
		OrderedSet<Object> orderedSet1 = new OrderedSet<Object>();
		OrderedSet<Object> orderedSet2 = new OrderedSet<Object>();

		int delta = orderedSet1.hashCode() - orderedSet2.hashCode();

		assertEquals(0, delta);
	}

	@Test
	public void shouldEqualGivenSameContents() {
		List<Object> contents = Arrays.<Object> asList("DEF", 2L);
		OrderedSet<Object> orderedSet1 = new OrderedSet<Object>(contents);
		OrderedSet<Object> orderedSet2 = new OrderedSet<Object>(contents);

		boolean equals = orderedSet1.equals(orderedSet2);

		assertTrue(equals);
	}

	@Test
	public void shouldHaveSameHashCodeGivenSameContents() {
		List<Object> contents = Arrays.<Object> asList("DEF", 2L);
		OrderedSet<Object> orderedSet1 = new OrderedSet<Object>(contents);
		OrderedSet<Object> orderedSet2 = new OrderedSet<Object>(contents);

		int delta = orderedSet1.hashCode() - orderedSet2.hashCode();

		assertEquals(0, delta);
	}

	@Test
	public void shouldNotEqualGivenNull() {
		OrderedSet<Object> orderedSet = new OrderedSet<Object>();
		boolean equals = orderedSet.equals(null);
		assertFalse(equals);
	}

	@Test
	public void shouldNotEqualGivenDifferentClass() {
		OrderedSet<Object> orderedSet = new OrderedSet<Object>();
		Object object = new LinkedHashSet<Object>();

		boolean equals = orderedSet.equals(object);

		assertFalse(equals);
	}

	@Test
	public void shouldNotEqualGivenOneIsEmpty() {
		OrderedSet<Object> orderedSet1 = new OrderedSet<Object>(Arrays.<Object> asList("GHI", 3L));
		OrderedSet<Object> orderedSet2 = new OrderedSet<Object>();

		boolean equals = orderedSet1.equals(orderedSet2);

		assertFalse(equals);
	}
}
