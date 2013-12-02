package uk.co.webamoeba.mockito.collections.core.integrationtests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;

import java.util.EventListener;
import java.util.Iterator;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

import uk.co.webamoeba.mockito.collections.MockitoCollections;
import uk.co.webamoeba.mockito.collections.core.integrationtests.support.ClassWithInheritedCollectionOfCollaborators;

public class InjectMocksAndSpiesIntoInheritedCollectionsStoryIntegrationTest implements
		InjectMocksAndSpiesIntoInheritedCollectionsStory {

	@Test
	public void objectUnderTestInheritsCollectionOfCollaborators() {
		// Given
		final ClassWithInheritedCollectionOfCollaborators outerObjectUnderTest = new ClassWithInheritedCollectionOfCollaborators();
		final EventListener outerCollaborator1 = mock(EventListener.class);
		final EventListener outerCollaborator2 = mock(EventListener.class);
		@SuppressWarnings("unused")
		Object exampleTest = new Object() {

			@InjectMocks
			private ClassWithInheritedCollectionOfCollaborators objectUnderTest = outerObjectUnderTest;

			@Mock
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

}
