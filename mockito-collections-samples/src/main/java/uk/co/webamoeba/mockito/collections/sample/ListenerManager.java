package uk.co.webamoeba.mockito.collections.sample;

import java.util.Collection;
import java.util.Set;

public class ListenerManager {

	private Collection<Listener> listeners;

	public void eventOccurred(String someEvent) {
		if (someEvent == null) {
			throw new IllegalArgumentException("someEvent must not be null");
		}

		for (Listener listener : listeners) {
			listener.eventOccured(someEvent);
		}
	}

	public void setListeners(Set<Listener> listeners) {
		this.listeners = listeners;
	}
}
