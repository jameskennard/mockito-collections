package uk.co.webamoeba.mockito.collections.util;

public class HashOrderedSetBackedSortedSetEqualsHashCodeTest extends EqualsHashCodeTestCase {

	@Override
	protected Object createInstance() throws Exception {
		HashOrderedSetBackedSortedSet<String> set = new HashOrderedSetBackedSortedSet<String>();
		set.add("A");
		return set;
	}

	@Override
	protected Object createNotEqualInstance() throws Exception {
		HashOrderedSetBackedSortedSet<String> set = new HashOrderedSetBackedSortedSet<String>();
		set.add("B");
		return set;
	}

}
