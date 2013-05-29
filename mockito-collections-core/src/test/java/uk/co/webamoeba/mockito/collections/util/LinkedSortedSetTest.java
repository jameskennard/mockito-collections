package uk.co.webamoeba.mockito.collections.util;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.SortedSet;

import org.junit.Test;

/**
 * @author James Kennard
 */
public class LinkedSortedSetTest {

	@Test
	public void shouldAddGivenGivenElementAlreadyInSet() {
		// Given
		SortedSet<Float> set = new LinkedSortedSet<Float>();
		Float duplicate = 3.2f;
		List<Float> elements = Arrays.asList(0.0f, duplicate, 1.2f);
		set.addAll(elements);

		// When
		boolean changed = set.add(duplicate);

		// Then
		assertFalse(changed);
		assertEquals(set.size(), elements.size());
	}

	@Test
	public void shouldAddGivenElementsInOrder() {
		// Given
		SortedSet<Integer> set = new LinkedSortedSet<Integer>();
		Integer[] elements = new Integer[] { 100, 23, 50, 48, 12, 66, 47, 59, 98 };
		Boolean[] changed = new Boolean[elements.length];

		// When
		int i = 0;
		for (Integer integer : elements) {
			changed[i++] = set.add(integer);
		}

		// Then
		i = 0;
		for (Integer integer : set) {
			assertEquals(elements[i], integer);
			assertTrue(changed[i]);
			i++;
		}
		assertEquals(set.size(), elements.length);
	}

	@Test
	public void shouldAddAllGivenElementsInOrder() {
		// Given
		SortedSet<Character> set = new LinkedSortedSet<Character>();
		List<Character> elements = Arrays.asList('A', 'F', 'E', 'Z', 'S', 'P', 'T');

		// When
		boolean changed = set.addAll(elements);

		// Then
		assertTrue(changed);
		int i = 0;
		for (Character character : set) {
			assertEquals(elements.get(i++), character);
		}
		assertEquals(set.size(), elements.size());
	}

	@Test
	public void shouldAddGivenSetAlreadyContainsSomeElements() {
		// Given
		SortedSet<String> set = new LinkedSortedSet<String>();
		List<String> elements = Arrays.asList("One", "Two", "Three", "Four");
		set.addAll(elements);
		String element = "Five";

		// When
		boolean changed = set.add(element);

		// Then
		assertTrue(changed);
		String actualElement = null;
		for (Iterator<String> iterator = set.iterator(); iterator.hasNext();) {
			actualElement = iterator.next();
		}
		assertEquals(element, actualElement);
		assertEquals(set.size(), elements.size() + 1);
	}

	@Test
	public void shouldNextGivenIteratorHasNoElements() {
		// Given
		SortedSet<Long> set = new LinkedSortedSet<Long>();
		Iterator<Long> iterator = set.iterator();

		// When
		assert iterator.hasNext() == false;
		try {
			iterator.next();

			// Then
			fail();
		} catch (NoSuchElementException e) {
			assertNull(e.getMessage());
		}
	}

	@Test
	public void shouldNextGivenIteratorHasNoMoreElements() {
		// Given
		SortedSet<Boolean> set = new LinkedSortedSet<Boolean>();
		set.add(Boolean.TRUE);
		Iterator<Boolean> iterator = set.iterator();
		iterator.next();

		// When
		assert iterator.hasNext() == false;
		try {
			iterator.next();

			// Then
			fail();
		} catch (NoSuchElementException e) {
			assertNull(e.getMessage());
		}
	}

	@Test
	public void shouldRemoveGivenIterator() {
		// Given
		SortedSet<Integer> set = new LinkedSortedSet<Integer>();
		Integer elementOne = 16;
		Integer elementTwo = 12;
		Integer elementThree = 10;
		set.addAll(Arrays.asList(elementOne, elementTwo, elementThree));
		Iterator<Integer> iterator = set.iterator();
		iterator.next();
		iterator.next();

		// When
		iterator.remove();

		// Then
		assertEquals(2, set.size());
		Iterator<Integer> verificationIterator = set.iterator();
		assertEquals(elementOne, verificationIterator.next());
		assertEquals(elementThree, verificationIterator.next());
		assertFalse(verificationIterator.hasNext());
	}

