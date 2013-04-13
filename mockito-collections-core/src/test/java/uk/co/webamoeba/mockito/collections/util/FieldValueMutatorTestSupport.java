package uk.co.webamoeba.mockito.collections.util;

import java.util.Collection;

/**
 * @author James Kennard
 * @see FieldValueMutatorTest
 */
public class FieldValueMutatorTestSupport {

	public String publicString;

	private Collection<?> privateCollection;

	public Collection<?> getPrivateCollection() {
		return privateCollection;
	}
}
