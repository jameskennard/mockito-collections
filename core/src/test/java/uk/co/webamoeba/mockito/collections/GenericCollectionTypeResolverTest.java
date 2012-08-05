package uk.co.webamoeba.mockito.collections;

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
	// Given
	Field field = getField("collection");

	// When
	Class<?> type = resolver.getCollectionFieldType(field);

	// Then
	assertSame(String.class, type);
    }

    @Test
    public void shouldGetCollectionFieldTypeGivenRawCollection() {
	// Given
	Field field = getField("rawCollection");

	// When
	Class<?> type = resolver.getCollectionFieldType(field);

	// Then
	assertNull(type);
    }

    /**
     * TODO do we care about this scenario?
     */
    @Test
    public void shouldGetCollectionFieldTypeGivenArrayCollection() {
	// Given
	Field field = getField("arrayCollection");

	// When
	Class<?> type = resolver.getCollectionFieldType(field);

	// Then
	assertSame(String[].class, type);
    }

    @Test
    public void shouldGetCollectionFieldTypeGivenWildcardCollection() {
	// Given
	Field field = getField("wildcardCollection");

	// When
	Class<?> type = resolver.getCollectionFieldType(field);

	// Then
	assertNull(type);
    }

    @Test
    public void shouldGetCollectionFieldTypeGivenWildcardUpperBoundCollection() {
	// Given
	Field field = getField("wildcardUpperBoundCollection");

	// When
	Class<?> type = resolver.getCollectionFieldType(field);

	// Then
	assertSame(InputStream.class, type);
    }

    /**
     * TODO is this the behaviour we want when given a lower bound?
     * 
     * @see <a href="http://docs.oracle.com/javase/tutorial/extra/generics/morefun.html">Generics, bound wildcards</a>
     */
    @Test
    public void shouldGetCollectionFieldTypeGivenWildcardLowerBoundCollection() {
	// Given
	Field field = getField("wildcardLowerBoundCollection");

	// When
	Class<?> type = resolver.getCollectionFieldType(field);

	// Then
	assertSame(FileInputStream.class, type);
    }

    @Test
    public void shouldGetCollectionFieldTypeGivenHardCodedTypeCollection() {
	// Given
	Field field = getField("hardCodedTypeCollection");

	// When
	Class<?> type = resolver.getCollectionFieldType(field);

	// Then
	assertSame(Integer.class, type);
    }

    @Test
    public void shouldGetCollectionFieldTypeGivenTypeCollection() {
	// Given
	Field field = getField("typeCollection");

	// When
	Class<?> type = resolver.getCollectionFieldType(field);

	// Then
	assertSame(Boolean.class, type);
    }

    @Test
    public void shouldGetCollectionFieldTypeGivenExtendedHardCodedTypeCollection() {
	// Given
	Field field = getField("extendedHardCodedTypeCollection");

	// When
	Class<?> type = resolver.getCollectionFieldType(field);

	// Then
	assertSame(Integer.class, type);
    }

    @Test
    public void shouldGetCollectionFieldTypeGivenDoubleExtendedHardCodedTypeCollection() {
	// Given
	Field field = getField("doubleExtendedHardCodedTypeCollection");

	// When
	Class<?> type = resolver.getCollectionFieldType(field);

	// Then
	assertSame(Integer.class, type);
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
	public Collection<String> collection;

	/**
	 * Expect <code>null</code>
	 */
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
