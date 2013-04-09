package uk.co.webamoeba.mockito.collections.sample;

import static org.mockito.Mockito.verify;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * This test shows a typical test where there is a {@link Collection} of delegates that do not return values. Comments
 * are included throughout the file to hellp clarify what's going on.
 * 
 * @author James Kennard
 */
@RunWith(MockitoJUnitRunner.class)
public class SampleListenerManagerTest {

	// SampleListenerManager containing a Collection of SampleListeners
	@InjectMocks
	private SampleListenerManager manager;

	@Mock
	private SampleListener sampleListener1;

	@Mock
	private SampleListener sampleListener2;

	// Setup setting the listeners
	@Before
	public void before() {
		Set<SampleListener> listeners = new HashSet<SampleListener>();
		listeners.add(sampleListener1);
		listeners.add(sampleListener2);
		manager.setListeners(listeners);
	}

	@Test
	public void shouldCallAllListeners() {
		// Given
		String someEvent = "Something";

		// When
		manager.eventOccurred(someEvent);

		// Then
		// verify all the listeners were called
		verify(sampleListener1).eventOccured(someEvent);
		verify(sampleListener2).eventOccured(someEvent);
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
