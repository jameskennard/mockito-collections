package uk.co.webamoeba.mockito.collections.core.integrationtests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;

import java.util.EventListener;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import uk.co.webamoeba.mockito.collections.MockitoCollections;
import uk.co.webamoeba.mockito.collections.annotation.IgnoreForCollections;
import uk.co.webamoeba.mockito.collections.core.integrationtests.support.ClassWithCollectionOfCollaborators;

public class DoNotInjectIgnoredMocksIntoCollectionsStoryIntegrationTest implements
		DoNotInjectIgnoredMocksIntoCollectionsStory {

	@Test
	public void allMocksHaveTheIgnoreForCollectionsAnnotation() {
		// Given
		final ClassWithCollectionOfCollaborators outerObjectUnderTest = new ClassWithCollectionOfCollaborators();
		final EventListener outerCollaborator1 = mock(EventListener.class);
		final EventListener outerCollaborator2 = mock(EventListener.class);
		@SuppressWarnings("unused")
		Object exampleTest = new Object() {

			@InjectMocks
			ClassWithCollectionOfCollaborators objectUnderTest = outerObjectUnderTest;

			@Mock
			@IgnoreForCollections
			private EventListener collaborator1 = outerCollaborator1;

			@Mock
			@IgnoreForCollections
			private EventListener collaborator2 = outerCollaborator2;

		};

		// When
		MockitoCollections.initialise(exampleTest);

		// Then
		assertNull(outerObjectUnderTest.getCollaborators());
	}

	public void someMocksHaveTheIgnoreMockForCollectionsAnnotation() {
		// Given
		final ClassWithCollectionOfCollaborators outerObjectUnderTest = new ClassWithCollectionOfCollaborators();
		final EventListener outerCollaborator1 = mock(EventListener.class);
		final EventListener outerCollaborator2 = mock(EventListener.class);
		@SuppressWarnings("unused")
		Object exampleTest = new Object() {

			@InjectMocks
			ClassWithCollectionOfCollaborators objectUnderTest = outerObjectUnderTest;

			@Mock
			private EventListener collaborator1 = outerCollaborator1;

			@Mock
			@IgnoreForCollections
			private EventListener collaborator2 = outerCollaborator2;

		};

		// When
		MockitoCollections.initialise(exampleTest);

		// Then
		assertNotNull(outerObjectUnderTest.getCollaborators());
		assertEquals(1, outerObjectUnderTest.getCollaborators().size());
		assertSame(outerCollaborator1, outerObjectUnderTest.getCollaborators().iterator().next());
	}

}
