package uk.co.webamoeba.mockito.collections.core.integrationtests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import java.util.Collection;
import java.util.EventListener;
import java.util.EventListenerProxy;
import java.util.Iterator;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

import uk.co.webamoeba.mockito.collections.MockitoCollections;
import uk.co.webamoeba.mockito.collections.core.integrationtests.support.ClassWithCollectionOfCollaborators;
import uk.co.webamoeba.mockito.collections.core.integrationtests.support.ConcreteEventListener;

public class InjectMocksAndSpiesIntoCollectionsStoryIntegrationTest implements InjectMocksAndSpiesIntoCollectionsStory {

	@Test
	public void testHasMocksAndSpiesOfExactType() {
		// Given
		final ClassWithCollectionOfConcreteEventListener outerObjectUnderTest = new ClassWithCollectionOfConcreteEventListener();
		final EventListener outerCollaborator1 = mock(ConcreteEventListener.class);
		final EventListener outerCollaborator2 = spy(new ConcreteEventListener());
		final EventListener outerCollaborator3 = mock(ConcreteEventListener.class);
		final EventListener outerCollaborator4 = spy(new ConcreteEventListener());

		@SuppressWarnings("unused")
		Object exampleTest = new Object() {

			@InjectMocks
			private ClassWithCollectionOfConcreteEventListener objectUnderTest = outerObjectUnderTest;

			@Mock
			private EventListener collaborator1 = outerCollaborator1;

			@Spy
			private EventListener collaborator2 = outerCollaborator2;

			@Mock
			private EventListener collaborator3 = outerCollaborator3;

			@Spy
			private EventListener collaborator4 = outerCollaborator4;
		};

		// When
		MockitoCollections.initialise(exampleTest);

		// Then
		assertEquals(4, outerObjectUnderTest.getCollaborators().size());
		Iterator<ConcreteEventListener> iterator = outerObjectUnderTest.getCollaborators().iterator();
		assertSame(outerCollaborator1, iterator.next());
		assertSame(outerCollaborator2, iterator.next());
		assertSame(outerCollaborator3, iterator.next());
		assertSame(outerCollaborator4, iterator.next());

	}

	@Test
	public void testHasMocksAndSpiesOfSubType() {
		// Given
		final ClassWithCollectionOfCollaborators outerObjectUnderTest = new ClassWithCollectionOfCollaborators();
		final ConcreteEventListener outerCollaborator1 = spy(new ConcreteEventListener());
		final EventListenerProxy outerCollaborator2 = mock(EventListenerProxy.class);

		@SuppressWarnings("unused")
		Object exampleTest = new Object() {

			@InjectMocks
			private ClassWithCollectionOfCollaborators objectUnderTest = outerObjectUnderTest;

			@Spy
			private ConcreteEventListener collaborator1 = outerCollaborator1;

			@Mock
			private EventListenerProxy collaborator2 = outerCollaborator2;

		};

		// When
		MockitoCollections.initialise(exampleTest);

		// Then
		assertEquals(2, outerObjectUnderTest.getCollaborators().size());
		Iterator<EventListener> iterator = outerObjectUnderTest.getCollaborators().iterator();
		assertSame(outerCollaborator1, iterator.next());
		assertSame(outerCollaborator2, iterator.next());
	}

	@Test
	public void testHasMocksAndSpiesOfDifferentType() {
		// Given
		final ClassWithCollectionOfCollaborators outerObjectUnderTest = new ClassWithCollectionOfCollaborators();
		final Object outerCollaborator1 = mock(Object.class);
		final Object outerCollaborator2 = spy(new Object());

		@SuppressWarnings("unused")
		Object exampleTest = new Object() {

			@InjectMocks
			private ClassWithCollectionOfCollaborators objectUnderTest = outerObjectUnderTest;

			@Mock
			private Object collaborator1 = outerCollaborator1;

			@Spy
			private Object collaborator2 = outerCollaborator2;

		};

		// When
		MockitoCollections.initialise(exampleTest);

		// Then
		assertNull(outerObjectUnderTest.getCollaborators());
	}

	private class ClassWithCollectionOfConcreteEventListener {

		private Collection<ConcreteEventListener> collaborators;

		public Collection<ConcreteEventListener> getCollaborators() {
			return collaborators;
		}

	}
}
