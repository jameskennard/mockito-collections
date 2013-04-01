package uk.co.webamoeba.mockito.collections.core.integrationtests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;

import java.text.DateFormat;
import java.util.EventListener;
import java.util.EventListenerProxy;
import java.util.Iterator;
import java.util.Observer;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import uk.co.webamoeba.mockito.collections.MockitoCollections;
import uk.co.webamoeba.mockito.collections.core.integrationtests.support.ClassWithCollectionOfCollaboratorsWithNoGenerics;
import uk.co.webamoeba.mockito.collections.core.integrationtests.support.ClassWithListOfCollaborators;
import uk.co.webamoeba.mockito.collections.core.integrationtests.support.ClassWithMoreThanOneCollectionOfCollaborators;
import uk.co.webamoeba.mockito.collections.core.integrationtests.support.ClassWithQueueOfCollaborators;
import uk.co.webamoeba.mockito.collections.core.integrationtests.support.ClassWithSetOfCollaborators;
import uk.co.webamoeba.mockito.collections.core.integrationtests.support.ClassWithSortedSetOfCollaborators;

/**
 * Integration test intended to exercise the scenarios set out in the story <b>
 * {@link InjectCollectionsOfMocksIntoAnObjectUnderTestStory Inject Collections (interfaces) of mocks into an Object
 * under test}</b>.
 * 
 * @author James Kennard
 */
