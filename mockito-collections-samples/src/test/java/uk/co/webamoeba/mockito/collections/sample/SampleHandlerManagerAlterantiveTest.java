package uk.co.webamoeba.mockito.collections.sample;

import static org.junit.Assert.assertArrayEquals;
import static org.mockito.BDDMockito.given;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import uk.co.webamoeba.mockito.collections.MockitoCollections;

/**
 * This test shows how you can use Mockito Collections to deal simply with a {@link Set} of delegates. Comments are
 * included throughout the file to hellp clarify what's going on.
 * 
 * @author James Kennard
 */
@RunWith(MockitoJUnitRunner.class)
public class SampleHandlerManagerAlterantiveTest {

	// SampleHandlerManager containing a Set of Handlers
	@InjectMocks
	private SampleHandlerManager manager;

	// Mock to be injected
	// Name means it will be always first even though it is to be injected into a Set (alphabetical)
	@Mock
	private Handler handler1;

	// Another Mock to be injected
	// Name means it will always be second even though it is to be injected into a Set (alphabetical)
	@Mock
	private Handler handler2;

	// Setup making use of Mockito Collections for injection of handlers
	@Before
	public void before() {
		MockitoCollections.initialise(this);
	}

	@Test
	public void shouldCallAllHandlers() {
		// Given
		String thingToHandle = "Something";
		// normal use of Mockito
		given(handler1.handle(thingToHandle)).willReturn("value1");
		given(handler2.handle(thingToHandle)).willReturn("value2");

		// When
		String[] strings = manager.handle(thingToHandle);

		// Then
		// Normal use of assert
		// Is array (order is important) but we can guarantee order of Set of Handlers using Mockito Collections
		// No need to verify see http://monkeyisland.pl/2008/04/26/asking-and-telling/
		assertArrayEquals(new String[] { "value1", "value2" }, strings);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldFailToHandleGivenNull() {
		// Given
		String thingToHandle = null;

		// When
		manager.handle(thingToHandle);

		// Then
		// exception thrown
	}
}
