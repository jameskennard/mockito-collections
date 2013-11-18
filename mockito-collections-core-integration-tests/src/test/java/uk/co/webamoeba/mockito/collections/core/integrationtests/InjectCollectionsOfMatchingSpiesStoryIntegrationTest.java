package uk.co.webamoeba.mockito.collections.core.integrationtests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;

import java.util.EventListener;
import java.util.EventListenerProxy;
import java.util.Iterator;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Spy;

import uk.co.webamoeba.mockito.collections.MockitoCollections;
import uk.co.webamoeba.mockito.collections.core.integrationtests.support.ClassWithCollectionOfCollaborators;

public class InjectCollectionsOfMatchingSpiesStoryIntegrationTest implements InjectCollectionsOfMatchingSpiesStory{

	@Test
	public void testHasSpiesOfSameType() {
		// Given
		final ClassWithCollectionOfCollaborators outerObjectUnderTest = new ClassWithCollectionOfCollaborators();
		final EventListener outerCollaborator1 = mock(EventListener.class);
		final EventListener outerCollaborator2 = mock(EventListener.class);

		Object exampleTest = new Object() {

			@InjectMocks
			ClassWithCollectionOfCollaborators objectUnderTest = outerObjectUnderTest;

			@Spy
			private EventListener collaborator1 = outerCollaborator1;

			@Spy
			private EventListener collaborator2 = outerCollaborator2;

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
	public void testHasSpiesOfSubType() {
		// Given
		final ClassWithCollectionOfCollaborators outerObjectUnderTest = new ClassWithCollectionOfCollaborators();
		final EventListenerProxy outerCollaborator1 = mock(EventListenerProxy.class);
		final EventListenerProxy outerCollaborator2 = mock(EventListenerProxy.class);

		Object exampleTest = new Object() {

			@InjectMocks
			ClassWithCollectionOfCollaborators objectUnderTest = outerObjectUnderTest;

			@Spy
			private EventListenerProxy collaborator1 = outerCollaborator1;

			@Spy
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
	public void testHasSpiesOfDifferentType() {
		// Given
		final ClassWithCollectionOfCollaborators outerObjectUnderTest = new ClassWithCollectionOfCollaborators();
		final Object outerCollaborator1 = mock(Object.class);
		final Object outerCollaborator2 = mock(Object.class);

		Object exampleTest = new Object() {

			@InjectMocks
			ClassWithCollectionOfCollaborators objectUnderTest = outerObjectUnderTest;

			@Spy
			private Object collaborator1 = outerCollaborator1;

			@Spy
			private Object collaborator2 = outerCollaborator2;

		};

		// When
		MockitoCollections.initialise(exampleTest);

		// Then
		assertNull(outerObjectUnderTest.getCollaborators());
	}
}
