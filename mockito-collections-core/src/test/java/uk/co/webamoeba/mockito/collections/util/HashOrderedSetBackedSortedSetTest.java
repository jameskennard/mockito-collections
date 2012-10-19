package uk.co.webamoeba.mockito.collections.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.SortedSet;

import org.junit.Test;

/**
 * @author James Kennard
 */
public class HashOrderedSetBackedSortedSetTest {

	@Test
	public void shouldAddGivenGivenElementAlreadyInSet() {
		// Given
		SortedSet<Float> set = new HashOrderedSetBackedSortedSet<Float>();
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
		SortedSet<Integer> set = new HashOrderedSetBackedSortedSet<Integer>();
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
		SortedSet<Character> set = new HashOrderedSetBackedSortedSet<Character>();
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
		SortedSet<String> set = new HashOrderedSetBackedSortedSet<String>();
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

	@Test(expected = NoSuchElementException.class)
	public void shouldNextGivenIteratorHasNoElements() {
		// Given
		SortedSet<Long> set = new HashOrderedSetBackedSortedSet<Long>();
		Iterator<Long> iterator = set.iterator();
		iterator.next();

		// When
		assert iterator.hasNext() == false;
		iterator.next();

		// Then
		// Exception Expected
	}

	@Test(expected = NoSuchElementException.class)
	public void shouldNextGivenIteratorHasNoMoreElements() {
		// Given
		SortedSet<Boolean> set = new HashOrderedSetBackedSortedSet<Boolean>();
		set.add(Boolean.TRUE);
		Iterator<Boolean> iterator = set.iterator();
		iterator.next();

		// When
		assert iterator.hasNext() == false;
		iterator.next();

		// Then
		// Exception Expected
	}

	@Test
	public void shouldRemove() {
		// Given
		SortedSet<Integer> set = new HashOrderedSetBackedSortedSet<Integer>();
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
	public void shouldRemoveGivenIsFirstElement() {
		// Given
		SortedSet<Integer> set = new HashOrderedSetBackedSortedSet<Integer>();
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
	public void shouldRemoveGivenIsLastElement() {
		// Given
		SortedSet<Long> set = new HashOrderedSetBackedSortedSet<Long>();
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
	public void shouldRemoveGivenIsOnlyElement() {
		// Given
		SortedSet<Boolean> set = new HashOrderedSetBackedSortedSet<Boolean>();
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
		SortedSet<Character> set = new HashOrderedSetBackedSortedSet<Character>();
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
		SortedSet<Double> set = new HashOrderedSetBackedSortedSet<Double>();
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
		SortedSet<Character> set = new HashOrderedSetBackedSortedSet<Character>();
		set.addAll(Arrays.asList('X', 'Y', 'Z'));

		// When
		boolean contains = set.contains('W');

		// Then
		assertFalse(contains);
	}

	@Test
	public void shouldContainsAllGivenDoesContainAll() {
		// Given
		SortedSet<Double> set = new HashOrderedSetBackedSortedSet<Double>();
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
		SortedSet<Double> set = new HashOrderedSetBackedSortedSet<Double>();
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
		SortedSet<Double> set = new HashOrderedSetBackedSortedSet<Double>();
		set.addAll(Arrays.asList(3.2d, 1.2d, 0.01d));

		// When
		boolean containsAll = set.containsAll(Arrays.asList(6.5d, 1.3d, 3.1d));

		// Then
		assertFalse(containsAll);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void shouldComparator() {
		new HashOrderedSetBackedSortedSet<Object>().comparator();
	}

	@Test(expected = UnsupportedOperationException.class)
	public void shouldSubSet() {
		new HashOrderedSetBackedSortedSet<Object>().subSet("From", "To");
	}

	@Test(expected = UnsupportedOperationException.class)
	public void shouldHeadSet() {
		new HashOrderedSetBackedSortedSet<Object>().headSet("To");
	}

	@Test(expected = UnsupportedOperationException.class)
	public void shouldTailSet() {
		new HashOrderedSetBackedSortedSet<Object>().tailSet("From");
	}

	@Test
	public void first() {
		// Given
		HashOrderedSetBackedSortedSet<Object> set = new HashOrderedSetBackedSortedSet<Object>();
		String expectedFirst = "A";
		set.addAll(Arrays.<Object> asList(expectedFirst, 'b', 1, 0.0, 3L));

		// When
		Object first = set.first();

		// Then
		assertEquals(expectedFirst, first);
	}

	@Test
	public void last() {
		// Given
		HashOrderedSetBackedSortedSet<Object> set = new HashOrderedSetBackedSortedSet<Object>();
		String expectedLast = "Z";
		set.addAll(Arrays.<Object> asList('F', 0, null, -2L, expectedLast));

		// When
		Object last = set.last();

		// Then
		assertEquals(expectedLast, last);
	}
}
