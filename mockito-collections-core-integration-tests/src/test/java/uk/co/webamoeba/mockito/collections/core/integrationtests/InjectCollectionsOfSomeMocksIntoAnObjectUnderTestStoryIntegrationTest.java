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
import uk.co.webamoeba.mockito.collections.annotation.IgnoreMockForCollections;
import uk.co.webamoeba.mockito.collections.core.integrationtests.support.ClassWithCollectionOfCollaborators;

public class InjectCollectionsOfSomeMocksIntoAnObjectUnderTestStoryIntegrationTest implements
		InjectCollectionsOfSomeMocksIntoAnObjectUnderTestStory {

	@Test
	public void allMocksHaveTheIgnoreMockForCollectionsAnnotation() {
		// Given
		final ClassWithCollectionOfCollaborators outterCUT = new ClassWithCollectionOfCollaborators();
		final EventListener outerCollaborator1 = mock(EventListener.class);
		final EventListener outerCollaborator2 = mock(EventListener.class);
		@SuppressWarnings("unused")
		Object exampleTest = new Object() {

			@InjectMocks
			ClassWithCollectionOfCollaborators cut = outterCUT;

			@Mock
			@IgnoreMockForCollections
			private EventListener collaborator1 = outerCollaborator1;

			@Mock
			@IgnoreMockForCollections
			private EventListener collaborator2 = outerCollaborator2;

		};

		// When
		MockitoCollections.initialise(exampleTest);

		// Then
		assertNull(outterCUT.getCollaborators());
	}

	public void someMocksHaveTheIgnoreMockForCollectionsAnnotation() {
		// Given
		final ClassWithCollectionOfCollaborators outterCUT = new ClassWithCollectionOfCollaborators();
		final EventListener outerCollaborator1 = mock(EventListener.class);
		final EventListener outerCollaborator2 = mock(EventListener.class);
		@SuppressWarnings("unused")
		Object exampleTest = new Object() {

			@InjectMocks
			ClassWithCollectionOfCollaborators cut = outterCUT;

			@Mock
			private EventListener collaborator1 = outerCollaborator1;

			@Mock
			@IgnoreMockForCollections
			private EventListener collaborator2 = outerCollaborator2;

		};

		// When
		MockitoCollections.initialise(exampleTest);

		// Then
		assertNotNull(outterCUT.getCollaborators());
		assertEquals(1, outterCUT.getCollaborators().size());
		assertSame(outerCollaborator1, outterCUT.getCollaborators().iterator().next());
	}

}
