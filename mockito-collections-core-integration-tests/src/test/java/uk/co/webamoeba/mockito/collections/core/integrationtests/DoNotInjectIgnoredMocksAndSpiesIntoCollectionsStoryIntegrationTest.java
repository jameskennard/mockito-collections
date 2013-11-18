package uk.co.webamoeba.mockito.collections.core.integrationtests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import java.util.EventListener;
import java.util.Iterator;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

import uk.co.webamoeba.mockito.collections.MockitoCollections;
import uk.co.webamoeba.mockito.collections.annotation.IgnoreForCollections;
import uk.co.webamoeba.mockito.collections.core.integrationtests.support.ClassWithCollectionOfCollaborators;

public class DoNotInjectIgnoredMocksAndSpiesIntoCollectionsStoryIntegrationTest implements
		DoNotInjectIgnoredMocksAndSpiesIntoCollectionsStory {

	@Test
	@SuppressWarnings("unused")
	public void allMocksAndSpiesHaveTheIgnoreForCollectionsAnnotation() {
		// Given
		final ClassWithCollectionOfCollaborators outterCUT = new ClassWithCollectionOfCollaborators();
		final EventListener outerMock = mock(EventListener.class);
		final EventListener outerSpy = spy(new ConcreteEventListener());
		Object exampleTest = new Object() {

			@InjectMocks
			ClassWithCollectionOfCollaborators cut = outterCUT;

			@Mock
			@IgnoreForCollections
			private EventListener mock = outerMock;

			@Spy
			@IgnoreForCollections
			private EventListener spy = outerSpy;

		};

		// When
		MockitoCollections.initialise(exampleTest);

		// Then
		assertNull(outterCUT.getCollaborators());
	}

	@Test
	@SuppressWarnings("unused")
	public void someMocksAndSomeSpiesHaveTheIgnoreForCollectionsAnnotation() {
		// Given
		final ClassWithCollectionOfCollaborators outterCUT = new ClassWithCollectionOfCollaborators();
		final EventListener outerMock = mock(EventListener.class);
		final EventListener outerIgnoredMock = mock(EventListener.class);
		final EventListener outerSpy = spy(new ConcreteEventListener());
		final EventListener outerIgnoredSpy = spy(new ConcreteEventListener());
		Object exampleTest = new Object() {

			@InjectMocks
			ClassWithCollectionOfCollaborators cut = outterCUT;

			@Mock
			private EventListener mock = outerMock;

			@Mock
			@IgnoreForCollections
			private EventListener ignoredMock = outerIgnoredMock;

			@Spy
			private EventListener spy = outerSpy;

			@Spy
			@IgnoreForCollections
			private EventListener ignoredSpy = outerIgnoredSpy;

		};

		// When
		MockitoCollections.initialise(exampleTest);

		// Then
		assertNotNull(outterCUT.getCollaborators());
		assertEquals(2, outterCUT.getCollaborators().size());
		Iterator<EventListener> iterator = outterCUT.getCollaborators().iterator();
		assertSame(outerMock, iterator.next());
		assertSame(outerSpy, iterator.next());
	}

	private class ConcreteEventListener implements EventListener {
	}

}
