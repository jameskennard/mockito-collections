package uk.co.webamoeba.mockito.collections.util;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.lang.reflect.Field;
import java.util.Collection;

import org.junit.Test;

import uk.co.webamoeba.mockito.collections.exception.MockitoCollectionsException;
import uk.co.webamoeba.mockito.collections.util.FieldValueMutator;

/**
 * @author James Kennard
 */
public class FieldValueMutatorTest {

    public String publicString;

    private Collection<?> privateCollection;

    @Test
    public void shouldSetGivenPublicField() {
	// Given
	FieldValueMutator mutator = new FieldValueMutator(this, getField("publicString"));
	Object value = "Some New Value";

	// When
	mutator.mutateTo(value);

	// Then
	assertEquals(value, publicString);
    }

    @Test
    public void shouldSetGivenPrivateField() {
	// Given
	FieldValueMutator mutator = new FieldValueMutator(this, getField("privateCollection"));
	Object value = mock(Collection.class);

	// When
	mutator.mutateTo(value);

	// Then
	assertEquals(value, privateCollection);
    }

    @Test(expected = MockitoCollectionsException.class)
    public void shouldFailToSetGivenIncompatibleType() {
	// Given
	FieldValueMutator mutator = new FieldValueMutator(this, getField("privateCollection"));
	Object value = 100L;

	// When
	mutator.mutateTo(value);

	// Then
	// Exception thrown
    }

    private Field getField(String fieldName) {
	for (Field field : getClass().getDeclaredFields()) {
	    if (field.getName().equals(fieldName)) {
		return field;
	    }
	}
	return null;
    }
}