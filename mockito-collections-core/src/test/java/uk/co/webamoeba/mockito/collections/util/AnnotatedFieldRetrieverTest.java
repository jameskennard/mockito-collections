package uk.co.webamoeba.mockito.collections.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import uk.co.webamoeba.mockito.collections.annotation.CollectionOfMocks;
import uk.co.webamoeba.mockito.collections.annotation.InjectCollections;

/**
 * @author James Kennard
 */
@RunWith(MockitoJUnitRunner.class)
public class AnnotatedFieldRetrieverTest {

	@InjectMocks
	private AnnotatedFieldRetriever retriever;

	@Test
	public void shouldGetAnnotatedFieldsGivenPrivateField() {
		// Given
		Class<ClassWithAnnnotations> clazz = ClassWithAnnnotations.class;
		Class<? extends Annotation> annotationClass = InjectMocks.class;

		// When
		Set<Field> fields = retriever.getAnnotatedFields(clazz, annotationClass);

		// Then
		assertEquals(1, fields.size());
		assertTrue(fields.contains(getField(clazz, "privateAnnotatedField")));
	}

	@Test
	public void shouldGetAnnotatedFieldsGivenProtectedField() {
		// Given
		Class<ClassWithAnnnotations> clazz = ClassWithAnnnotations.class;
		Class<? extends Annotation> annotationClass = Mock.class;

		// When
		Set<Field> fields = retriever.getAnnotatedFields(clazz, annotationClass);

		// Then
		assertEquals(1, fields.size());
		assertTrue(fields.contains(getField(clazz, "protectedAnnotatedField")));
	}

	@Test
	public void shouldGetAnnotatedFieldsGivenPublicField() {
		// Given
		Class<ClassWithAnnnotations> clazz = ClassWithAnnnotations.class;
		Class<? extends Annotation> annotationClass = CollectionOfMocks.class;

		// When
		Set<Field> fields = retriever.getAnnotatedFields(clazz, annotationClass);

		// Then
		assertEquals(1, fields.size());
		assertTrue(fields.contains(getField(clazz, "publicAnnotatedField")));
	}

	@Test
	public void shouldGetAnnotatedFieldsGivenPackageField() {
		// Given
		Class<ClassWithAnnnotations> clazz = ClassWithAnnnotations.class;
		Class<? extends Annotation> annotationClass = InjectCollections.class;

		// When
		Set<Field> fields = retriever.getAnnotatedFields(clazz, annotationClass);

		// Then
		assertEquals(1, fields.size());
		assertTrue(fields.contains(getField(clazz, "defaultAnnotatedField")));
	}

	@Test
	public void shouldGetAnnotatedFieldsGivenExtendedClass() {
		// Given
		Class<ExtendedClassWithAnnnotations> clazz = ExtendedClassWithAnnnotations.class;
		Class<? extends Annotation> annotationClass = InjectMocks.class;

		// When
		Set<Field> fields = retriever.getAnnotatedFields(clazz, annotationClass);

		// Then
		assertEquals(2, fields.size());
		assertTrue(fields.contains(getField(clazz, "privateAnnotatedField")));
		assertTrue(fields.contains(getField(ClassWithAnnnotations.class, "privateAnnotatedField")));
	}

	private Field getField(Class<?> clazz, String name) {
		Field field;
		try {
			field = clazz.getDeclaredField(name);
		} catch (Exception e) {
			throw new IllegalArgumentException("No such field exists");
		}
		return field;
	}

	// suppressing unused warnings, in practice all fields are used via reflection
	@SuppressWarnings("unused")
	private class ClassWithAnnnotations {

		@InjectMocks
		private Object privateAnnotatedField;

		@Mock
		protected Object protectedAnnotatedField;

		@CollectionOfMocks
		public Object publicAnnotatedField;

		@InjectCollections
		Object defaultAnnotatedField;
	}

	// suppressing unused warnings, in practice all fields are used via reflection
	@SuppressWarnings("unused")
	private class ExtendedClassWithAnnnotations extends ClassWithAnnnotations {

		@InjectMocks
		public Object privateAnnotatedField;
	}
}
