package uk.co.webamoeba.mockito.collections.sample;

import java.util.Collection;
import java.util.Set;

public class SampleListenerManager {

	private Collection<SampleListener> listeners;

	public void eventOccurred(String someEvent) {
		if (someEvent == null) {
			throw new IllegalArgumentException("someEvent must not be null");
		}

		for (SampleListener listener : listeners) {
			listener.eventOccured(someEvent);
		}
	}

	public void setListeners(Set<SampleListener> listeners) {
		this.listeners = listeners;
	}
}
