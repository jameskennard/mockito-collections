package uk.co.webamoeba.mockito.collections.sample;

import static org.junit.Assert.assertSame;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static uk.co.webamoeba.mockito.collections.MockitoCollections.collectiveVerify;

import java.util.Collection;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import uk.co.webamoeba.mockito.collections.MockitoCollections;
import uk.co.webamoeba.mockito.collections.annotation.CollectionOfMocks;
import uk.co.webamoeba.mockito.collections.sample.VerifyCollectionOfMocksStory.MockitoCollectionsGivenCollectionOfMocksAnnotation;
import uk.co.webamoeba.mockito.collections.sample.VerifyCollectionOfMocksStory.MockitoCollectionsTest;
import uk.co.webamoeba.mockito.collections.sample.VerifyCollectionOfMocksStory.PlainMockitoTest;

/**
 * <b>As a</b> developer<br />
 * <b>I want</b> to inject a collection of collaborators into an object under test<br />
 * <b>So that</b> I can verify the behaviour of the object under test with the collection of collaborators
 * 
 * @author James Kennard
 */
// FIXME this is a mishmash of a sample and a BDD acceptance test for mockito-collections-core
@RunWith(Suite.class)
@SuiteClasses({ PlainMockitoTest.class, MockitoCollectionsTest.class,
		MockitoCollectionsGivenCollectionOfMocksAnnotation.class })
public class VerifyCollectionOfMocksStory {

	@RunWith(MockitoJUnitRunner.class)
	public static class PlainMockitoTest {

		@InjectMocks
		private SampleClassUnderTest sampleClassUnderTest;

		@Mock
		private SampleCollaborator sampleCollaborator;

		@Mock
		private SampleListener sampleListener;

		@Before
		public void before() {
			sampleClassUnderTest.setSampleListeners(Collections.singleton(sampleListener));
		}

		@Test
		public void shouldCallCollaboratorAndSampleListeners() {
			// Given
			SampleData data = mock(SampleData.class);
			SampleData expectedData = mock(SampleData.class);
			given(sampleCollaborator.method(data)).willReturn(expectedData);

			// When
			SampleData actualData = sampleClassUnderTest.callCollaboratorAndSampleListeners(data);

			// Then
			assertSame(expectedData, actualData);
			verify(sampleListener).eventOccured(expectedData);
		}
	}

	@RunWith(MockitoJUnitRunner.class)
	public static class MockitoCollectionsTest {

		@InjectMocks
		private SampleClassUnderTest sampleClassUnderTest;

		@Mock
		private SampleCollaborator sampleCollaborator;

		@Mock
		private SampleListener sampleListener;

		@Before
		public void before() {
			MockitoCollections.initialise(this);
		}

		@Test
		public void shouldCallCollaboratorAndSampleListeners() {
			// Given
			SampleData data = mock(SampleData.class);
			SampleData expectedData = mock(SampleData.class);
			given(sampleCollaborator.method(data)).willReturn(expectedData);

			// When
			SampleData actualData = sampleClassUnderTest.callCollaboratorAndSampleListeners(data);

			// Then
			assertSame(expectedData, actualData);
			verify(sampleListener).eventOccured(expectedData);
		}
	}

	@RunWith(MockitoJUnitRunner.class)
	public static class MockitoCollectionsGivenCollectionOfMocksAnnotation {

		@InjectMocks
		private SampleClassUnderTest sampleClassUnderTest;

		@Mock
		private SampleCollaborator sampleCollaborator;

		@CollectionOfMocks
		private Collection<SampleListener> sampleListeners;

		@Before
		public void before() {
			MockitoCollections.initialise(this);
		}

		@Test
		public void shouldCallCollaboratorAndSampleListeners() {
			// Given
			SampleData data = mock(SampleData.class);
			SampleData expectedData = mock(SampleData.class);
			given(sampleCollaborator.method(data)).willReturn(expectedData);

			// When
			SampleData actualData = sampleClassUnderTest.callCollaboratorAndSampleListeners(data);

			// Then
			assertSame(expectedData, actualData);
			collectiveVerify(SampleListener.class, sampleListeners).eventOccured(expectedData);
		}
	}
}
