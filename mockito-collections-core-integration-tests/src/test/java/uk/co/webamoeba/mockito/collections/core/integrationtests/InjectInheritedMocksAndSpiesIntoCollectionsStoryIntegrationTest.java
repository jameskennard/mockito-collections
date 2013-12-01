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
import org.mockito.Spy;

import uk.co.webamoeba.mockito.collections.MockitoCollections;
import uk.co.webamoeba.mockito.collections.core.integrationtests.support.ClassWithCollectionOfCollaborators;

public class InjectInheritedMocksAndSpiesIntoCollectionsStoryIntegrationTest implements
		InjectInheritedMocksAndSpiesIntoCollectionsStory {

	@Test
	public void testInheritsSomeMocksAndSpiesFromAParentClass() {
		// Given
		final ClassWithCollectionOfCollaborators outerCUT = new ClassWithCollectionOfCollaborators();
		final EventListener outerCollaborator2 = mock(EventListener.class);
		final EventListener outerCollaborator4 = mock(EventListener.class);
		@SuppressWarnings("unused")
		ParentTest exampleTest = new ParentTest() {

			@InjectMocks
			private ClassWithCollectionOfCollaborators cut = outerCUT;

			@Mock
			private EventListener collaborator2 = outerCollaborator2;

			@Spy
			private EventListener collaborator4 = outerCollaborator4;

		};

		// When
		MockitoCollections.initialise(exampleTest);

		// Then
		assertNotNull(outerCUT.getCollaborators());
		assertEquals(4, outerCUT.getCollaborators().size());
		Iterator<EventListener> iterator = outerCUT.getCollaborators().iterator();
		assertSame(exampleTest.collaborator1, iterator.next());
		assertSame(exampleTest.collaborator3, iterator.next());
		assertSame(outerCollaborator2, iterator.next());
		assertSame(outerCollaborator4, iterator.next());
	}

	@Test
	public void testInheritsSomeMocksAndSpiesWithTheSameNamesFromAParentTestClass() {
		// Given
		final ClassWithCollectionOfCollaborators outerCUT = new ClassWithCollectionOfCollaborators();
		final EventListener outerCollaborator1 = mock(EventListener.class);
		final EventListener outerCollaborator3 = mock(EventListener.class);
		@SuppressWarnings("unused")
		ParentTest exampleTest = new ParentTest() {

			@InjectMocks
			private ClassWithCollectionOfCollaborators cut = outerCUT;

			@Mock
			private EventListener collaborator1 = outerCollaborator1;

			@Spy
			private EventListener collaborator3 = outerCollaborator3;

		};

		// When
		MockitoCollections.initialise(exampleTest);

		// Then
		assertNotNull(outerCUT.getCollaborators());
		assertEquals(4, outerCUT.getCollaborators().size());
		Iterator<EventListener> iterator = outerCUT.getCollaborators().iterator();
		assertSame(exampleTest.collaborator1, iterator.next());
		assertSame(exampleTest.collaborator3, iterator.next());
		assertSame(outerCollaborator1, iterator.next());
		assertSame(outerCollaborator3, iterator.next());
	}

	private abstract class ParentTest {

		@Mock
		private EventListener collaborator1 = mock(EventListener.class);

		@Spy
		private EventListener collaborator3 = mock(EventListener.class);
	}

}
