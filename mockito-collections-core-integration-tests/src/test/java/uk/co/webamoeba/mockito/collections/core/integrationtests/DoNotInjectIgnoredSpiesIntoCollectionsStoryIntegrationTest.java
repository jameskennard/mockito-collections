package uk.co.webamoeba.mockito.collections.core.integrationtests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;

import java.util.EventListener;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Spy;

import uk.co.webamoeba.mockito.collections.MockitoCollections;
import uk.co.webamoeba.mockito.collections.annotation.IgnoreForCollections;
import uk.co.webamoeba.mockito.collections.core.integrationtests.support.ClassWithCollectionOfCollaborators;

public class DoNotInjectIgnoredSpiesIntoCollectionsStoryIntegrationTest implements
		DoNotInjectIgnoredSpiesIntoCollectionsStory {

	@Test
	public void allSpiesHaveTheIgnoreInjectableAnnotation() {
		// Given
		final ClassWithCollectionOfCollaborators outterCUT = new ClassWithCollectionOfCollaborators();
		final EventListener outerCollaborator1 = mock(EventListener.class);
		final EventListener outerCollaborator2 = mock(EventListener.class);
		Object exampleTest = new Object() {

			@InjectMocks
			ClassWithCollectionOfCollaborators cut = outterCUT;

			@Spy
			@IgnoreForCollections
			private EventListener collaborator1 = outerCollaborator1;

			@Spy
			@IgnoreForCollections
			private EventListener collaborator2 = outerCollaborator2;

		};

		// When
		MockitoCollections.initialise(exampleTest);

		// Then
		assertNull(outterCUT.getCollaborators());
	}

	@Test
	public void someSpiesHaveTheIgnoreInjectableAnnotation() {
		// Given
		final ClassWithCollectionOfCollaborators outterCUT = new ClassWithCollectionOfCollaborators();
		final EventListener outerCollaborator1 = mock(EventListener.class);
		final EventListener outerCollaborator2 = mock(EventListener.class);
		Object exampleTest = new Object() {

			@InjectMocks
			ClassWithCollectionOfCollaborators cut = outterCUT;

			@Spy
			private EventListener collaborator1 = outerCollaborator1;

			@Spy
			@IgnoreForCollections
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
