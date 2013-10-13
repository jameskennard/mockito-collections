package uk.co.webamoeba.mockito.collections.core.integrationtests.support;

import java.util.Collection;
import java.util.EventListener;

public class ClassWithCollectionOfCollaborators implements HasCollaborators<EventListener> {

	private Collection<EventListener> collaborators;

	public Collection<EventListener> getCollaborators() {
		return collaborators;
	}
}
