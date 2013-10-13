package uk.co.webamoeba.mockito.collections.core.integrationtests.support;

import java.util.EventListener;
import java.util.List;

public class ClassWithListOfCollaborators implements HasCollaborators<EventListener> {

	private List<EventListener> collaborators;

	public List<EventListener> getCollaborators() {
		return collaborators;
	}

}
