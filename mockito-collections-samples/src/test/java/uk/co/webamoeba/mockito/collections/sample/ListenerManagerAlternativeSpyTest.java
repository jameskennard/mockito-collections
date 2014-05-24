package uk.co.webamoeba.mockito.collections.sample;

import static org.mockito.Mockito.verify;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Spy;

import uk.co.webamoeba.mockito.collections.MockitoCollections;

/**
 * This test shows how you can use Mockito-Collections to test where there is a {@link Collection} of delegates that do
 * not return values. Comments are included throughout the file to help clarify what's going on.
 * 
 * @author Matt Reines
 */
public class ListenerManagerAlternativeSpyTest {

	// ListenerManager containing a Collection of SampleListeners
	@InjectMocks
	private ListenerManager manager;

	@Spy
	private ConcreteListener listener1;

	@Spy
	private ConcreteListener listener2;

	// Setup making use of Mockito-Collections for injection of handlers
	@Before
	public void before() {
		MockitoCollections.initialiseAll(this);
	}

	@Test
	public void shouldCallAllListeners() {
		// Given
		String someEvent = "Something";

		// When
		manager.eventOccurred(someEvent);

		// Then
		// verify all the listeners were called
		verify(listener1).eventOccured("Something");
		verify(listener2).eventOccured("Something");
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
