package uk.co.webamoeba.mockito.collections.core.integrationtests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.EventListener;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.internal.util.MockUtil;

import uk.co.webamoeba.mockito.collections.MockitoCollections;
import uk.co.webamoeba.mockito.collections.annotation.CollectionOfMocks;
import uk.co.webamoeba.mockito.collections.core.integrationtests.support.ClassWithCollectionOfCollaborators;

public class InjectCollectionOfMocksAnnotatedFieldsStoryIntegrationTest implements
		InjectCollectionOfMocksAnnotatedFieldsStory {

	@Test
	public void objectUnderTestHasCollectionOfCollaborators() {
		// Given
		ExampleTest test = new ExampleTest();
		test.objectUnderTest = new ClassWithCollectionOfCollaborators();

		// When
		MockitoCollections.initialise(test);

		// Then
		assertEquals(1, test.collaborators.size());
		assertTrue(new MockUtil().isMock(test.collaborators.iterator().next()));
		assertEquals(test.collaborators, test.objectUnderTest.getCollaborators());
	}

	private final class ExampleTest {

		@InjectMocks
		private ClassWithCollectionOfCollaborators objectUnderTest;

		@CollectionOfMocks
		private Collection<EventListener> collaborators;
	}
}
