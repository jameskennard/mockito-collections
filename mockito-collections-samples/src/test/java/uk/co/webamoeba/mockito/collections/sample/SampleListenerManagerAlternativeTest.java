package uk.co.webamoeba.mockito.collections.sample;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import uk.co.webamoeba.mockito.collections.MockitoCollections;
import uk.co.webamoeba.mockito.collections.annotation.CollectionOfMocks;

/**
 * This test shows how you can use Mockito Collections to test where there is a {@link Collection} of delegates that do
 * not return values. Comments are included throughout the file to hellp clarify what's going on.
 * 
 * @author James Kennard
 */
@RunWith(MockitoJUnitRunner.class)
public class SampleListenerManagerAlternativeTest {

	// SampleListenerManager containing a Collection of SampleListeners
	@InjectMocks
	private SampleListenerManager manager;

	// A Collection of two Mocks
	@CollectionOfMocks(numberOfMocks = 2)
	private Collection<SampleListener> listeners;

	// Setup making use of Mockito Collections for injection of handlers
	@Before
	public void before() {
		MockitoCollections.initialise(this);
	}

	@Test
	public void shouldCallAllListeners() {
		// Given
		String someEvent = "Something";

		// When
		manager.eventOccurred(someEvent);

		// Then
		// collectively verify all the listeners were called
		MockitoCollections.collectiveVerify(SampleListener.class, listeners).eventOccured(someEvent);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldFailGivenNull() {
		// Given
		String someEvent = null;

		// When
		manager.eventOccurred(someEvent);

		// Then
		// Exception Thrown
	}
}
