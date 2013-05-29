package uk.co.webamoeba.mockito.collections.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Test;

/**
 * @author James Kennard
 */
public class OrderedSetTest {

	@Test
	public void shouldConstruct() {
		// Given

		// When
		OrderedSet<String> set = new OrderedSet<String>();

		// Then
		assertTrue(set.isEmpty());
	}

	@Test
	public void shouldConstructGivenExistingCollection() {
		// Given
		Collection<String> orginalCollection = Arrays.asList("ABC", "GHI", "DEF", "JKL");

		// When
		OrderedSet<String> set = new OrderedSet<String>(orginalCollection);

		// Then
		assertTrue(orginalCollection.equals(Arrays.asList(set.toArray())));
	}

	@Test
	public void shouldAddGivenGivenElementAlreadyInSet() {
		// Given
		OrderedSet<Float> set = new OrderedSet<Float>();
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
		OrderedSet<Integer> set = new OrderedSet<Integer>();
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
		OrderedSet<Character> set = new OrderedSet<Character>();
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
		OrderedSet<String> set = new OrderedSet<String>();
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
		OrderedSet<Long> set = new OrderedSet<Long>();
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
		OrderedSet<Boolean> set = new OrderedSet<Boolean>();
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
	public void shouldRemove() {
		// Given
		OrderedSet<Integer> set = new OrderedSet<Integer>();
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
		OrderedSet<Integer> set = new OrderedSet<Integer>();
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
		OrderedSet<Long> set = new OrderedSet<Long>();
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
		OrderedSet<Boolean> set = new OrderedSet<Boolean>();
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
		OrderedSet<Character> set = new OrderedSet<Character>();
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
		OrderedSet<Double> set = new OrderedSet<Double>();
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
		OrderedSet<Character> set = new OrderedSet<Character>();
		set.addAll(Arrays.asList('X', 'Y', 'Z'));

		// When
		boolean contains = set.contains('W');

		// Then
		assertFalse(contains);
	}

	@Test
	public void shouldContainsAllGivenDoesContainAll() {
		// Given
		OrderedSet<Double> set = new OrderedSet<Double>();
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
		OrderedSet<Double> set = new OrderedSet<Double>();
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
		OrderedSet<Double> set = new OrderedSet<Double>();
		set.addAll(Arrays.asList(3.2d, 1.2d, 0.01d));

		// When
		boolean containsAll = set.containsAll(Arrays.asList(6.5d, 1.3d, 3.1d));

		// Then
		assertFalse(containsAll);
	}
}
