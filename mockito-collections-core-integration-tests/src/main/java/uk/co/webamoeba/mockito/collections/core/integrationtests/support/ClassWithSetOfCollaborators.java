package uk.co.webamoeba.mockito.collections.core.integrationtests.support;

import java.util.EventListener;
import java.util.Set;

public class ClassWithSetOfCollaborators implements HasCollaborators<EventListener> {

	private Set<EventListener> collaborators;

	public Set<EventListener> getCollaborators() {
		return collaborators;
	}

}