	@Test
	public void shouldRemoveGivenIteratorAndIsFirstElement() {
		// Given
		SortedSet<Integer> set = new LinkedSortedSet<Integer>();
		Integer elementOne = 16;
		Integer elementTwo = 12;
		set.addAll(Arrays.asList(elementOne, elementTwo));
		Iterator<Integer> iterator = set.iterator();
		iterator.next();

		// When
		iterator.remove();

		// Then
		assertEquals(1, set.size());
		Iterator<Integer> verificationIterator = set.iterator();
		assertEquals(elementTwo, verificationIterator.next());
		assertFalse(verificationIterator.hasNext());
	}

	@Test
	public void shouldRemoveGivenIteratorAndIsLastElement() {
		// Given
		SortedSet<Long> set = new LinkedSortedSet<Long>();
		Long elementOne = 3454L;
		Long elementTwo = 2311L;
		set.addAll(Arrays.asList(elementOne, elementTwo));
		Iterator<Long> iterator = set.iterator();
		iterator.next();
		iterator.next();

		// When
		iterator.remove();

		// Then
		assertEquals(1, set.size());
		Iterator<Long> verificationIterator = set.iterator();
		assertEquals(elementOne, verificationIterator.next());
		assertFalse(verificationIterator.hasNext());
	}

	@Test
	public void shouldRemoveGivenIteratorAndIsOnlyElement() {
		// Given
		SortedSet<Boolean> set = new LinkedSortedSet<Boolean>();
		Boolean element = false;
		set.add(element);
		Iterator<Boolean> iterator = set.iterator();
		iterator.next();

		// When
		iterator.remove();

		// Then
		assertEquals(0, set.size());
		assertFalse(set.iterator().hasNext());
	}

	@Test
	public void shouldClear() {
		// Given
		SortedSet<Character> set = new LinkedSortedSet<Character>();
		set.addAll(Arrays.asList('X', 'Y', 'Z'));

		// When
		set.clear();

		// Then
		assertEquals(0, set.size());
		assertTrue(set.isEmpty());
		assertFalse(set.iterator().hasNext());
	}

	@Test
	public void shouldContainsGivenDoesContainElement() {
		// Given
		SortedSet<Double> set = new LinkedSortedSet<Double>();
		Double element = 12.2d;
		set.addAll(Arrays.asList(0.1d, element, 0.01d));

		// When
		boolean contains = set.contains(element);

		// Then
		assertTrue(contains);
	}

	@Test
	public void shouldContainsGivenDoesNotContainElement() {
		// Given
		SortedSet<Character> set = new LinkedSortedSet<Character>();
		set.addAll(Arrays.asList('X', 'Y', 'Z'));

		// When
		boolean contains = set.contains('W');

		// Then
		assertFalse(contains);
	}

	@Test
	public void shouldContainsAllGivenDoesContainAll() {
		// Given
		SortedSet<Double> set = new LinkedSortedSet<Double>();
		Double elementOne = 0.1d;
		Double elementTwo = 12.2d;
		set.addAll(Arrays.asList(elementOne, elementTwo, 0.01d));

		// When
		boolean containsAll = set.containsAll(Arrays.asList(elementOne, elementTwo));

		// Then
		assertTrue(containsAll);
	}

	@Test
	public void shouldContainsAllGivenDoesNotContainAll() {
		// Given
		SortedSet<Double> set = new LinkedSortedSet<Double>();
		Double elementOne = 0.1d;
		Double elementTwo = 12.2d;
		set.addAll(Arrays.asList(elementOne, elementTwo, 0.01d));

		// When
		boolean containsAll = set.containsAll(Arrays.asList(elementOne, elementTwo, 3.1d));

		// Then
		assertFalse(containsAll);
	}

	@Test
	public void shouldContainsAllGivenDoesNotContainAny() {
		// Given
		SortedSet<Double> set = new LinkedSortedSet<Double>();
		set.addAll(Arrays.asList(3.2d, 1.2d, 0.01d));

		// When
		boolean containsAll = set.containsAll(Arrays.asList(6.5d, 1.3d, 3.1d));

		// Then
		assertFalse(containsAll);
	}

