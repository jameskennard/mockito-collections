package uk.co.webamoeba.mockito.collections.core.integrationtests.support;

import java.util.Collection;
import java.util.EventListener;

public class ClassWithMoreThanOneCollectionOfCollaborators {

	@SuppressWarnings("unused")
	private Collection<EventListener> someCollaborators;

	@SuppressWarnings("unused")
	private Collection<EventListener> someOtherCollaborators;

}
