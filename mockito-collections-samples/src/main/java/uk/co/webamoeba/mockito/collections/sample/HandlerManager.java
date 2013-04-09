package uk.co.webamoeba.mockito.collections.sample;

import java.util.Set;

/**
 * Sample Manager class that encapsulates a {@link Set} of {@link Handler Handlers} and delegates calls to them.
 * 
 * @author James Kennard
 */
public class HandlerManager {

	private Set<Handler> handlers;

	public String[] handle(String thingToHandle) {
		if (thingToHandle == null) {
			throw new IllegalArgumentException("thingToHandle must not be null");
		}

		String[] strings = new String[handlers.size()];
		int i = 0;
		for (Handler handler : handlers) {
			strings[i++] = handler.handle(thingToHandle);
		}
		return strings;
	}

	public void setHandlers(Set<Handler> handlers) {
		this.handlers = handlers;
	}
}
