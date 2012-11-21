package uk.co.webamoeba.mockito.collections.core.integrationtests.support;

import java.util.EventListener;

public class ClassWithConcreteCollectionOfCollaborators {

	private ConcreteCollection<EventListener> collaborators;

	public ConcreteCollection<EventListener> getCollaborators() {
		return collaborators;
	}
}
