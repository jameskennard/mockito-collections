package uk.co.webamoeba.mockito.collections;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.Collections;
import java.util.EventListener;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import uk.co.webamoeba.mockito.collections.util.GenericCollectionTypeResolver;

/**
 * @author James Kennard
 */
@RunWith(MockitoJUnitRunner.class)
@SuppressWarnings({ "unchecked", "rawtypes" })
public class CollectionInjectorTest {

	@InjectMocks
	private CollectionInjector injector;

	@Mock
	private CollectionFactory collectionFactory;

	@Mock
	private InjectableSelectionStrategy strategy;

	@Mock
	private GenericCollectionTypeResolver genericCollectionTypeResolver;

	@Before
	public void setup() {
		// Workaround so as we can mock this correctly in tests, without this will throw ClassCastException when
		// specifying willReturn (unless returning a LinkedList or parent type) because the default Mockito answer
		// returns an empty LinkedList. Would be nicer if there were an Answers type that always returned null.
		given(collectionFactory.createCollection(any(Class.class), any(Collection.class))).willReturn(null);
	}

	@Test
	public void shouldInjectGivenNullType() throws Exception {
		// Given
		InjectionDetails injectionDetails = mock(InjectionDetails.class);

		ClassWithPublicEventListenerCollection injectee = new ClassWithPublicEventListenerCollection();
		given(injectionDetails.getInjectees()).willReturn(Collections.<Object> singleton(injectee));

		Field field = getField("listeners", injectee);
		given(genericCollectionTypeResolver.getCollectionFieldType(field)).willReturn(null);

		// When
		injector.inject(injectionDetails);

		// Then
		assertNull(injectee.listeners);
		assertNull(injectee.eventListener);
		assertNull(injectee.iterator);
	}

	@Test
	public void shouldInjectGivenNoMatchingInjectables() throws Exception {
		// Given
		InjectionDetails injectionDetails = mock(InjectionDetails.class);

		ClassWithPublicEventListenerCollection injectee = new ClassWithPublicEventListenerCollection();
		given(injectionDetails.getInjectees()).willReturn(Collections.<Object> singleton(injectee));

		Field field = getField("listeners", injectee);
		given(genericCollectionTypeResolver.getCollectionFieldType(field)).willReturn((Class) EventListener.class);

		// When
		injector.inject(injectionDetails);

		// Then
		assertNull(injectee.listeners);
		assertNull(injectee.eventListener);
		assertNull(injectee.iterator);
	}

	@Test
	public void shouldInjectIntoPublicField() throws Exception {
		// Given
		InjectionDetails injectionDetails = mock(InjectionDetails.class);

		ClassWithPublicEventListenerCollection injectee = new ClassWithPublicEventListenerCollection();
		Set<Object> injectables = mock(Set.class);
		given(injectionDetails.getInjectees()).willReturn(Collections.<Object> singleton(injectee));
		given(injectionDetails.getInjectables()).willReturn(injectables);

		Set<EventListener> stragtegyInjectables = mock(Set.class);
		Class<EventListener> clazz = EventListener.class;
		given(strategy.getInjectables(injectables, clazz)).willReturn(stragtegyInjectables);

		Collection<Object> set = mock(Collection.class);
		given(collectionFactory.createCollection(Collection.class, stragtegyInjectables)).willReturn(set);

		Field field = getField("listeners", injectee);
		given(genericCollectionTypeResolver.getCollectionFieldType(field)).willReturn((Class) clazz);

		// When
		injector.inject(injectionDetails);

		// Then
		assertSame(set, injectee.listeners);
		assertNull(injectee.eventListener);
		assertNull(injectee.iterator);
	}

	@Test
	public void shouldInjectIntoPrivateField() throws Exception {
		// Given
		InjectionDetails injectionDetails = mock(InjectionDetails.class);

		ClassWithPrivateEventListenerCollection injectee = new ClassWithPrivateEventListenerCollection();
		Set<Object> injectables = mock(Set.class);
		given(injectionDetails.getInjectees()).willReturn(Collections.<Object> singleton(injectee));
		given(injectionDetails.getInjectables()).willReturn(injectables);

		Set<EventListener> stragtegyInjectables = mock(Set.class);
		Class<EventListener> clazz = EventListener.class;
		given(strategy.getInjectables(injectables, clazz)).willReturn(stragtegyInjectables);

		List<Object> list = mock(List.class);
		given(collectionFactory.createCollection(List.class, stragtegyInjectables)).willReturn(list);

		Field field = getField("listeners", injectee);
		given(genericCollectionTypeResolver.getCollectionFieldType(field)).willReturn((Class) clazz);

		// When
		injector.inject(injectionDetails);

		// Then
		assertSame(list, injectee.listeners);
	}

	/**
	 * Gets the {@link Field} of the specified name from the injectee. This method will only look for {@link Field
	 * Fields} that are declared in the class represented by this injectee. This includes public, protected, default
	 * (package) access, and private fields, but excludes inherited fields.
	 * 
	 * @param fieldName
	 *            Name of the field we want to retrieve
	 * @param injectee
	 *            Object from which we want to retrieve the {@link Field}.
	 * @return
	 * @throws NoSuchFieldException
	 *             Thrown when no matching {@link Field} can be found
	 */
	private Field getField(String fieldName, Object injectee) throws NoSuchFieldException {
		for (Field field : injectee.getClass().getDeclaredFields()) {
			if (field.getName().equals(fieldName)) {
				return field;
			}
		}
		throw new NoSuchFieldException("No such field " + fieldName + " exists on object of type "
				+ injectee.getClass());
	}

