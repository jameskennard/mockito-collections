package uk.co.webamoeba.mockito.collections;

import static org.junit.Assert.assertSame;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Collections;
import java.util.EventListener;
import java.util.Set;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.util.MockUtil;
import org.mockito.runners.MockitoJUnitRunner;

import uk.co.webamoeba.mockito.collections.annotation.InjectableCollectionOfMocks;
import uk.co.webamoeba.mockito.collections.exception.MockitoCollectionsException;
import uk.co.webamoeba.mockito.collections.util.AnnotatedFieldRetriever;
import uk.co.webamoeba.mockito.collections.util.GenericCollectionTypeResolver;

@RunWith(MockitoJUnitRunner.class)
public class MockitoCollectionInitialiserTest {

    @InjectMocks
    private MockitoCollectionInitialiser initialiser;

    @Mock
    private AnnotatedFieldRetriever annotatedFieldRetriever;

    @Mock
    private GenericCollectionTypeResolver genericCollectionTypeResolver;

    @Mock
    private CollectionFactory collectionFactory;

    private MockUtil mockUtil = new MockUtil();

    @Test
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void shouldInitialise() {
	// Given
	ClassWithAnnnotations object = new ClassWithAnnnotations();
	Field field = getField(object.getClass(), "collection");
	Set<Field> fields = Collections.singleton(field);
	given(annotatedFieldRetriever.getAnnotatedFields(object.getClass(), InjectableCollectionOfMocks.class))
		.willReturn(fields);
	Class collectionType = EventListener.class;
	given(genericCollectionTypeResolver.getCollectionFieldType(field)).willReturn(collectionType);
	Collection collection = mock(Collection.class);
	given(collectionFactory.createCollection(eq(Collection.class), mocks(collectionType, 1)))
		.willReturn(collection);

	// When
	initialiser.initialise(object);

	// Then
	assertSame(collection, object.collection);
    }

    @Test
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void shouldInitialiseGivenAnnotationWithZeroMocks() {
	// Given
	ClassWithAnnnotations object = new ClassWithAnnnotations();
	Field field = getField(object.getClass(), "collectionWithZeroMocks");
	Set<Field> fields = Collections.singleton(field);
	given(annotatedFieldRetriever.getAnnotatedFields(object.getClass(), InjectableCollectionOfMocks.class))
		.willReturn(fields);
	Class collectionType = EventListener.class;
	given(genericCollectionTypeResolver.getCollectionFieldType(field)).willReturn(collectionType);
	Collection collection = mock(Collection.class);
	given(collectionFactory.createCollection(eq(Collection.class), mocks(collectionType, 0)))
		.willReturn(collection);

	// When
	initialiser.initialise(object);

	// Then
	assertSame(collection, object.collectionWithZeroMocks);
    }

    @Test(expected = MockitoCollectionsException.class)
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void shouldFailToInitialiseGivenAnnotationWithNegativeNumberOfMocks() {
	// Given
	ClassWithAnnnotations object = new ClassWithAnnnotations();
	Field field = getField(object.getClass(), "collectionWithNegativeNumberOfMocks");
	Set<Field> fields = Collections.singleton(field);
	given(annotatedFieldRetriever.getAnnotatedFields(object.getClass(), InjectableCollectionOfMocks.class))
		.willReturn(fields);
	Class collectionType = EventListener.class;
	given(genericCollectionTypeResolver.getCollectionFieldType(field)).willReturn(collectionType);
	Collection collection = mock(Collection.class);
	given(collectionFactory.createCollection(eq(Collection.class), mocks(collectionType, 0)))
		.willReturn(collection);

	// When
	initialiser.initialise(object);

	// Then
	// Exception Thrown
    }

    /**
     * @param collectionType
     *            The type of objects that must be in the {@link Collection} in order for the {@link Collection} to
     *            match
     * @param size
     *            Number of objects that must be in the {@link Collection} in order for the {@link Collection} to match
     * @return {@link Matcher} that will match any {@link Collection} of the specified size containing only {@link Mock}
     *         objects of the specified {@link Type}.
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private Collection mocks(final Class collectionType, final int size) {
	return argThat(new BaseMatcher<Collection>() {

	    public boolean matches(Object item) {
		Collection collection = (Collection) item;
		if (collection.size() != size) {
		    return false;
		}
		for (Object object : collection) {
		    if (!mockUtil.isMock(object)) {
			return false;
		    }
		    if (!collectionType.isAssignableFrom(object.getClass())) {
			return false;
		    }
		}
		return true;
	    }

	    public void describeTo(Description description) {
	    }
	});
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

    private class ClassWithAnnnotations {

	@InjectableCollectionOfMocks
	private Collection<EventListener> collection;

	@InjectableCollectionOfMocks(numberOfMocks = 0)
	private Collection<EventListener> collectionWithZeroMocks;

	@SuppressWarnings("unused")
	@InjectableCollectionOfMocks(numberOfMocks = -1)
	private Collection<EventListener> collectionWithNegativeNumberOfMocks;
    }
}
