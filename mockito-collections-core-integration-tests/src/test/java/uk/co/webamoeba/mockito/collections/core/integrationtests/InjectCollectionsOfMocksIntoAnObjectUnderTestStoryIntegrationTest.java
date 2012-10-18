package uk.co.webamoeba.mockito.collections.core.integrationtests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

import java.text.DateFormat;
import java.util.Arrays;
import java.util.EventListener;
import java.util.EventListenerProxy;
import java.util.Iterator;
import java.util.List;
import java.util.Observer;

import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import uk.co.webamoeba.mockito.collections.MockitoCollectionAnnotations;
import uk.co.webamoeba.mockito.collections.core.integrationtests.support.ClassWithListOfCollaborators;
import uk.co.webamoeba.mockito.collections.core.integrationtests.support.ClassWithSetOfCollaborators;

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
		final ClassWithListOfCollaborators outterCUT = new ClassWithListOfCollaborators();
		final EventListener outerCollaborator1 = mock(EventListener.class);
		final EventListener outerCollaborator2 = mock(EventListener.class);
		final EventListenerProxy outerSubTypeCollaborator1 = mock(EventListenerProxy.class);
		final EventListenerProxy outerSubTypeCollaborator2 = mock(EventListenerProxy.class);
		List<EventListener> expectedCollection = Arrays.asList(outerCollaborator1, outerCollaborator2,
				outerSubTypeCollaborator1, outerSubTypeCollaborator2);
		@SuppressWarnings("unused")
		Object exampleTest = new Object() {

			@InjectMocks
			private ClassWithListOfCollaborators cut = outterCUT;

			@Mock
			private EventListener collaborator1 = outerCollaborator1;

			@Mock
			private EventListener collaborator2 = outerCollaborator2;

			@Mock
			private EventListenerProxy subTypeCollaborator1 = outerSubTypeCollaborator1;

			@Mock
			private EventListenerProxy subTypeCollaborator2 = outerSubTypeCollaborator2;

			@Mock
			private Observer uninterestingMock = mock(Observer.class);

			@Mock
			private DateFormat anotherUninterestingMock = mock(DateFormat.class);
		};

		// When
		MockitoCollectionAnnotations.inject(exampleTest);

		// Then
		assertNotNull(outterCUT.getCollaborators());
		assertTrue(outterCUT.getCollaborators().equals(expectedCollection));
	}

	@Test
	public void classOfObjectUnderTestHasSetOfCollaborators() {
		// Given
		final ClassWithSetOfCollaborators outterCUT = new ClassWithSetOfCollaborators();
		final EventListener outerCollaborator1 = mock(EventListener.class);
		final EventListener outerCollaborator2 = mock(EventListener.class);
		final EventListenerProxy outerSubTypeCollaborator1 = mock(EventListenerProxy.class);
		final EventListenerProxy outerSubTypeCollaborator2 = mock(EventListenerProxy.class);
		@SuppressWarnings("unused")
		Object exampleTest = new Object() {

			@InjectMocks
			private ClassWithSetOfCollaborators cut = outterCUT;

			@Mock
			private EventListener collaborator1 = outerCollaborator1;

			@Mock
			private EventListener collaborator2 = outerCollaborator2;

			@Mock
			private EventListenerProxy subTypeCollaborator1 = outerSubTypeCollaborator1;

			@Mock
			private EventListenerProxy subTypeCollaborator2 = outerSubTypeCollaborator2;

			@Mock
			private Observer uninterestingMock = mock(Observer.class);

			@Mock
			private DateFormat anotherUninterestingMock = mock(DateFormat.class);
		};

		// When
		MockitoCollectionAnnotations.inject(exampleTest);

		// Then
		assertNotNull(outterCUT.getCollaborators());
		assertEquals(4, outterCUT.getCollaborators().size());
		Iterator<EventListener> iterator = outterCUT.getCollaborators().iterator();
		assertSame(outerCollaborator1, iterator.next());
		assertSame(outerCollaborator2, iterator.next());
		assertSame(outerSubTypeCollaborator1, iterator.next());
		assertSame(outerSubTypeCollaborator2, iterator.next());
	}

	@Test
	@Ignore
	public void classOfObjectUnderTestHasSortedSetOfCollaborators() {
		fail("TODO");
	}

	@Test
	@Ignore
	public void classOfObjectUnderTestHasQueueOfCollaborators() {
		fail("TODO");
	}

	@Test
	@Ignore
	public void classOfObjectUnderTestHasMoreThanOneCollectionOfCollaborators() {
		fail("TODO");
	}

	@Test
	@Ignore
	public void classOfObjectUnderTestHasNoCollectionsOfCollaborators() {
		fail("TODO");
	}

	@Test
	@Ignore
	public void classOfObjectUnderTestHasCollectionsOfCollaboratorsWithNoGenerics() {
		fail("TODO");
	}

}
