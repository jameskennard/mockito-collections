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
 * <b>Narrative:</b> Inject {@link Collection Collections} (interfaces) of mocks into an {@link Object} under test
 * <p>
 * <b>As a</b> developer<br />
 * <b>I want</b> to inject {@link Collection Collections} of mocks (denoted by the {@link Mock} annotation) into an
 * {@link Object} under test (denoted by an {@link InjectMocks} annotation)<br />
 * <b>So that</b> I can mock the behaviour of the collaborators<br />
 * <b>And</b> validate the behaviour of the {@link Object} under test
 * 
 * @see InjectConcreteCollectionImplementationsOfMocksIntoAnObjectUnderTestStory
 * @author James Kennard
 */
public interface InjectCollectionsOfMocksIntoAnObjectUnderTestStory {

	/**
	 * <b>Scenario:</b> Class of object under test has {@link List} of collaborators
	 * <p>
	 * <b>Given</b> a test Class with an object under test denoted by an {@link InjectMocks} annotation<br />
	 * <b>And</b> the Class of the {@link Object} under test has one {@link List} of collaborators with generics<br />
	 * <b>And</b> the test Class defines a number of mocks of the exact generic type<br />
	 * <b>And</b> the test Class defines a number of mocks of a subtype of the generic type<br />
	 * <b>And</b> the test Class defines a number of mocks that are of an entirely different type to the generic type<br />
	 * <b>When</b> I setup the JUnit test<br />
	 * <b>Then</b> the mocks are injected as a {@link List} into the object under test<br />
	 * <b>And</b> the mocks in the {@link List} are in alphabetical order
	 * 
	 * @see ClassWithListOfCollaborators
	 */
	public void classOfObjectUnderTestHasListOfCollaborators();

	/**
	 * <b>Scenario:</b> Class of object under test has {@link Set} of collaborators
	 * <p>
	 * <b>Given</b> a test Class with an object under test denoted by an {@link InjectMocks} annotation<br />
	 * <b>And</b> the Class of the {@link Object} under test has one {@link Set} of collaborators with generics<br />
	 * <b>And</b> the test Class defines a number of mocks of the exact generic type<br />
	 * <b>And</b> the test Class defines a number of mocks of a subtype of the generic type<br />
	 * <b>And</b> the test Class defines a number of mocks that are of an entirely different type to the generic type<br />
	 * <b>When</b> I setup the JUnit test<br />
	 * <b>Then</b> the mocks are injected as a {@link Set} into the object under test<br />
	 * <b>And</b> the mocks in the {@link Set} are in alphabetical order
	 * 
	 * @see ClassWithSetOfCollaborators
	 */
	public void classOfObjectUnderTestHasSetOfCollaborators();

	/**
	 * <b>Scenario:</b> Class of object under test has {@link SortedSet} of collaborators
	 * <p>
	 * <b>Given</b> a test Class with an object under test denoted by an {@link InjectMocks} annotation<br />
	 * <b>And</b> the Class of the {@link Object} under test has one {@link SortedSet} of collaborators with generics<br />
	 * <b>And</b> the test Class defines a number of mocks of the exact generic type<br />
	 * <b>And</b> the test Class defines a number of mocks of a subtype of the generic type<br />
	 * <b>And</b> the test Class defines a number of mocks that are of an entirely different type to the generic type<br />
	 * <b>When</b> I setup the JUnit test<br />
	 * <b>Then</b> the mocks are injected as a {@link SortedSet} into the object under test<br />
	 * <b>And</b> the mocks in the {@link SortedSet} are in alphabetical order<br />
	 * <b>And</b> the {@link SortedSet} of mocks is FILO sorted
	 * 
	 * @see ClassWithSortedSetOfCollaborators
	 */
	public void classOfObjectUnderTestHasSortedSetOfCollaborators();

	/**
	 * <b>Scenario:</b> Class of object under test has {@link Queue} of collaborators
	 * <p>
	 * <b>Given</b> a test Class with an object under test denoted by an {@link InjectMocks} annotation<br />
	 * <b>And</b> the Class of the {@link Object} under test has one {@link Queue} of collaborators with generics<br />
	 * <b>And</b> the test Class defines a number of mocks of the exact generic type<br />
	 * <b>And</b> the test Class defines a number of mocks of a subtype of the generic type<br />
	 * <b>And</b> the test Class defines a number of mocks that are of an entirely different type to the generic type<br />
	 * <b>When</b> I setup the JUnit test<br />
	 * <b>Then</b> the mocks are injected as a {@link Queue} into the object under test<br />
	 * <b>And</b> the mocks in the {@link Queue} are in alphabetical order
	 * 
	 * @see ClassWithQueueOfCollaborators
	 */
	public void classOfObjectUnderTestHasQueueOfCollaborators();

	/**
	 * <b>Scenario:</b> Class of object under test has more than one {@link Collection} of collaborators
	 * <p>
	 * <b>Given</b> a test Class with an object under test denoted by an {@link InjectMocks} annotation<br />
	 * <b>And</b> the Class of the {@link Object} under test has more than one {@link Collection} of collaborators with
	 * generics<br />
	 * <b>And</b> the test Class defines a number of mocks of suitable type for the {@link Collection Collections} of
	 * collaborators<br />
	 * <b>When</b> I setup the JUnit test<br />
	 * <b>Then</b> the mocks are injected as {@link Collection Collections} into the object under test<br />
	 * <b>And</b> the mocks in the {@link Collection Collections} are in alphabetical order
	 * 
	 * @see ClassWithMoreThanOneCollectionOfCollaborators
	 */
	public void classOfObjectUnderTestHasMoreThanOneCollectionOfCollaborators();

	/**
	 * <b>Scenario:</b> Class of object under test has no {@link Collection Collections} of collaborators
	 * <p>
	 * <b>Given</b> a test Class with an object under test denoted by an {@link InjectMocks} annotation<br />
	 * <b>And</b> the Class of the {@link Object} under test has no {@link Collection Collections} of collaborators<br />
	 * <b>When</b> I setup the test<br />
	 * <b>Then</b> no {@link Collection Collections} of mocks are injected into the object under test
	 */
	public void classOfObjectUnderTestHasNoCollectionsOfCollaborators();

	/**
	 * <b>Scenario:</b> Class of object under test has {@link Collection Collections} of collaborators with no generics
	 * <p>
	 * <b>Given</b> a test Class with an object under test denoted by an {@link InjectMocks} annotation<br />
	 * <b>And</b> the Class of the {@link Object} under test has {@link Collection Collections} of collaborators with no
	 * generics<br />
	 * <b>When</b> I setup the test<br />
	 * <b>Then</b> no {@link Collection Collections} of mocks are injected into the object under test
	 * 
	 * @see ClassWithCollectionOfCollaboratorsWithNoGenerics
	 */
	public void classOfObjectUnderTestHasCollectionsOfCollaboratorsWithNoGenerics();

	// @formatter:off

	/* TODO Consider these scenarios:
	 * Abstract Collection
	 * <b>Scenario:</b> Class of object under test declares an abstract {@link Collection} of collaborators
	 *
	 * Extended Collection interface that is not with the plain java library
	 * <b>Scenario:</b> Class of object under test declares an extended {@link Collection} interface of collaborators
	 */

	// @formatter:on
}