public class InjectCollectionsOfMocksIntoAnObjectUnderTestStoryIntegrationTest implements
		InjectCollectionsOfMocksIntoAnObjectUnderTestStory {

	@Test
	public void classOfObjectUnderTestHasListOfCollaborators() {
		// Given
		ExampleTest<ClassWithListOfCollaborators> exampleTest = new ExampleTest<ClassWithListOfCollaborators>();
		exampleTest.cut = new ClassWithListOfCollaborators();

		// When
		MockitoCollections.initialise(exampleTest);

		// Then
		assertNotNull(exampleTest.cut.getCollaborators());
		assertEquals(4, exampleTest.cut.getCollaborators().size());
		Iterator<EventListener> iterator = exampleTest.cut.getCollaborators().iterator();
		assertSame(exampleTest.collaborator1, iterator.next());
		assertSame(exampleTest.collaborator2, iterator.next());
		assertSame(exampleTest.subTypeCollaborator1, iterator.next());
		assertSame(exampleTest.subTypeCollaborator2, iterator.next());
	}

	@Test
	public void classOfObjectUnderTestHasSetOfCollaborators() {
		// Given
		ExampleTest<ClassWithSetOfCollaborators> exampleTest = new ExampleTest<ClassWithSetOfCollaborators>();
		exampleTest.cut = new ClassWithSetOfCollaborators();

		// When
		MockitoCollections.initialise(exampleTest);

		// Then
		assertNotNull(exampleTest.cut.getCollaborators());
		assertEquals(4, exampleTest.cut.getCollaborators().size());
		Iterator<EventListener> iterator = exampleTest.cut.getCollaborators().iterator();
		assertSame(exampleTest.collaborator1, iterator.next());
		assertSame(exampleTest.collaborator2, iterator.next());
		assertSame(exampleTest.subTypeCollaborator1, iterator.next());
		assertSame(exampleTest.subTypeCollaborator2, iterator.next());
	}

	@Test
	public void classOfObjectUnderTestHasSortedSetOfCollaborators() {
		// Given
		ExampleTest<ClassWithSortedSetOfCollaborators> exampleTest = new ExampleTest<ClassWithSortedSetOfCollaborators>();
		exampleTest.cut = new ClassWithSortedSetOfCollaborators();

		// When
		MockitoCollections.initialise(exampleTest);

		// Then
		assertNotNull(exampleTest.cut.getCollaborators());
		assertEquals(4, exampleTest.cut.getCollaborators().size());
		Iterator<EventListener> iterator = exampleTest.cut.getCollaborators().iterator();
		assertSame(exampleTest.collaborator1, iterator.next());
		assertSame(exampleTest.collaborator2, iterator.next());
		assertSame(exampleTest.subTypeCollaborator1, iterator.next());
		assertSame(exampleTest.subTypeCollaborator2, iterator.next());
	}

	@Test
	public void classOfObjectUnderTestHasQueueOfCollaborators() {
		// Given
		ExampleTest<ClassWithQueueOfCollaborators> exampleTest = new ExampleTest<ClassWithQueueOfCollaborators>();
		exampleTest.cut = new ClassWithQueueOfCollaborators();

		// When
		MockitoCollections.initialise(exampleTest);

		// Then
		assertNotNull(exampleTest.cut.getCollaborators());
		assertEquals(4, exampleTest.cut.getCollaborators().size());
		Iterator<EventListener> iterator = exampleTest.cut.getCollaborators().iterator();
		assertSame(exampleTest.collaborator1, iterator.next());
		assertSame(exampleTest.collaborator2, iterator.next());
		assertSame(exampleTest.subTypeCollaborator1, iterator.next());
		assertSame(exampleTest.subTypeCollaborator2, iterator.next());
	}

	@Test
	public void classOfObjectUnderTestHasMoreThanOneCollectionOfCollaborators() {
		// Given
		ExampleTest<ClassWithMoreThanOneCollectionOfCollaborators> exampleTest = new ExampleTest<ClassWithMoreThanOneCollectionOfCollaborators>();
		exampleTest.cut = new ClassWithMoreThanOneCollectionOfCollaborators();

		// When
		MockitoCollections.initialise(exampleTest);

		// Then
		assertNotNull(exampleTest.cut.getSomeCollaborators());
		assertEquals(4, exampleTest.cut.getSomeCollaborators().size());
		Iterator<EventListener> iterator = exampleTest.cut.getSomeCollaborators().iterator();
		assertSame(exampleTest.collaborator1, iterator.next());
		assertSame(exampleTest.collaborator2, iterator.next());
		assertSame(exampleTest.subTypeCollaborator1, iterator.next());
		assertSame(exampleTest.subTypeCollaborator2, iterator.next());

		assertNotNull(exampleTest.cut.getSomeOtherCollaborators());
		assertEquals(4, exampleTest.cut.getSomeOtherCollaborators().size());
		Iterator<EventListener> otherIterator = exampleTest.cut.getSomeOtherCollaborators().iterator();
		assertSame(exampleTest.collaborator1, otherIterator.next());
		assertSame(exampleTest.collaborator2, otherIterator.next());
		assertSame(exampleTest.subTypeCollaborator1, otherIterator.next());
		assertSame(exampleTest.subTypeCollaborator2, otherIterator.next());

		assertNotSame(exampleTest.cut.getSomeCollaborators(), exampleTest.cut.getSomeOtherCollaborators());
	}

	@Test
	public void classOfObjectUnderTestHasNoCollectionsOfCollaborators() {
		// Given
		ExampleTest<Object> exampleTest = new ExampleTest<Object>();
		exampleTest.cut = new Object();

		// When
		MockitoCollections.initialise(exampleTest);

		// Then
		// Nothing to assert, we just want to ensure we make it out alive!
	}

	@Test
	public void classOfObjectUnderTestHasCollectionsOfCollaboratorsWithNoGenerics() {
		// Given
		ExampleTest<ClassWithCollectionOfCollaboratorsWithNoGenerics> exampleTest = new ExampleTest<ClassWithCollectionOfCollaboratorsWithNoGenerics>();
		exampleTest.cut = new ClassWithCollectionOfCollaboratorsWithNoGenerics();

		// When
		MockitoCollections.initialise(exampleTest);

		// Then
		assertNull(exampleTest.cut.getCollaborators());
	}

	private class ExampleTest<CUT> {

		@InjectMocks
		private CUT cut;

		@Mock
		private EventListener collaborator1 = mock(EventListener.class);

		@Mock
		private EventListener collaborator2 = mock(EventListener.class);

		@Mock
		private EventListenerProxy subTypeCollaborator1 = mock(EventListenerProxy.class);

		@Mock
		private EventListenerProxy subTypeCollaborator2 = mock(EventListenerProxy.class);

		@Mock
		@SuppressWarnings("unused")
		private Observer uninterestingMock = mock(Observer.class);

		@Mock
		@SuppressWarnings("unused")
		private DateFormat anotherUninterestingMock = mock(DateFormat.class);

	};

}
