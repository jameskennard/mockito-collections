package uk.co.webamoeba.mockito.collections.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

import java.lang.reflect.Field;
import java.util.Collection;

import org.junit.Test;

import uk.co.webamoeba.mockito.collections.exception.MockitoCollectionsException;

/**
 * @author James Kennard
 */
public class FieldValueMutatorTest {

	private final FieldValueMutatorTestSupport support = new FieldValueMutatorTestSupport();

	@Test
	public void shouldSetGivenPublicField() {
		// Given
		Field field = getField("publicString");
		FieldValueMutator mutator = new FieldValueMutator(support, field);
		Object value = "Some New Value";

		// When
		mutator.mutateTo(value);

		// Then
		assertEquals(value, support.publicString);
		assertFalse(field.isAccessible());
	}

	@Test
	public void shouldSetGivenPublicAccesibleField() {
		// Given
		Field field = getField("publicString");
		field.setAccessible(true);
		FieldValueMutator mutator = new FieldValueMutator(support, field);
		Object value = "Some New Value";

		// When
		mutator.mutateTo(value);

		// Then
		assertEquals(value, support.publicString);
		assertTrue(field.isAccessible());
	}

	@Test
	public void shouldSetGivenPrivateField() {
		// Given
		Field field = getField("privateCollection");
		FieldValueMutator mutator = new FieldValueMutator(support, field);
		Object value = mock(Collection.class);

		// When
		mutator.mutateTo(value);

		// Then
		assertEquals(value, support.getPrivateCollection());
		assertFalse(field.isAccessible());
	}

	@Test
	public void shouldFailToSetGivenIncompatibleType() {
		// Given
		FieldValueMutator mutator = new FieldValueMutator(support, getField("privateCollection"));
		Object value = 100L;

		try {
			// When
			mutator.mutateTo(value);

			// Then
			fail();
		} catch (MockitoCollectionsException e) {
			assertTrue(e.getMessage().contains("incompatible type"));
		}
	}

	private Field getField(String fieldName) {
		for (Field field : support.getClass().getDeclaredFields()) {
			if (field.getName().equals(fieldName)) {
				return field;
			}
		}
		return null;
	}
}