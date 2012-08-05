package uk.co.webamoeba.mockito.collections;

import static org.mockito.Mockito.verify;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class IntegrationTest {

    @InjectMocks
    private MyDelegate delegate = new MyDelegate();

    @Mock
    private MyListener listener1;

    @Mock
    private MyListener listener2;

    @Before
    public void setup() {
	MockitoCollectionInjector.inject(this);
    }

    @Test
    public void shouldPerformAction() {
	// When
	delegate.performAction();

	// Then
	verify(listener1).actionPerformed();
	verify(listener2).actionPerformed();
    }

    private class MyDelegate {

	private Collection<MyListener> listeners;

	public void performAction() {
	    for (MyListener listener : listeners) {
		listener.actionPerformed();
	    }
	}
    }

    private interface MyListener {

	public void actionPerformed();
    }
}
