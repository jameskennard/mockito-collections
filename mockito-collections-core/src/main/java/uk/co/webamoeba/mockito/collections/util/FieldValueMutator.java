package uk.co.webamoeba.mockito.collections.util;

import java.lang.reflect.Field;

import uk.co.webamoeba.mockito.collections.exception.MockitoCollectionsException;

/**
 * Utility class that can be used to mutate the value of a field. <i>Based on Mockito's FieldSetter</i>.
 * 
 * @author James Kennard
 */
public class FieldValueMutator {

	private Object object;

	private Field field;

	public FieldValueMutator(Object object, Field field) {
		this.object = object;
		this.field = field;
	}

	public void mutateTo(Object value) {
		boolean wasAccessible = field.isAccessible();
		if (!wasAccessible) {
			field.setAccessible(true);
		}

		try {
			field.set(object, value);
		} catch (IllegalAccessException e) {
			throw new MockitoCollectionsException("Could not set field '" + field + "' on object '" + object
					+ "' with value: '" + value + "' because field is not accessible", e);
		} catch (IllegalArgumentException e) {
			throw new MockitoCollectionsException("Could not set field '" + field + "' on object '" + object
					+ "' with value '" + value + "' because value was of an incompatible type", e);
		}

		if (!wasAccessible) {
			try {
				field.setAccessible(false);
			} catch (Throwable t) {
				// Swallow it, we've done what we need to do
			}
		}
	}
}
