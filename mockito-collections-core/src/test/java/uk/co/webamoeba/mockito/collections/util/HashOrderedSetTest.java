package uk.co.webamoeba.mockito.collections.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Test;

/**
 * @author James Kennard
 */
public class HashOrderedSetTest {

	@Test
	public void shouldConstructGivenExistingCollection() {
		// Given
		Collection<String> orginalCollection = Arrays.asList("ABC", "GHI", "DEF", "JKL");

		// When
		HashOrderedSet<String> set = new HashOrderedSet<String>(orginalCollection);

		// Then
		assertTrue(orginalCollection.equals(Arrays.asList(set.toArray())));
	}

	@Test
	public void shouldAddGivenGivenElementAlreadyInSet() {
		// Given
		HashOrderedSet<Float> set = new HashOrderedSet<Float>();
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
		HashOrderedSet<Integer> set = new HashOrderedSet<Integer>();
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
		HashOrderedSet<Character> set = new HashOrderedSet<Character>();
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
		HashOrderedSet<String> set = new HashOrderedSet<String>();
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
		HashOrderedSet<Long> set = new HashOrderedSet<Long>();
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
		HashOrderedSet<Boolean> set = new HashOrderedSet<Boolean>();
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
		HashOrderedSet<Integer> set = new HashOrderedSet<Integer>();
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
		HashOrderedSet<Integer> set = new HashOrderedSet<Integer>();
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
		HashOrderedSet<Long> set = new HashOrderedSet<Long>();
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
		HashOrderedSet<Boolean> set = new HashOrderedSet<Boolean>();
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
		HashOrderedSet<Character> set = new HashOrderedSet<Character>();
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
		HashOrderedSet<Double> set = new HashOrderedSet<Double>();
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
		HashOrderedSet<Character> set = new HashOrderedSet<Character>();
		set.addAll(Arrays.asList('X', 'Y', 'Z'));

		// When
		boolean contains = set.contains('W');

		// Then
		assertFalse(contains);
	}

	@Test
	public void shouldContainsAllGivenDoesContainAll() {
		// Given
		HashOrderedSet<Double> set = new HashOrderedSet<Double>();
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
		HashOrderedSet<Double> set = new HashOrderedSet<Double>();
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
		HashOrderedSet<Double> set = new HashOrderedSet<Double>();
		set.addAll(Arrays.asList(3.2d, 1.2d, 0.01d));

		// When
		boolean containsAll = set.containsAll(Arrays.asList(6.5d, 1.3d, 3.1d));

		// Then
		assertFalse(containsAll);
	}
}
