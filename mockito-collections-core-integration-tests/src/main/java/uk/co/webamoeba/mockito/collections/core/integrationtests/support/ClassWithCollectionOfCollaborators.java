package uk.co.webamoeba.mockito.collections.core.integrationtests.support;

import java.util.Collection;
import java.util.EventListener;

public class ClassWithCollectionOfCollaborators {

	private Collection<EventListener> collaborators;

	public Collection<EventListener> getCollaborators() {
		return collaborators;
	}
}
