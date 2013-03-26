package uk.co.webamoeba.mockito.collections.inject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EventListener;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import uk.co.webamoeba.mockito.collections.annotation.IgnoreMockForCollections;
import uk.co.webamoeba.mockito.collections.exception.MockitoCollectionsException;
import uk.co.webamoeba.mockito.collections.util.AnnotatedFieldRetriever;
import uk.co.webamoeba.mockito.collections.util.GenericCollectionTypeResolver;

/**
 * @author James Kennard
 */
@RunWith(MockitoJUnitRunner.class)
public class InjectionDetailsFactoryTest {

	@InjectMocks
	private InjectionDetailsFactory factory;

	@Mock
	private AnnotatedFieldRetriever annotatedFieldRetriever;

	@Mock
	private GenericCollectionTypeResolver genericCollectionTypeResolver;

	@Test
	public void shouldCreateInjectionDetailsGivenInjectMocks() {
		// Given
		ClassWithAnnnotations object = new ClassWithAnnnotations();
		Field injectCollectionsField = getField(object.getClass(), "injectCollections1");
		given(annotatedFieldRetriever.getAnnotatedFields(object.getClass(), InjectMocks.class)).willReturn(
				Collections.singleton(injectCollectionsField));

		// When
		InjectionDetails injectionDetails = factory.createInjectionDetails(object);

		// Then
		assertEquals(0, injectionDetails.getMocks().size());
		assertEquals(0, injectionDetails.getInjectableCollectionSet().size());
		assertEquals(1, injectionDetails.getInjectCollections().size());
		assertTrue(injectionDetails.getInjectCollections().contains(object.injectCollections1));
	}

	@Test
	public void shouldCreateInjectionDetailsGivenMock() {
		// Given
		ClassWithAnnnotations object = new ClassWithAnnnotations();
		Field mockField = getField(object.getClass(), "mock1");
		given(annotatedFieldRetriever.getAnnotatedFields(object.getClass(), Mock.class)).willReturn(
				Collections.singleton(mockField));

		// When
		InjectionDetails injectionDetails = factory.createInjectionDetails(object);

		// Then
		assertEquals(0, injectionDetails.getInjectCollections().size());
		assertEquals(0, injectionDetails.getInjectableCollectionSet().size());
		assertEquals(1, injectionDetails.getMocks().size());
		assertTrue(injectionDetails.getMocks().contains(object.mock1));
	}

	@Test
	public void shouldCreateInjectionDetailsGivenInheritance() {
		// Given
		ExtendedClassWithAnnnotations object = new ExtendedClassWithAnnnotations();
		Field mockField1 = getField(ExtendedClassWithAnnnotations.class, "mock1");
		Field mockField2 = getField(ExtendedClassWithAnnnotations.class, "mock2");
		Field inheritedInjectableField1 = getField(ClassWithAnnnotations.class, "mock1");
		Field inheritedInjectableField2 = getField(ClassWithAnnnotations.class, "mock2");
		given(annotatedFieldRetriever.getAnnotatedFields(object.getClass(), Mock.class)).willReturn(
				new HashSet<Field>(Arrays.asList(mockField1, inheritedInjectableField1, mockField2,
						inheritedInjectableField2)));

		// When
		InjectionDetails injectionDetails = factory.createInjectionDetails(object);

		// Then
		assertEquals(0, injectionDetails.getInjectCollections().size());
		assertEquals(0, injectionDetails.getInjectableCollectionSet().size());
		assertEquals(4, injectionDetails.getMocks().size());
		Iterator<Object> iterator = injectionDetails.getMocks().iterator();
		assertSame(object.getInjectable1(), iterator.next());
		assertSame(object.getInjectable2(), iterator.next());
		assertSame(object.getExtendedInjectable1(), iterator.next());
		assertSame(object.getExtendedInjectable2(), iterator.next());
	}

	@Test
	public void shouldCreateInjectionDetailsGivenIgnoredInjectables() {
		// Given
		ClassWithAnnnotations object = new ClassWithAnnnotations();
		Field mockField1 = getField(object.getClass(), "mock1");
		given(annotatedFieldRetriever.getAnnotatedFields(object.getClass(), Mock.class)).willReturn(
				new HashSet<Field>(Collections.singleton(mockField1)));
		given(annotatedFieldRetriever.getAnnotatedFields(object.getClass(), IgnoreMockForCollections.class))
				.willReturn(Collections.singleton(mockField1));

		// When
		InjectionDetails injectionDetails = factory.createInjectionDetails(object);

		// Then
		assertEquals(0, injectionDetails.getInjectCollections().size());
		assertEquals(0, injectionDetails.getInjectableCollectionSet().size());
		assertEquals(0, injectionDetails.getMocks().size());
	}

	@Test
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void shouldCreateInjectionDetailsGivenInjectableCollection() {
		// Given
		ClassWithAnnnotations object = new ClassWithAnnnotations();
		Field collectionOfMocksField = getField(object.getClass(), "mocksField1");
		given(
				annotatedFieldRetriever.getAnnotatedFields(object.getClass(),
						uk.co.webamoeba.mockito.collections.annotation.CollectionOfMocks.class)).willReturn(
				Collections.singleton(collectionOfMocksField));
		Class typeOfElements = EventListener.class;
		given(genericCollectionTypeResolver.getCollectionFieldType(collectionOfMocksField)).willReturn(typeOfElements);

		// When
		InjectionDetails injectionDetails = factory.createInjectionDetails(object);

		// Then
		assertEquals(0, injectionDetails.getMocks().size());
		assertEquals(0, injectionDetails.getInjectCollections().size());
		assertEquals(1, injectionDetails.getInjectableCollectionSet().size());
		CollectionOfMocksField<Collection<Object>, Object> mocksField = injectionDetails.getInjectableCollectionSet()
				.iterator().next();
		assertSame(object.mocksField1, mocksField.getValue());
		assertEquals(List.class, mocksField.getTypeOfCollection());
		assertEquals(typeOfElements, mocksField.getTypeOfElements());
	}

	@Test(expected = MockitoCollectionsException.class)
	public void shouldFailToCreateInjectionDetailsGivenInjectableCollectionOnNonCollection() {
		// Given
		ClassWithAnnnotations object = new ClassWithAnnnotations();
		Field collectionOfMocksField = getField(object.getClass(), "mock1");
		given(
				annotatedFieldRetriever.getAnnotatedFields(object.getClass(),
						uk.co.webamoeba.mockito.collections.annotation.CollectionOfMocks.class)).willReturn(
				Collections.singleton(collectionOfMocksField));

		// When
		factory.createInjectionDetails(object);

		// Then
		// Exception Thrown
	}

	// FIXME add tests to deal with null values in fields

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

		Object injectCollections1 = mock(Object.class);

		protected Object mock1 = mock(InputStream.class);

		public Object mock2 = mock(InputStream.class);

		@SuppressWarnings("unchecked")
		private List<EventListener> mocksField1 = mock(List.class);

		public Object getInjectable1() {
			return mock1;
		}

		public Object getInjectable2() {
			return mock2;
		}

	}

	private class ExtendedClassWithAnnnotations extends ClassWithAnnnotations {

		protected Object mock1 = mock(InputStream.class);

		public Object mock2 = mock(InputStream.class);

		public Object getExtendedInjectable1() {
			return mock1;
		}

		public Object getExtendedInjectable2() {
			return mock2;
		}

	}
}
