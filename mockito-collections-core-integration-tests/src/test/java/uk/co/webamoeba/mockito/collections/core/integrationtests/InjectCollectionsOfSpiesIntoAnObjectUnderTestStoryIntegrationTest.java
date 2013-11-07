package uk.co.webamoeba.mockito.collections.core.integrationtests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.spy;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.EventListener;
import java.util.EventListenerProxy;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Spy;

import uk.co.webamoeba.mockito.collections.MockitoCollections;
import uk.co.webamoeba.mockito.collections.core.integrationtests.support.ClassWithCollectionOfCollaboratorsWithNoGenerics;
import uk.co.webamoeba.mockito.collections.core.integrationtests.support.ClassWithListOfCollaborators;
import uk.co.webamoeba.mockito.collections.core.integrationtests.support.ClassWithMoreThanOneCollectionOfCollaborators;
import uk.co.webamoeba.mockito.collections.core.integrationtests.support.ClassWithQueueOfCollaborators;
import uk.co.webamoeba.mockito.collections.core.integrationtests.support.ClassWithSetOfCollaborators;
import uk.co.webamoeba.mockito.collections.core.integrationtests.support.ClassWithSortedSetOfCollaborators;

public class InjectCollectionsOfSpiesIntoAnObjectUnderTestStoryIntegrationTest implements InjectCollectionsOfSpiesIntoAnObjectUnderTestStory{

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
	
	private class MockEventListener implements EventListener{
		
	}
	private class MockEventListenerProxy extends EventListenerProxy{
		public MockEventListenerProxy(){
			super(new MockEventListener());
		}
		public MockEventListenerProxy(EventListener arg0) {
			super(arg0);
		}
		
	}	
	private class MockObserver implements Observer{
		public void update(Observable arg0, Object arg1) {
		}
	}
	
	private class ExampleTest<CUT> {

		@InjectMocks
		private CUT cut;

		@Spy
		private EventListener collaborator1 = spy(new MockEventListener());

		@Spy
		private EventListener collaborator2 = spy(new MockEventListener());

		@Spy
		private EventListenerProxy subTypeCollaborator1 = spy(new MockEventListenerProxy());

		@Spy
		private EventListenerProxy subTypeCollaborator2 = spy(new MockEventListenerProxy());

		@Spy
		private Observer uninterestingMock = spy(new MockObserver());

		@Spy
		private DateFormat anotherUninterestingMock = spy(new SimpleDateFormat());

	};
}