	@Test
	public void shouldInjectIntoPrivateSetField() throws Exception {
		// Given
		InjectionDetails injectionDetails = mock(InjectionDetails.class);

		ClassWithPrivateEventListenerSet injectee = new ClassWithPrivateEventListenerSet();
		Set<Object> injectables = mock(Set.class);
		given(injectionDetails.getInjectees()).willReturn(Collections.<Object> singleton(injectee));
		given(injectionDetails.getInjectables()).willReturn(injectables);

		Set<EventListener> stragtegyInjectables = mock(Set.class);
		Class<EventListener> clazz = EventListener.class;
		given(strategy.getInjectables(injectables, clazz)).willReturn(stragtegyInjectables);

		Set<Object> set = mock(Set.class);
		given(collectionFactory.createCollection(Set.class, stragtegyInjectables)).willReturn(set);

		Field field = getField("listeners", injectee);
		given(genericCollectionTypeResolver.getCollectionFieldType(field)).willReturn((Class) clazz);

		// When
		injector.inject(injectionDetails);

		// Then
		assertSame(set, injectee.listeners);
	}

	@Test
	public void shouldInjectIntoPrivateVectorField() throws Exception {
		// Given
		InjectionDetails injectionDetails = mock(InjectionDetails.class);

		ClassWithPrivateEventListenerVector injectee = new ClassWithPrivateEventListenerVector();
		Set<Object> injectables = mock(Set.class);
		given(injectionDetails.getInjectees()).willReturn(Collections.<Object> singleton(injectee));
		given(injectionDetails.getInjectables()).willReturn(injectables);

		Set<EventListener> stragtegyInjectables = mock(Set.class);
		Class<EventListener> clazz = EventListener.class;
		given(strategy.getInjectables(injectables, clazz)).willReturn(stragtegyInjectables);

		Vector<Object> vector = mock(Vector.class);
		given(collectionFactory.createCollection(Vector.class, stragtegyInjectables)).willReturn(vector);

		Field field = getField("listeners", injectee);
		given(genericCollectionTypeResolver.getCollectionFieldType(field)).willReturn((Class) clazz);

		// When
		injector.inject(injectionDetails);

		// Then
		assertSame(vector, injectee.listeners);
	}

	@Test
	public void shouldInjectIntoPrivateArrayField() throws Exception {
		// Given
		InjectionDetails injectionDetails = mock(InjectionDetails.class);

		ClassWithPrivateEventListenerArray injectee = new ClassWithPrivateEventListenerArray();
		Set<Object> injectables = mock(Set.class);
		given(injectionDetails.getInjectees()).willReturn(Collections.<Object> singleton(injectee));
		given(injectionDetails.getInjectables()).willReturn(injectables);

		Set<EventListener> stragtegyInjectables = mock(Set.class);
		EventListener[] eventListeners = { mock(EventListener.class) };
		given(strategy.getInjectables(injectables, EventListener.class)).willReturn(stragtegyInjectables);
		given(stragtegyInjectables.toArray()).willReturn(eventListeners);

		// When
		injector.inject(injectionDetails);

		// Then
		assertSame(eventListeners, injectee.listeners);
	}

	@Test
	public void shouldInjectGivenInjectableCollection() throws NoSuchFieldException {
		// Given
		InjectionDetails injectionDetails = mock(InjectionDetails.class);

		ClassWithPrivateEventListenerSet injectee = new ClassWithPrivateEventListenerSet();
		Set<Object> injectables = mock(Set.class);
		InjectableCollectionSet injectableCollectionSet = mock(InjectableCollectionSet.class);
		given(injectionDetails.getInjectees()).willReturn(Collections.<Object> singleton(injectee));
		given(injectionDetails.getInjectables()).willReturn(injectables);
		given(injectionDetails.getInjectableCollectionSet()).willReturn(injectableCollectionSet);

		Class<EventListener> clazz = EventListener.class;
		InjectableCollection injectableCollection = mock(InjectableCollection.class, RETURNS_DEEP_STUBS);
		Set<EventListener> eventListeners = new HashSet<EventListener>();
		given(injectableCollection.getValue()).willReturn(eventListeners);
		given(strategy.getInjectableCollection(injectableCollectionSet, Set.class, clazz)).willReturn(
				injectableCollection);

		Field field = getField("listeners", injectee);
		given(genericCollectionTypeResolver.getCollectionFieldType(field)).willReturn((Class) clazz);

		// When
		injector.inject(injectionDetails);

		// Then
		assertSame(eventListeners, injectee.listeners);
	}

	private class ClassWithPublicEventListenerCollection {

		public Collection<EventListener> listeners;

		/**
		 * A non {@link Collection} based field in order to verify this sort of field is ignored.
		 */
		public EventListener eventListener;

		/**
		 * A non {@link Collection} based {@link ParameterizedType} field in order to verify this sort of field is
		 * ignored.
		 */
		public Iterator<String> iterator;
	}

	private class ClassWithPrivateEventListenerCollection {

		private List<EventListener> listeners;
	}

	private class ClassWithPrivateEventListenerSet {

		private Set<EventListener> listeners;
	}

	private class ClassWithPrivateEventListenerVector {

		private Vector<EventListener> listeners;
	}

	private class ClassWithPrivateEventListenerArray {

		private EventListener[] listeners;
	}
}