	@Test
	public void shouldComparator() {
		// Given
		LinkedSortedSet<Object> set = new LinkedSortedSet<Object>();

		// When
		try {
			set.comparator();

			// Then
			fail();
		} catch (UnsupportedOperationException e) {
			assertEquals("Mockito-Collections injected SortedSet does not support this method", e.getMessage());
		}
	}

	@Test
	public void shouldSubSet() {
		// Given
		LinkedSortedSet<Object> set = new LinkedSortedSet<Object>();

		// When
		try {
			set.subSet("From", "To");

			// Then
			fail();
		} catch (UnsupportedOperationException e) {
			assertEquals("Mockito-Collections injected SortedSet does not support this method", e.getMessage());
		}
	}

	@Test
	public void shouldHeadSet() {
		// Given
		LinkedSortedSet<Object> set = new LinkedSortedSet<Object>();

		// When
		try {
			set.headSet("To");

			// Then
			fail();
		} catch (UnsupportedOperationException e) {
			assertEquals("Mockito-Collections injected SortedSet does not support this method", e.getMessage());
		}
	}

	@Test
	public void shouldTailSet() {
		// Given
		LinkedSortedSet<Object> set = new LinkedSortedSet<Object>();

		// When
		try {
			set.tailSet("From");

			// Then
			fail();
		} catch (UnsupportedOperationException e) {
			assertEquals("Mockito-Collections injected SortedSet does not support this method", e.getMessage());
		}
	}

	@Test
	public void shouldGetFirst() {
		// Given
		LinkedSortedSet<Object> set = new LinkedSortedSet<Object>();
		String expectedFirst = "A";
		set.addAll(Arrays.<Object> asList(expectedFirst, 'b', 1, 0.0, 3L));

		// When
		Object first = set.first();

		// Then
		assertEquals(expectedFirst, first);
	}

	@Test
	public void shouldGetLast() {
		// Given
		LinkedSortedSet<Object> set = new LinkedSortedSet<Object>();
		String expectedLast = "Z";
		set.addAll(Arrays.<Object> asList('F', 0, null, -2L, expectedLast));

		// When
		Object last = set.last();

		// Then
		assertEquals(expectedLast, last);
	}

	@Test
	public void shouldGetAsArray() {
		// Given
		LinkedSortedSet<Object> set = new LinkedSortedSet<Object>();
		Object[] expectedObjects = new Object[] { 'E', 9, null, -1L, "W" };
		set.addAll(Arrays.<Object> asList(expectedObjects));

		// When
		Object[] array = set.toArray();

		// Then
		assertArrayEquals(expectedObjects, array);
	}

	@Test
	public void shouldGetAsArrayGivenExistingArray() {
		// Given
		LinkedSortedSet<String> set = new LinkedSortedSet<String>();
		String[] expectedObjects = new String[] { "M", "O", "C", "K" };
		set.addAll(Arrays.<String> asList(expectedObjects));

		// When
		String[] array = set.toArray(new String[0]);

		// Then
		assertArrayEquals(expectedObjects, array);
	}

	@Test
	public void shouldRemove() {
		// Given
		LinkedSortedSet<String> set = new LinkedSortedSet<String>();
		String[] expectedObjects = new String[] { "A", "B", "C", "D" };
		set.addAll(Arrays.<String> asList(expectedObjects));

		// When
		boolean removed = set.remove("C");

		// Then
		assertTrue(removed);
		assertArrayEquals(new Object[] { "A", "B", "D" }, set.toArray());
	}

	@Test
	public void shouldRemoveGivenNotPresent() {
		// Given
		LinkedSortedSet<String> set = new LinkedSortedSet<String>();
		String[] expectedObjects = new String[] { "A", "B", "C", "D" };
		set.addAll(Arrays.<String> asList(expectedObjects));

		// When
		boolean removed = set.remove("E");

		// Then
		assertFalse(removed);
		assertArrayEquals(new Object[] { "A", "B", "C", "D" }, set.toArray());
	}
}
