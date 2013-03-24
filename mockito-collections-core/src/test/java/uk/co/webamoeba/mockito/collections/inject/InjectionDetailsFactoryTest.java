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

import uk.co.webamoeba.mockito.collections.annotation.IgnoreInjectable;
import uk.co.webamoeba.mockito.collections.annotation.InjectCollections;
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
		assertEquals(0, injectionDetails.getInjectables().size());
		assertEquals(0, injectionDetails.getInjectableCollectionSet().size());
		assertEquals(1, injectionDetails.getInjectCollections().size());
		assertTrue(injectionDetails.getInjectCollections().contains(object.injectCollections1));
	}

	@Test
	public void shouldCreateInjectionDetailsGivenInjectCollections() {
		// Given
		ClassWithAnnnotations object = new ClassWithAnnnotations();
		Field injectCollectionsField = getField(object.getClass(), "injectCollections2");
		given(annotatedFieldRetriever.getAnnotatedFields(object.getClass(), InjectCollections.class)).willReturn(
				Collections.singleton(injectCollectionsField));

		// When
		InjectionDetails injectionDetails = factory.createInjectionDetails(object);

		// Then
		assertEquals(0, injectionDetails.getInjectables().size());
		assertEquals(0, injectionDetails.getInjectableCollectionSet().size());
		assertEquals(1, injectionDetails.getInjectCollections().size());
		assertTrue(injectionDetails.getInjectCollections().contains(object.injectCollections2));
	}

	@Test
	public void shouldCreateInjectionDetailsGivenMock() {
		// Given
		ClassWithAnnnotations object = new ClassWithAnnnotations();
		Field injectableField = getField(object.getClass(), "injectable1");
		given(annotatedFieldRetriever.getAnnotatedFields(object.getClass(), Mock.class)).willReturn(
				Collections.singleton(injectableField));

		// When
		InjectionDetails injectionDetails = factory.createInjectionDetails(object);

		// Then
		assertEquals(0, injectionDetails.getInjectCollections().size());
		assertEquals(0, injectionDetails.getInjectableCollectionSet().size());
		assertEquals(1, injectionDetails.getInjectables().size());
		assertTrue(injectionDetails.getInjectables().contains(object.injectable1));
	}

	@Test
	public void shouldCreateInjectionDetailsGivenInheritance() {
		// Given
		ExtendedClassWithAnnnotations object = new ExtendedClassWithAnnnotations();
		Field injectableField1 = getField(ExtendedClassWithAnnnotations.class, "injectable1");
		Field injectableField2 = getField(ExtendedClassWithAnnnotations.class, "injectable2");
		Field inheritedInjectableField1 = getField(ClassWithAnnnotations.class, "injectable1");
		Field inheritedInjectableField2 = getField(ClassWithAnnnotations.class, "injectable2");
		given(annotatedFieldRetriever.getAnnotatedFields(object.getClass(), Mock.class)).willReturn(
				new HashSet<Field>(Arrays.asList(injectableField1, inheritedInjectableField1, injectableField2,
						inheritedInjectableField2)));

		// When
		InjectionDetails injectionDetails = factory.createInjectionDetails(object);

		// Then
		assertEquals(0, injectionDetails.getInjectCollections().size());
		assertEquals(0, injectionDetails.getInjectableCollectionSet().size());
		assertEquals(4, injectionDetails.getInjectables().size());
		Iterator<Object> iterator = injectionDetails.getInjectables().iterator();
		assertSame(object.getInjectable1(), iterator.next());
		assertSame(object.getInjectable2(), iterator.next());
		assertSame(object.getExtendedInjectable1(), iterator.next());
		assertSame(object.getExtendedInjectable2(), iterator.next());
	}

	@Test
	public void shouldCreateInjectionDetailsGivenIgnoredInjectables() {
		// Given
		ClassWithAnnnotations object = new ClassWithAnnnotations();
		Field injectableField1 = getField(object.getClass(), "injectable1");
		given(annotatedFieldRetriever.getAnnotatedFields(object.getClass(), Mock.class)).willReturn(
				new HashSet<Field>(Collections.singleton(injectableField1)));
		given(annotatedFieldRetriever.getAnnotatedFields(object.getClass(), IgnoreInjectable.class)).willReturn(
				Collections.singleton(injectableField1));

		// When
		InjectionDetails injectionDetails = factory.createInjectionDetails(object);

		// Then
		assertEquals(0, injectionDetails.getInjectCollections().size());
		assertEquals(0, injectionDetails.getInjectableCollectionSet().size());
		assertEquals(0, injectionDetails.getInjectables().size());
	}

	@Test
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void shouldCreateInjectionDetailsGivenInjectableCollection() {
		// Given
		ClassWithAnnnotations object = new ClassWithAnnnotations();
		Field collectionOfMocksField = getField(object.getClass(), "injectableCollection1");
		given(
				annotatedFieldRetriever.getAnnotatedFields(object.getClass(),
						uk.co.webamoeba.mockito.collections.annotation.CollectionOfMocks.class)).willReturn(
				Collections.singleton(collectionOfMocksField));
		Class typeOfElements = EventListener.class;
		given(genericCollectionTypeResolver.getCollectionFieldType(collectionOfMocksField)).willReturn(typeOfElements);

		// When
		InjectionDetails injectionDetails = factory.createInjectionDetails(object);

		// Then
		assertEquals(0, injectionDetails.getInjectables().size());
		assertEquals(0, injectionDetails.getInjectCollections().size());
		assertEquals(1, injectionDetails.getInjectableCollectionSet().size());
		InjectableCollection<Collection<Object>, Object> injectableCollection = injectionDetails
				.getInjectableCollectionSet().iterator().next();
		assertSame(object.injectableCollection1, injectableCollection.getValue());
		assertEquals(List.class, injectableCollection.getTypeOfCollection());
		assertEquals(typeOfElements, injectableCollection.getTypeOfElements());
	}

	@Test(expected = MockitoCollectionsException.class)
	public void shouldFailToCreateInjectionDetailsGivenInjectableCollectionOnNonCollection() {
		// Given
		ClassWithAnnnotations object = new ClassWithAnnnotations();
		Field collectionOfMocksField = getField(object.getClass(), "injectable1");
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

		private Object injectCollections2 = mock(Object.class);

		protected Object injectable1 = mock(InputStream.class);

		public Object injectable2 = mock(InputStream.class);

		@SuppressWarnings("unchecked")
		private List<EventListener> injectableCollection1 = mock(List.class);

		public Object getInjectable1() {
			return injectable1;
		}

		public Object getInjectable2() {
			return injectable2;
		}

	}

	private class ExtendedClassWithAnnnotations extends ClassWithAnnnotations {

		protected Object injectable1 = mock(InputStream.class);

		public Object injectable2 = mock(InputStream.class);

		public Object getExtendedInjectable1() {
			return injectable1;
		}

		public Object getExtendedInjectable2() {
			return injectable2;
		}

	}
}
