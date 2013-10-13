package uk.co.webamoeba.mockito.collections.core.integrationtests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;

import java.util.EventListener;
import java.util.Iterator;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import uk.co.webamoeba.mockito.collections.MockitoCollections;
import uk.co.webamoeba.mockito.collections.core.integrationtests.support.ClassWithCollectionOfCollaborators;
import uk.co.webamoeba.mockito.collections.core.integrationtests.support.ClassWithCollectionOfCollaboratorsWithNoGenerics;
import uk.co.webamoeba.mockito.collections.core.integrationtests.support.ClassWithListOfCollaborators;
import uk.co.webamoeba.mockito.collections.core.integrationtests.support.ClassWithMoreThanOneCollectionOfCollaborators;
import uk.co.webamoeba.mockito.collections.core.integrationtests.support.ClassWithQueueOfCollaborators;
import uk.co.webamoeba.mockito.collections.core.integrationtests.support.ClassWithSetOfCollaborators;
import uk.co.webamoeba.mockito.collections.core.integrationtests.support.ClassWithSortedSetOfCollaborators;
import uk.co.webamoeba.mockito.collections.core.integrationtests.support.HasCollaborators;

/**
 * Integration test intended to exercise the scenarios set out in the story <b> {@link InjectCollectionsOfMocksStory
 * Inject Collections (interfaces) of mocks into an Object under test}</b>.
 * 
 * @author James Kennard
 */
public class InjectCollectionsOfMocksStoryIntegrationTest implements InjectCollectionsOfMocksStory {

	@Test
	public void objectUnderTestHasCollectionOfCollaborators() {
		ExampleTest<ClassWithCollectionOfCollaborators> exampleTest = new ExampleTest<ClassWithCollectionOfCollaborators>();
		exampleTest.objectUnderTest = new ClassWithCollectionOfCollaborators();
		assertInjectsCollectionsOfMocks(exampleTest);
	}

	@Test
	public void objectUnderTestHasListOfCollaborators() {
		ExampleTest<ClassWithListOfCollaborators> exampleTest = new ExampleTest<ClassWithListOfCollaborators>();
		exampleTest.objectUnderTest = new ClassWithListOfCollaborators();
		assertInjectsCollectionsOfMocks(exampleTest);
	}

	@Test
	public void objectUnderTestHasSetOfCollaborators() {
		ExampleTest<ClassWithSetOfCollaborators> exampleTest = new ExampleTest<ClassWithSetOfCollaborators>();
		exampleTest.objectUnderTest = new ClassWithSetOfCollaborators();
		assertInjectsCollectionsOfMocks(exampleTest);
	}

	@Test
	public void objectUnderTestHasSortedSetOfCollaborators() {
		ExampleTest<ClassWithSortedSetOfCollaborators> exampleTest = new ExampleTest<ClassWithSortedSetOfCollaborators>();
		exampleTest.objectUnderTest = new ClassWithSortedSetOfCollaborators();
		assertInjectsCollectionsOfMocks(exampleTest);
	}

	@Test
	public void objectUnderTestHasQueueOfCollaborators() {
		ExampleTest<ClassWithQueueOfCollaborators> exampleTest = new ExampleTest<ClassWithQueueOfCollaborators>();
		exampleTest.objectUnderTest = new ClassWithQueueOfCollaborators();
		assertInjectsCollectionsOfMocks(exampleTest);
	}

	public <T extends HasCollaborators<EventListener>> void assertInjectsCollectionsOfMocks(ExampleTest<T> exampleTest) {
		// When
		MockitoCollections.initialise(exampleTest);

		// Then
		assertEquals(2, exampleTest.objectUnderTest.getCollaborators().size());
		Iterator<EventListener> iterator = exampleTest.objectUnderTest.getCollaborators().iterator();
		assertSame(exampleTest.collaborator1, iterator.next());
		assertSame(exampleTest.collaborator2, iterator.next());
	}

	@Test
	public void objectUnderTestHasMoreThanOneCollectionOfCollaborators() {
		// Given
		ExampleTest<ClassWithMoreThanOneCollectionOfCollaborators> exampleTest = new ExampleTest<ClassWithMoreThanOneCollectionOfCollaborators>();
		exampleTest.objectUnderTest = new ClassWithMoreThanOneCollectionOfCollaborators();

		// When
		MockitoCollections.initialise(exampleTest);

		// Then
		assertEquals(2, exampleTest.objectUnderTest.getSomeCollaborators().size());
		Iterator<EventListener> iterator = exampleTest.objectUnderTest.getSomeCollaborators().iterator();
		assertSame(exampleTest.collaborator1, iterator.next());
		assertSame(exampleTest.collaborator2, iterator.next());

		assertEquals(2, exampleTest.objectUnderTest.getSomeOtherCollaborators().size());
		Iterator<EventListener> otherIterator = exampleTest.objectUnderTest.getSomeOtherCollaborators().iterator();
		assertSame(exampleTest.collaborator1, otherIterator.next());
		assertSame(exampleTest.collaborator2, otherIterator.next());

		assertNotSame(exampleTest.objectUnderTest.getSomeCollaborators(),
				exampleTest.objectUnderTest.getSomeOtherCollaborators());
	}

	@Test
	public void objectUnderTestHasNoCollectionsOfCollaborators() {
		// Given
		ExampleTest<Object> exampleTest = new ExampleTest<Object>();
		exampleTest.objectUnderTest = new Object();

		// When
		MockitoCollections.initialise(exampleTest);

		// Then
		// Nothing to assert, we just want to ensure we make it out alive!
	}

	@Test
	public void classOfObjectUnderTestHasCollectionsOfCollaboratorsWithNoGenerics() {
		// Given
		ExampleTest<ClassWithCollectionOfCollaboratorsWithNoGenerics> exampleTest = new ExampleTest<ClassWithCollectionOfCollaboratorsWithNoGenerics>();
		exampleTest.objectUnderTest = new ClassWithCollectionOfCollaboratorsWithNoGenerics();

		// When
		MockitoCollections.initialise(exampleTest);

		// Then
		assertNull(exampleTest.objectUnderTest.getCollaborators());
	}

	private class ExampleTest<T> {

		@InjectMocks
		private T objectUnderTest;

		@Mock
		private EventListener collaborator1 = mock(EventListener.class);

		@Mock
		private EventListener collaborator2 = mock(EventListener.class);

	};

}
