package uk.co.webamoeba.mockito.collections.core.integrationtests.support;

import java.util.EventListener;
import java.util.SortedSet;

public class ClassWithSortedSetOfCollaborators {

	private SortedSet<EventListener> collaborators;

	public SortedSet<EventListener> getCollaborators() {
		return collaborators;
	}

}
