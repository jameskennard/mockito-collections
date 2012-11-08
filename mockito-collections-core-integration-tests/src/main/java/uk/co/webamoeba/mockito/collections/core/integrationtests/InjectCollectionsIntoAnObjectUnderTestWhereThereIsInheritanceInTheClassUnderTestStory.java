package uk.co.webamoeba.mockito.collections.core.integrationtests;

import java.util.Collection;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import uk.co.webamoeba.mockito.collections.core.integrationtests.support.ClassWithInheritedCollectionOfCollaborators;

/**
 * <b>Narrative:</b> Inject {@link Collection Collections} of mocks into an {@link Object} under test where there is
 * inheritance in the class under test
 * <p>
 * <b>As a</b> developer<br />
 * <b>I want</b> to inject {@link Collection Collections} of mocks (denoted by the {@link Mock} annotation) into an
 * {@link Object} under test (denoted by an {@link InjectMocks} annotation) where there is inheritance in the class
 * under test<br />
 * <b>So that</b> I can mock the behaviour of all the collaborators<br />
 * <b>And</b> validate the behaviour of the {@link Object} under test
 * 
 * @author James Kennard
 */
public interface InjectCollectionsIntoAnObjectUnderTestWhereThereIsInheritanceInTheClassUnderTestStory {

	/**
	 * <b>Scenario:</b> Class of object under test inherits {@link Collection} of collaborators from parent class
	 * <p>
	 * <b>Given</b> a test Class with an object under test denoted by an {@link InjectMocks} annotation<br />
	 * <b>And</b> the Class of the {@link Object} under test inherits from a parent Class<br />
	 * <b>And</b> the parent class has one {@link Collection} of collaborators with generics<br />
	 * <b>And</b> the test Class defines a number of mocks suitable for the {@link Collection}<br />
	 * <b>When</b> I setup the test<br />
	 * <b>Then</b> the mocks are injected as a {@link Collection} into the object under test<br />
	 * <b>And</b> the mocks in the {@link Collection} are in the order they are defined
	 * 
	 * @see ClassWithInheritedCollectionOfCollaborators
	 */
	public void classOfObjectUnderTestInheritsCollectionOfCollaboratorsFromParentClass();
}
