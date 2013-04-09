package uk.co.webamoeba.mockito.collections.sample;

import static org.junit.Assert.assertArrayEquals;
import static org.mockito.BDDMockito.given;

import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * This test shows a typical test where there is a {@link Set} of delegates that return values. Comments are included
 * throughout the file to hellp clarify what's going on.
 * 
 * @author James Kennard
 */
@RunWith(MockitoJUnitRunner.class)
public class SampleHandlerManagerTest {

	// SampleHandlerManager containing a Set of Handlers
	@InjectMocks
	private SampleHandlerManager manager;

	@Mock
	private Handler handler1;

	@Mock
	private Handler handler2;

	// Setup making use of LinkedHashSet to guarantee order
	@Before
	public void before() {
		Set<Handler> handlers = new LinkedHashSet<Handler>();
		handlers.add(handler1);
		handlers.add(handler2);
		manager.setHandlers(handlers);
	}

	@Test
	public void shouldCallAllHandlers() {
		// Given
		String thingToHandle = "Something";
		given(handler1.handle(thingToHandle)).willReturn("value1");
		given(handler2.handle(thingToHandle)).willReturn("value2");

		// When
		String[] strings = manager.handle(thingToHandle);

		// Then
		// Is array (order is important) but we can guarantee order of Set of Handlers using LinkedHashSet
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
