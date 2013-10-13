package uk.co.webamoeba.mockito.collections.core.integrationtests;

import java.util.Collection;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.SortedSet;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import uk.co.webamoeba.mockito.collections.core.integrationtests.support.ClassWithCollectionOfCollaboratorsWithNoGenerics;
import uk.co.webamoeba.mockito.collections.core.integrationtests.support.ClassWithListOfCollaborators;
import uk.co.webamoeba.mockito.collections.core.integrationtests.support.ClassWithMoreThanOneCollectionOfCollaborators;
import uk.co.webamoeba.mockito.collections.core.integrationtests.support.ClassWithQueueOfCollaborators;
import uk.co.webamoeba.mockito.collections.core.integrationtests.support.ClassWithSetOfCollaborators;
import uk.co.webamoeba.mockito.collections.core.integrationtests.support.ClassWithSortedSetOfCollaborators;

/**
 * <b>Story:</b> Inject {@link Collection Collections} of mocks
 * <p>
 * <b>In order to</b> test the behaviour of an {@link Object} containing a {@link Collection} of collaborators <br />
 * <b>We will</b> inject mock {@link Object Objects} (denoted by the {@link Mock} annotation)
 * 
 * @see InjectConcreteCollectionImplementationsOfMocksIntoAnObjectUnderTestStory
 * @author James Kennard
 */
public interface InjectCollectionsOfMocksStory {

	/**
	 * <b>Scenario:</b> {@link Object} under test has a {@link Collection} of collaborators
	 * <p>
	 * <b>Given</b> an {@link Object} under test (denoted by an {@link InjectMocks} annotation)<br />
	 * <b>And</b> the {@link Object} under test has a {@link List} of collaborators with generics<br />
	 * <b>And</b> the test Class defines mocks suitable types<br />
	 * <b>When</b> I setup the test<br />
	 * <b>Then</b> the mocks are injected as a {@link Collection} into the {@link Object} under test<br />
	 * <b>And</b> the mocks in the {@link Collection} are in alphabetical order
	 * 
	 * @see ClassWithListOfCollaborators
	 */
	public void objectUnderTestHasCollectionOfCollaborators();

	/**
	 * <b>Scenario:</b> {@link Object} under test has {@link List} of collaborators
	 * <p>
	 * <b>Given</b> an {@link Object} under test (denoted by an {@link InjectMocks} annotation)<br />
	 * <b>And</b> the {@link Object} under test has a {@link List} of collaborators with generics<br />
	 * <b>And</b> the test Class defines mocks suitable types<br />
	 * <b>When</b> I setup the test<br />
	 * <b>Then</b> the mocks are injected as a {@link List} into the {@link Object} under test<br />
	 * <b>And</b> the mocks in the {@link List} are in alphabetical order
	 * 
	 * @see ClassWithListOfCollaborators
	 */
	public void objectUnderTestHasListOfCollaborators();

	/**
	 * <b>Scenario:</b> {@link Object} under test has {@link Set} of collaborators
	 * <p>
	 * <b>Given</b> an object under test (denoted by an {@link InjectMocks} annotation)<br />
	 * <b>And</b> the {@link Object} under test has a {@link Set} of collaborators with generics<br />
	 * <b>And</b> the test Class defines mocks suitable types<br />
	 * <b>When</b> I setup the test<br />
	 * <b>Then</b> the mocks are injected as a {@link Set} into the {@link Object} under test<br />
	 * <b>And</b> the mocks in the {@link Set} are in alphabetical order
	 * 
	 * @see ClassWithSetOfCollaborators
	 */
	public void objectUnderTestHasSetOfCollaborators();

	/**
	 * <b>Scenario:</b> {@link Object} under test has {@link SortedSet} of collaborators
	 * <p>
	 * <b>Given</b> an {@link Object} under test (denoted by an {@link InjectMocks} annotation)<br />
	 * <b>And</b> the {@link Object} under test has a {@link SortedSet} of collaborators with generics<br />
	 * <b>And</b> the test Class defines mocks suitable types<br />
	 * <b>When</b> I setup the test<br />
	 * <b>Then</b> the mocks are injected as a {@link SortedSet} into the {@link Object} under test<br />
	 * <b>And</b> the mocks in the {@link SortedSet} are in alphabetical order<br />
	 * 
	 * @see ClassWithSortedSetOfCollaborators
	 */
	public void objectUnderTestHasSortedSetOfCollaborators();

	/**
	 * <b>Scenario:</b> {@link Object} under test has {@link Queue} of collaborators
	 * <p>
	 * <b>Given</b> an {@link Object} under test (denoted by an {@link InjectMocks} annotation)<br />
	 * <b>And</b> the {@link Object} under test has one {@link Queue} of collaborators with generics<br />
	 * <b>And</b> the test Class defines mocks suitable types<br />
	 * <b>When</b> I setup the test<br />
	 * <b>Then</b> the mocks are injected as a {@link Queue} into the object under test<br />
	 * <b>And</b> the mocks in the {@link Queue} are in alphabetical order
	 * 
	 * @see ClassWithQueueOfCollaborators
	 */
	public void objectUnderTestHasQueueOfCollaborators();

	/**
	 * <b>Scenario:</b> {@link Object} under test has more than one {@link Collection} of collaborators
	 * <p>
	 * <b>Given</b> an {@link Object} under test (denoted by an {@link InjectMocks} annotation)<br />
	 * <b>And</b> the {@link Object} under test has more than one {@link Collection} of collaborators with generics<br />
	 * <b>And</b> the test Class defines mocks of suitable types for the {@link Collection Collections} of collaborators
	 * <br />
	 * <b>When</b> I setup the test<br />
	 * <b>Then</b> the mocks are injected as {@link Collection Collections} into the object under test<br />
	 * <b>And</b> the mocks in the {@link Collection Collections} are in alphabetical order
	 * 
	 * @see ClassWithMoreThanOneCollectionOfCollaborators
	 */
	public void objectUnderTestHasMoreThanOneCollectionOfCollaborators();

	/**
	 * <b>Scenario:</b> {@link Object} under test has no {@link Collection Collections} of collaborators
	 * <p>
	 * <b>Given</b> an {@link Object} under test (denoted by an {@link InjectMocks} annotation)<br />
	 * <b>And</b> the {@link Object} under test has no {@link Collection Collections} of collaborators<br />
	 * <b>When</b> I setup the test<br />
	 * <b>Then</b> no {@link Collection Collections} of mocks are injected into the object under test
	 */
	public void objectUnderTestHasNoCollectionsOfCollaborators();

	/**
	 * <b>Scenario:</b> {@link Object} under test has {@link Collection Collections} of collaborators with no generics
	 * <p>
	 * <b>Given</b> an {@link Object} under test (denoted by an {@link InjectMocks} annotation)<br />
	 * <b>And</b> the {@link Object} under test has {@link Collection Collections} of collaborators with no generics<br />
	 * <b>When</b> I setup the test<br />
	 * <b>Then</b> no {@link Collection Collections} of mocks are injected into the object under test
	 * 
	 * @see ClassWithCollectionOfCollaboratorsWithNoGenerics
	 */
	public void classOfObjectUnderTestHasCollectionsOfCollaboratorsWithNoGenerics();
}
