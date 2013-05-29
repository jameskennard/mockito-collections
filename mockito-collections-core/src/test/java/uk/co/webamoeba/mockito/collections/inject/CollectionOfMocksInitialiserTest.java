package uk.co.webamoeba.mockito.collections.inject;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Collections;
import java.util.EventListener;
import java.util.Iterator;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import uk.co.webamoeba.mockito.collections.annotation.CollectionOfMocks;
import uk.co.webamoeba.mockito.collections.exception.MockitoCollectionsException;
import uk.co.webamoeba.mockito.collections.util.AnnotatedFieldRetriever;
import uk.co.webamoeba.mockito.collections.util.GenericCollectionTypeResolver;
import uk.co.webamoeba.mockito.collections.util.OrderedSet;

@RunWith(MockitoJUnitRunner.class)
public class CollectionOfMocksInitialiserTest {

	@InjectMocks
	private CollectionOfMocksInitialiser initialiser;

	@Mock
	private AnnotatedFieldRetriever annotatedFieldRetriever;

	@Mock
	private GenericCollectionTypeResolver genericCollectionTypeResolver;

	@Mock
	private CollectionFactory collectionFactory;

	@Mock
	private MockStrategy mockStrategy;

	@Test
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void shouldInitialise() {
		// Given
		ClassWithAnnnotations object = new ClassWithAnnnotations();
		Field field = getField(object.getClass(), "collection");
		Set<Field> fields = Collections.singleton(field);
		given(annotatedFieldRetriever.getAnnotatedFields(object.getClass(), CollectionOfMocks.class))
				.willReturn(fields);
		Class collectionType = EventListener.class;
		given(genericCollectionTypeResolver.getCollectionFieldType(field)).willReturn(collectionType);
		EventListener mockEventListener = mock(EventListener.class);
		given(mockStrategy.createMock(EventListener.class)).willReturn(mockEventListener);
		Collection collection = mock(Collection.class);
		given(
				collectionFactory.createCollection(eq(Collection.class),
						eq(new OrderedSet(Collections.singleton(mockEventListener))))).willReturn(collection);

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
		given(annotatedFieldRetriever.getAnnotatedFields(object.getClass(), CollectionOfMocks.class))
				.willReturn(fields);
		Class collectionType = EventListener.class;
		given(genericCollectionTypeResolver.getCollectionFieldType(field)).willReturn(collectionType);
		Collection collection = mock(Collection.class);
		given(collectionFactory.createCollection(Collection.class, new OrderedSet())).willReturn(collection);

		// When
		initialiser.initialise(object);

		// Then
		assertSame(collection, object.collectionWithZeroMocks);
	}

	@Test
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void shouldFailToInitialiseGivenAnnotationWithNegativeNumberOfMocks() {
		// Given
		ClassWithAnnnotations object = new ClassWithAnnnotations();
		Field field = getField(object.getClass(), "collectionWithNegativeNumberOfMocks");
		Set<Field> fields = Collections.singleton(field);
		given(annotatedFieldRetriever.getAnnotatedFields(object.getClass(), CollectionOfMocks.class))
				.willReturn(fields);
		Class collectionType = EventListener.class;
		given(genericCollectionTypeResolver.getCollectionFieldType(field)).willReturn(collectionType);

		// When
		MockitoCollectionsException exception = initialiseAndMockitoCollectionsExceptionThrown(object);

		// Then
		assertTrue(exception.getMessage().contains("Unexpected numberOfMocks"));
	}

	@Test
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void shouldFailToInitialiseGivenAnnotatedFieldNotCollection() {
		// Given
		ClassWithAnnnotations object = new ClassWithAnnnotations();
		Field field = getField(object.getClass(), "notCollection");
		Set<Field> fields = Collections.singleton(field);
		given(annotatedFieldRetriever.getAnnotatedFields(object.getClass(), CollectionOfMocks.class))
				.willReturn(fields);
		Class collectionType = EventListener.class;
		given(genericCollectionTypeResolver.getCollectionFieldType(field)).willReturn(collectionType);

		// When
		MockitoCollectionsException exception = initialiseAndMockitoCollectionsExceptionThrown(object);

		// Then
		assertTrue(exception.getMessage().contains("must be a Collection"));
	}

	@Test
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void shouldFailToInitialiseGivenAnnotatedFieldNotCollectionButIsParameterizedType() {
		// Given
		ClassWithAnnnotations object = new ClassWithAnnnotations();
		Field field = getField(object.getClass(), "notCollectionButIsParameterizedType");
		Set<Field> fields = Collections.singleton(field);
		given(annotatedFieldRetriever.getAnnotatedFields(object.getClass(), CollectionOfMocks.class))
				.willReturn(fields);
		Class collectionType = EventListener.class;
		given(genericCollectionTypeResolver.getCollectionFieldType(field)).willReturn(collectionType);

		// When
		MockitoCollectionsException exception = initialiseAndMockitoCollectionsExceptionThrown(object);

		// Then
		assertTrue(exception.getMessage().contains("must be a Collection"));
	}

	@Test
	public void shouldFailToInitialiseGivenAnnotatedFieldIsCollectionButHasNoGenerics() {
		// Given
		ClassWithAnnnotations object = new ClassWithAnnnotations();
		Field field = getField(object.getClass(), "collectionButHasNoGenerics");
		Set<Field> fields = Collections.singleton(field);
		given(annotatedFieldRetriever.getAnnotatedFields(object.getClass(), CollectionOfMocks.class))
				.willReturn(fields);
		given(genericCollectionTypeResolver.getCollectionFieldType(field)).willReturn(null);

		// When
		MockitoCollectionsException exception = initialiseAndMockitoCollectionsExceptionThrown(object);

		// Then
		assertTrue(exception.getMessage().contains("must be a Collection with Generics"));
	}

	private MockitoCollectionsException initialiseAndMockitoCollectionsExceptionThrown(Object object) {
		try {
			initialiser.initialise(object);
		} catch (MockitoCollectionsException e) {
			return e;
		}
		return null;
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

	@SuppressWarnings("unused")
	private class ClassWithAnnnotations {

		@CollectionOfMocks
		private Collection<EventListener> collection;

		@CollectionOfMocks(numberOfMocks = 0)
		private Collection<EventListener> collectionWithZeroMocks;

		@CollectionOfMocks
		@SuppressWarnings("rawtypes")
		private Collection collectionButHasNoGenerics;

		@CollectionOfMocks
		private InputStream notCollection;

		@CollectionOfMocks
		private Iterator<String> notCollectionButIsParameterizedType;

		@CollectionOfMocks(numberOfMocks = -1)
		private Collection<EventListener> collectionWithNegativeNumberOfMocks;
	}
}
