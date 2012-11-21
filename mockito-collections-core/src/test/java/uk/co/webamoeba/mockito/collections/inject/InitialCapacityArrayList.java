package uk.co.webamoeba.mockito.collections.inject;

import java.util.ArrayList;

public class InitialCapacityArrayList<E extends Object> extends ArrayList<E> {

	private static final long serialVersionUID = 1L;

	public InitialCapacityArrayList(int initialCapacity) {
		super(initialCapacity);
	}
}
