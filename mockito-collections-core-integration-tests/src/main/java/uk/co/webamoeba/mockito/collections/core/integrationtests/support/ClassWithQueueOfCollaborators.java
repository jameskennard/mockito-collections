package uk.co.webamoeba.mockito.collections.core.integrationtests.support;

import java.util.EventListener;
import java.util.Queue;

public class ClassWithQueueOfCollaborators implements HasCollaborators<EventListener> {

	private Queue<EventListener> collaborators;

	public Queue<EventListener> getCollaborators() {
		return collaborators;
	}

}
