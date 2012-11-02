package uk.co.webamoeba.mockito.collections.core.integrationtests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

import java.util.EventListener;
import java.util.Iterator;

import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import uk.co.webamoeba.mockito.collections.MockitoCollectionAnnotations;
import uk.co.webamoeba.mockito.collections.core.integrationtests.support.ClassWithCollectionOfCollaborators;

public class InjectCollectionsIntoAnObjectUnderTestWhereThereIsInheritanceStoryIntegrationTest implements
		InjectCollectionsIntoAnObjectUnderTestWhereThereIsInheritanceStory {

	@Test
	public void testClassInheritsSomeMocksFromAParentTestClass() {
		// Given
		final ClassWithCollectionOfCollaborators outerCUT = new ClassWithCollectionOfCollaborators();
		final EventListener outerCollaborator2 = mock(EventListener.class);
		final EventListener outerCollaborator4 = mock(EventListener.class);
		@SuppressWarnings("unused")
		BaseExampleTest exampleTest = new BaseExampleTest() {

			@InjectMocks
			private ClassWithCollectionOfCollaborators cut = outerCUT;

			@Mock
			private EventListener collaborator2 = outerCollaborator2;

			@Mock
			private EventListener collaborator4 = outerCollaborator4;

		};

		// When
		MockitoCollectionAnnotations.inject(exampleTest);

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
	@Ignore
	public void testClassInheritsSomeMocksFromAParentTestClassWithTheSameNames() {
		fail("TODO");
	}

	@Test
	@Ignore
	public void classOfObjectUnderTestInheritsCollectionOfCollaboratorsFromParentClass() {
		fail("TODO");
	}

	private abstract class BaseExampleTest {

		@Mock
		private EventListener collaborator1 = mock(EventListener.class);

		@Mock
		private EventListener collaborator3 = mock(EventListener.class);
	}

}
