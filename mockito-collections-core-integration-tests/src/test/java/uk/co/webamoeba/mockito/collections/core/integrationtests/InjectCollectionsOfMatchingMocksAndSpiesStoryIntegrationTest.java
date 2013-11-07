package uk.co.webamoeba.mockito.collections.core.integrationtests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import java.util.EventListener;
import java.util.EventListenerProxy;
import java.util.Iterator;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

import uk.co.webamoeba.mockito.collections.MockitoCollections;
import uk.co.webamoeba.mockito.collections.core.integrationtests.support.ClassWithCollectionOfCollaborators;

public class InjectCollectionsOfMatchingMocksAndSpiesStoryIntegrationTest implements InjectCollectionsOfMatchingMocksAndSpiesStory{


	@Test
	public void testHasMocksAndSpiesOfSameType() {
		// Given
		final ClassWithCollectionOfCollaborators outerObjectUnderTest = new ClassWithCollectionOfCollaborators();
		final EventListener outerCollaborator1 = mock(EventListener.class);
		final EventListener outerCollaborator2 = spy(new EventListener(){});
		final EventListener outerCollaborator3 = mock(EventListener.class);
		final EventListener outerCollaborator4 = spy(new EventListener(){});

		Object exampleTest = new Object() {

			@InjectMocks
			ClassWithCollectionOfCollaborators objectUnderTest = outerObjectUnderTest;

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
		Iterator<EventListener> iterator = outerObjectUnderTest.getCollaborators().iterator();
		assertSame(outerCollaborator1, iterator.next());
		assertSame(outerCollaborator2, iterator.next());
		assertSame(outerCollaborator3, iterator.next());
		assertSame(outerCollaborator4, iterator.next());

	}

	@Test
	public void testHasMocksAndSpiesOfSubType() {
		// Given
		final ClassWithCollectionOfCollaborators outerObjectUnderTest = new ClassWithCollectionOfCollaborators();
		final EventListenerProxy outerCollaborator1 = spy(new EventListenerProxy(new EventListener(){}){});
		final EventListenerProxy outerCollaborator2 = mock(EventListenerProxy.class);

		Object exampleTest = new Object() {

			@InjectMocks
			ClassWithCollectionOfCollaborators objectUnderTest = outerObjectUnderTest;

			@Spy
			private EventListenerProxy collaborator1 = outerCollaborator1;

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

		Object exampleTest = new Object() {

			@InjectMocks
			ClassWithCollectionOfCollaborators objectUnderTest = outerObjectUnderTest;

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
}
