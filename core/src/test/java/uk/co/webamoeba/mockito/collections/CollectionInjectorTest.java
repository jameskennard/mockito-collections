import static org.junit.Assert.assertSame;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Matchers.any;

import java.util.Collection;
import java.util.Collections;
import java.util.EventListener;
import java.util.Set;
import java.util.Vector;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CollectionInjectorTest {

	@InjectMocks
	private CollectionInjector injector;

	@Mock
	private CollectionFactory collectionFactory;

	@Mock
	private InjectableSelectionStrategy strategy;

	@Test
	public void shouldInjectIntoPublicField() {
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
		given(collectionFactory.createCollection(Collection.class)).willReturn(set);

		// When
		injector.inject(injectionDetails);

		// Then
		assertSame(set, injectee.listeners);
		verify(set).addAll(stragtegyInjectables);
	}

	@Test
	public void shouldInjectIntoPrivateField() {
		// Given
		InjectionDetails injectionDetails = mock(InjectionDetails.class);

		ClassWithPrivateEventListenerCollection injectee = new ClassWithPrivateEventListenerCollection();
		Set<Object> injectables = mock(Set.class);
		given(injectionDetails.getInjectees()).willReturn(Collections.<Object> singleton(injectee));
		given(injectionDetails.getInjectables()).willReturn(injectables);

		Set<EventListener> stragtegyInjectables = mock(Set.class);
		Class<EventListener> clazz = EventListener.class;
		given(strategy.getInjectables(injectables, clazz)).willReturn(stragtegyInjectables);

		Collection<Object> set = mock(Collection.class);
		given(collectionFactory.createCollection(Collection.class)).willReturn(set);

		// When
		injector.inject(injectionDetails);

		// Then
		assertSame(set, injectee.listeners);
		verify(set).addAll(stragtegyInjectables);
	}

	@Test
	public void shouldInjectIntoPrivateSetField() {
		// Given
		InjectionDetails injectionDetails = mock(InjectionDetails.class);

		ClassWithPrivateEventListenerSet injectee = new ClassWithPrivateEventListenerSet();
		Set<Object> injectables = mock(Set.class);
		given(injectionDetails.getInjectees()).willReturn(Collections.<Object> singleton(injectee));
		given(injectionDetails.getInjectables()).willReturn(injectables);

		Set<EventListener> stragtegyInjectables = mock(Set.class);
		Class<EventListener> clazz = EventListener.class;
		given(strategy.getInjectables(injectables, clazz)).willReturn(stragtegyInjectables);

		// FIXME for some reason cannot say Set.class, have to use any(Class.class)
		Set<Object> set = mock(Set.class);
		given(collectionFactory.createCollection(any(Class.class))).willReturn(set);

		// When
		injector.inject(injectionDetails);

		// Then
		assertSame(set, injectee.listeners);
		verify(set).addAll(stragtegyInjectables);
	}

	@Test
	public void shouldInjectIntoPrivateVectorField() {
		// Given
		InjectionDetails injectionDetails = mock(InjectionDetails.class);

		ClassWithPrivateEventListenerVector injectee = new ClassWithPrivateEventListenerVector();
		Set<Object> injectables = mock(Set.class);
		given(injectionDetails.getInjectees()).willReturn(Collections.<Object> singleton(injectee));
		given(injectionDetails.getInjectables()).willReturn(injectables);

		Set<EventListener> stragtegyInjectables = mock(Set.class);
		Class<EventListener> clazz = EventListener.class;
		given(strategy.getInjectables(injectables, clazz)).willReturn(stragtegyInjectables);

		// FIXME for some reason cannot say Set.class, have to use any(Class.class)
		Vector<Object> set = mock(Vector.class);
		given(collectionFactory.createCollection(any(Class.class))).willReturn(set);

		// When
		injector.inject(injectionDetails);

		// Then
		assertSame(set, injectee.listeners);
		verify(set).addAll(stragtegyInjectables);
	}

	@Test
	public void shouldInjectIntoPrivateComplexCollectionField() {
		// Given
		InjectionDetails injectionDetails = mock(InjectionDetails.class);

		ClassWithPrivateEventListenerComplexCollection injectee = new ClassWithPrivateEventListenerComplexCollection();
		Set<Object> injectables = mock(Set.class);
		given(injectionDetails.getInjectees()).willReturn(Collections.<Object> singleton(injectee));
		given(injectionDetails.getInjectables()).willReturn(injectables);

		Set<EventListener> stragtegyInjectables = mock(Set.class);
		Class<EventListener> clazz = EventListener.class;
		given(strategy.getInjectables(injectables, clazz)).willReturn(stragtegyInjectables);

		// FIXME for some reason cannot say Set.class, have to use any(Class.class)
		ComplexCollection<Object, Object, Object> set = mock(ComplexCollection.class);
		given(collectionFactory.createCollection(any(Class.class))).willReturn(set);

		// When
		injector.inject(injectionDetails);

		// Then
		assertSame(set, injectee.listeners);
		verify(set).addAll(stragtegyInjectables);
	}

	private class ClassWithPublicEventListenerCollection {
		public Collection<EventListener> listeners;
	}

	private class ClassWithPrivateEventListenerCollection {
		private Collection<EventListener> listeners;
	}

	private class ClassWithPrivateEventListenerSet {
		private Set<EventListener> listeners;
	}

	private class ClassWithPrivateEventListenerVector {
		private Vector<EventListener> listeners;
	}

	private class ClassWithPrivateEventListenerComplexCollection {
		private ComplexCollection<String, Integer, EventListener> listeners;
	}

	private interface ComplexCollection<A, B, T> extends Collection<T> {

	}
}
