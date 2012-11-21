package uk.co.webamoeba.mockito.collections.core.integrationtests.support;

import java.util.EventListener;

public class ClassWithConcreteCollectionWithInitialCapacityConstructorOfCollaborators {

	private ConcreteCollectionWithInitialCapacityConstructor<EventListener> collaborators;

	public ConcreteCollectionWithInitialCapacityConstructor<EventListener> getCollaborators() {
		return collaborators;
	}
}
