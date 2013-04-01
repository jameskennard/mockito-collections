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

import uk.co.webamoeba.mockito.collections.MockitoCollections;
import uk.co.webamoeba.mockito.collections.core.integrationtests.support.ClassWithConcreteCollectionOfCollaborators;
import uk.co.webamoeba.mockito.collections.core.integrationtests.support.ClassWithConcreteCollectionWithInitialCapacityConstructorOfCollaborators;

public class InjectConcreteCollectionImplementationsOfMocksIntoAnObjectUnderTestStoryIntegrationTest implements
		InjectConcreteCollectionImplementationsOfMocksIntoAnObjectUnderTestStory {

	@Test
	public void classOfObjectUnderTestDeclaresAConcreteCollectionWithADefaultConstructorOfCollaborators() {
		// Given
		final ClassWithConcreteCollectionOfCollaborators outterCUT = new ClassWithConcreteCollectionOfCollaborators();
		BaseExampleTest exampleTest = new BaseExampleTest() {

			@InjectMocks
			@SuppressWarnings("unused")
			private ClassWithConcreteCollectionOfCollaborators cut = outterCUT;

		};

		// When
		MockitoCollections.initialise(exampleTest);

		// Then
		assertNotNull(outterCUT.getCollaborators());
		assertEquals(2, outterCUT.getCollaborators().size());
		Iterator<EventListener> iterator = outterCUT.getCollaborators().iterator();
		assertSame(exampleTest.collaborator1, iterator.next());
		assertSame(exampleTest.collaborator2, iterator.next());
	}

	@Test
	public void classOfObjectUnderTestDeclaresAConcreteCollectionWithAnInitialCapacityConstructorOfCollaborators() {
		// Given
		final ClassWithConcreteCollectionWithInitialCapacityConstructorOfCollaborators outterCUT = new ClassWithConcreteCollectionWithInitialCapacityConstructorOfCollaborators();
		BaseExampleTest exampleTest = new BaseExampleTest() {

			@InjectMocks
			@SuppressWarnings("unused")
			private ClassWithConcreteCollectionWithInitialCapacityConstructorOfCollaborators cut = outterCUT;

		};

		// When
		MockitoCollections.initialise(exampleTest);

		// Then
		assertNotNull(outterCUT.getCollaborators());
		assertEquals(2, outterCUT.getCollaborators().size());
		Iterator<EventListener> iterator = outterCUT.getCollaborators().iterator();
		assertSame(exampleTest.collaborator1, iterator.next());
		assertSame(exampleTest.collaborator2, iterator.next());

	}

	private abstract class BaseExampleTest {

		@Mock
		private EventListener collaborator1 = mock(EventListener.class);

		@Mock
		private EventListener collaborator2 = mock(EventListener.class);
	}
}
