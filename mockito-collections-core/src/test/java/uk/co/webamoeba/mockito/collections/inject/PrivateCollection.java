package uk.co.webamoeba.mockito.collections.inject;

import java.util.HashSet;

/**
 * @see CollectionFactoryTest
 */
public class PrivateCollection<E extends Object> extends HashSet<E> {

	private static final long serialVersionUID = 1L;

	private PrivateCollection() {
		super();
	}
}
