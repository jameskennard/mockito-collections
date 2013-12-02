package uk.co.webamoeba.mockito.collections.core.integrationtests;

import java.util.Collection;

import org.mockito.InjectMocks;

import uk.co.webamoeba.mockito.collections.core.integrationtests.support.ClassWithInheritedCollectionOfCollaborators;

/**
 * <b>Narrative:</b> Inject mocks and spies into inherites {@link Collection Collections}
 * <p>
 * <b>In order to</b> test the behaviour of an {@link Object} containing inherited {@link Collection Collections} of
 * collaborators<br />
 * <b>We will</b> inject mocks and spies into inherited {@link Collection Collections} of collaborators
 * 
 * @author James Kennard
 */
public interface InjectMocksAndSpiesIntoInheritedCollectionsStory {

	/**
	 * <b>Scenario:</b> {@link Object} under test inherits {@link Collection} of collaborators
	 * <p>
	 * <b>Given</b> an {@link Object} under test (denoted by an {@link InjectMocks} annotation)<br />
	 * <b>And</b> the {@link Object} under test inherits a {@link Collection} of collaborators with generics<br />
	 * <b>And</b> the test Class defines a number of mocks and spies suitable for the {@link Collection}<br />
	 * <b>When</b> I setup the test<br />
	 * <b>Then</b> the mocks and spies are injected as a {@link Collection} into the object under test<br />
	 * <b>And</b> the mocks and spies in the {@link Collection} are in alphabetical order
	 * 
	 * @see ClassWithInheritedCollectionOfCollaborators
	 */
	public void objectUnderTestInheritsCollectionOfCollaborators();
}
