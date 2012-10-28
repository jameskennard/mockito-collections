package uk.co.webamoeba.mockito.collections.core.integrationtests.support;

import java.util.Collection;
import java.util.EventListener;

public class ClassWithMoreThanOneCollectionOfCollaborators {

	private Collection<EventListener> someCollaborators;

	private Collection<EventListener> someOtherCollaborators;

	public Collection<EventListener> getSomeCollaborators() {
		return someCollaborators;
	}

	public Collection<EventListener> getSomeOtherCollaborators() {
		return someOtherCollaborators;
	}

}
