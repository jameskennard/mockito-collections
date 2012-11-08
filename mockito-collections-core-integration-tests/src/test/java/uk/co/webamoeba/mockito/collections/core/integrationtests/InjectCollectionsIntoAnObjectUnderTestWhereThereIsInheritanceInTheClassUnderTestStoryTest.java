package uk.co.webamoeba.mockito.collections.core.integrationtests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;

import java.util.EventListener;
import java.util.Iterator;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import uk.co.webamoeba.mockito.collections.MockitoCollectionAnnotations;
import uk.co.webamoeba.mockito.collections.core.integrationtests.support.ClassWithInheritedCollectionOfCollaborators;

public class InjectCollectionsIntoAnObjectUnderTestWhereThereIsInheritanceInTheClassUnderTestStoryTest implements
		InjectCollectionsIntoAnObjectUnderTestWhereThereIsInheritanceInTheClassUnderTestStory {

	@Test
	public void classOfObjectUnderTestInheritsCollectionOfCollaboratorsFromParentClass() {
		// Given
		final ClassWithInheritedCollectionOfCollaborators outerCUT = new ClassWithInheritedCollectionOfCollaborators();
		final EventListener outerCollaborator1 = mock(EventListener.class);
		final EventListener outerCollaborator3 = mock(EventListener.class);
		@SuppressWarnings("unused")
		Object exampleTest = new Object() {

			@InjectMocks
			private ClassWithInheritedCollectionOfCollaborators cut = outerCUT;

			@Mock
			private EventListener collaborator1 = outerCollaborator1;

			@Mock
			private EventListener collaborator2 = outerCollaborator3;

		};

		// When
		MockitoCollectionAnnotations.inject(exampleTest);

		// Then
		assertNotNull(outerCUT.getCollaborators());
		assertEquals(2, outerCUT.getCollaborators().size());
		Iterator<EventListener> iterator = outerCUT.getCollaborators().iterator();
		assertSame(outerCollaborator1, iterator.next());
		assertSame(outerCollaborator3, iterator.next());
	}

}
