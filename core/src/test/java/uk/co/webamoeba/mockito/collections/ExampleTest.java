package uk.co.webamoeba.mockito.collections;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * This test is intended to provide an example of how one might use Mockito-Collections in practice.
 * 
 * @author James Kennard
 */
// FIXME This isn't really part of core, this should be in a separate project.
@RunWith(MockitoJUnitRunner.class)
public class ExampleTest {

    @InjectMocks
    private MyDelegate delegate = new MyDelegate();

    @Mock
    private MyListener listener1;

    @Mock
    private MyListener listener2;

    @Before
    public void setup() {
	MockitoCollectionAnnotations.inject(this);
    }

    @Test
    public void shouldPerformAction() {
	// Given
	SomeAction action = mock(SomeAction.class);

	// When
	delegate.performAction(action);

	// Then
	verify(listener1).actionPerformed(action);
	verify(listener2).actionPerformed(action);
    }

    private class MyDelegate {

	private Collection<MyListener> listeners;

	public void performAction(SomeAction action) {
	    for (MyListener listener : listeners) {
		listener.actionPerformed(action);
	    }
	}
    }

    private interface MyListener {

	public void actionPerformed(SomeAction action);
    }

    private interface SomeAction {

    }
}
