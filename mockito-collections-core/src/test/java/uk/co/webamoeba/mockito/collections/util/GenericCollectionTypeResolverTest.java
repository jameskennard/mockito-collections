package uk.co.webamoeba.mockito.collections.util;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.Field;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Unit test for {@link GenericCollectionTypeResolver}. Unlike {@link GenericCollectionTypeResolver} this class has not
 * been ported from Spring.
 * 
 * @author James Kennard
 */
@RunWith(MockitoJUnitRunner.class)
public class GenericCollectionTypeResolverTest {

	private GenericCollectionTypeResolver resolver = new GenericCollectionTypeResolver();

	@Test
	public void shouldGetCollectionFieldTypeGivenCollection() {
		shouldGetCollectionFieldType("collection", String.class);
	}

	@Test
	public void shouldGetCollectionFieldTypeGivenRawCollection() {
		shouldGetCollectionFieldType("rawCollection", null);
	}

	@Test
	public void shouldGetCollectionFieldTypeGivenArrayCollection() {
		shouldGetCollectionFieldType("arrayCollection", String[].class);
	}

	@Test
	public void shouldGetCollectionFieldTypeGivenWildcardCollection() {
		shouldGetCollectionFieldType("wildcardCollection", null);
	}

	@Test
	public void shouldGetCollectionFieldTypeGivenWildcardUpperBoundCollection() {
		shouldGetCollectionFieldType("wildcardUpperBoundCollection", InputStream.class);
	}

	@Test
	public void shouldGetCollectionFieldTypeGivenWildcardLowerBoundCollection() {
		shouldGetCollectionFieldType("wildcardLowerBoundCollection", FileInputStream.class);
	}

	@Test
	public void shouldGetCollectionFieldTypeGivenHardCodedTypeCollection() {
		shouldGetCollectionFieldType("hardCodedTypeCollection", Integer.class);
	}

	@Test
	public void shouldGetCollectionFieldTypeGivenTypeCollection() {
		shouldGetCollectionFieldType("typeCollection", Boolean.class);
	}

	@Test
	public void shouldGetCollectionFieldTypeGivenExtendedHardCodedTypeCollection() {
		shouldGetCollectionFieldType("extendedHardCodedTypeCollection", Integer.class);
	}

	@Test
	public void shouldGetCollectionFieldTypeGivenDoubleExtendedHardCodedTypeCollection() {
		shouldGetCollectionFieldType("doubleExtendedHardCodedTypeCollection", Integer.class);
	}

	@Test
	public void shouldGetCollectionFieldTypeGivenCollectionOfArrays() {
		shouldGetCollectionFieldType("collectionOfArrays", Integer[].class);
	}

	@Test
	public void shouldFailToGetCollectionFieldTypeGivenTypeVariable() {
		shouldFailToGetCollectionFieldType("typeVariable");
	}

	@Test
	public void shouldFailToGetCollectionFieldTypeGivenGenericArrayType() {
		shouldFailToGetCollectionFieldType("genericArrayType");
	}

	private void shouldGetCollectionFieldType(String fieldName, Class<?> expectedType) {
		// Given
		Field field = getField(fieldName);

		// When
		Class<?> type = resolver.getCollectionFieldType(field);

		// Then
		assertSame(expectedType, type);
	}

	private void shouldFailToGetCollectionFieldType(String fieldName) {
		// Given
		Field field = getField(fieldName);

		// When
		Class<?> type = resolver.getCollectionFieldType(field);

		// Then
		assertNull(type);
	}

	private Field getField(String name) {
		try {
			return FieldProvider.class.getField(name);
		} catch (Exception e) {
			throw new IllegalArgumentException("Failed to get Field " + name, e);
		}
	}

	/**
	 * Provides a bunch of fields declared such that we can resolve the types associated with the {@link Field}s for the
	 * purpose of this unit test.
	 * 
	 * @author James Kennard
	 */
	@SuppressWarnings("unused")
	private class FieldProvider<T> {

		/**
		 * Expect {@link String}
		 */
		public Collection<String> collection;

		/**
		 * Expect <code>null</code>
		 */
		@SuppressWarnings("rawtypes")
		public Collection rawCollection;

		/**
		 * Expect {@link String[]}
		 */
		public Collection<String[]> arrayCollection;

		/**
		 * Expect <code>null</code>
		 */
		public Collection<?> wildcardCollection;

		/**
		 * Expect {@link InputStream}
		 */
		public Collection<? extends InputStream> wildcardUpperBoundCollection;

		/**
		 * Expect {@link FileInputStream}
		 * <p>
		 * TODO is this the behaviour we want when given a lower bound?
		 * 
		 * @see <a href="http://docs.oracle.com/javase/tutorial/extra/generics/morefun.html">Generics, bound
		 *      wildcards</a>
		 */
		public Collection<? super FileInputStream> wildcardLowerBoundCollection;

		/**
		 * Expect {@link Integer} as per {@link HardCodedTypeCollection}
		 */
		public HardCodedTypeCollection hardCodedTypeCollection;

		/**
		 * Expect {@link Boolean}
		 */
		public TypeCollection<Boolean> typeCollection;

		/**
		 * Expect {@link Integer} as per {@link HardCodedTypeCollection} (should ignore {@link String} generic declared
		 * here)
		 */
		public ExtendedHardCodedTypeCollection<String> extendedHardCodedTypeCollection;

		/**
		 * Expect {@link Integer} as per {@link HardCodedTypeCollection} (should ignore {@link Reader} generic declared
		 * as part of {@link DoubleExtendedHardCodedTypeCollection})
		 */
		public DoubleExtendedHardCodedTypeCollection doubleExtendedHardCodedTypeCollection;

		public Collection<Integer[]> collectionOfArrays;

		public T typeVariable;

		public T[] genericArrayType;
	}

	private interface HardCodedTypeCollection extends Collection<Integer> {
	}

	private interface TypeCollection<T> extends Collection<T> {
	}

	private interface ExtendedHardCodedTypeCollection<T> extends HardCodedTypeCollection {
	}

	private interface DoubleExtendedHardCodedTypeCollection extends ExtendedHardCodedTypeCollection<Reader> {
	}
}
