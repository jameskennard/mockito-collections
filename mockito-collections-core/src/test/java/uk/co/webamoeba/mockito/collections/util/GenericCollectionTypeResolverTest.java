package uk.co.webamoeba.mockito.collections.util;

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

	private void shouldGetCollectionFieldType(String fieldName, Class<?> expectedType) {
		// Given
		Field field = getField(fieldName);

		// When
		Class<?> type = resolver.getCollectionFieldType(field);

		// Then
		assertSame(expectedType, type);
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
	private class FieldProvider {

		/**
		 * Expect {@link String}
		 */
		@SuppressWarnings("unused")
		public Collection<String> collection;

		/**
		 * Expect <code>null</code>
		 */
		@SuppressWarnings({ "unused", "rawtypes" })
		public Collection rawCollection;

		/**
		 * Expect {@link String[]}
		 */
		@SuppressWarnings("unused")
		public Collection<String[]> arrayCollection;

		/**
		 * Expect <code>null</code>
		 */
		@SuppressWarnings("unused")
		public Collection<?> wildcardCollection;

		/**
		 * Expect {@link InputStream}
		 */
		@SuppressWarnings("unused")
		public Collection<? extends InputStream> wildcardUpperBoundCollection;

		/**
		 * Expect {@link FileInputStream}
		 * <p>
		 * TODO is this the behaviour we want when given a lower bound?
		 * 
		 * @see <a href="http://docs.oracle.com/javase/tutorial/extra/generics/morefun.html">Generics, bound
		 *      wildcards</a>
		 */
		@SuppressWarnings("unused")
		public Collection<? super FileInputStream> wildcardLowerBoundCollection;

		/**
		 * Expect {@link Integer} as per {@link HardCodedTypeCollection}
		 */
		@SuppressWarnings("unused")
		public HardCodedTypeCollection hardCodedTypeCollection;

		/**
		 * Expect {@link Boolean}
		 */
		@SuppressWarnings("unused")
		public TypeCollection<Boolean> typeCollection;

		/**
		 * Expect {@link Integer} as per {@link HardCodedTypeCollection} (should ignore {@link String} generic declared
		 * here)
		 */
		@SuppressWarnings("unused")
		public ExtendedHardCodedTypeCollection<String> extendedHardCodedTypeCollection;

		/**
		 * Expect {@link Integer} as per {@link HardCodedTypeCollection} (should ignore {@link Reader} generic declared
		 * as part of {@link DoubleExtendedHardCodedTypeCollection})
		 */
		@SuppressWarnings("unused")
		public DoubleExtendedHardCodedTypeCollection doubleExtendedHardCodedTypeCollection;

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
