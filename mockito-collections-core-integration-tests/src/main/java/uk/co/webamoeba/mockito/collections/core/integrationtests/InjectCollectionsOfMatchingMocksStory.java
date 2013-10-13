package uk.co.webamoeba.mockito.collections.core.integrationtests;

import java.util.Collection;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import uk.co.webamoeba.mockito.collections.core.integrationtests.support.ClassWithListOfCollaborators;

/**
 * <b>Story:</b> Inject {@link Collection Collections} of mocks that match the generics
 * <p>
 * <b>In order to</b> test the behaviour of an {@link Object} containing a {@link Collection} of collaborators with
 * generics<br />
 * <b>We will</b> inject mock {@link Object Objects} (denoted by the {@link Mock} annotation) that can be safely added
 * given the generics
 * 
 * @see InjectConcreteCollectionImplementationsOfMocksIntoAnObjectUnderTestStory
 * @author James Kennard
 */
public interface InjectCollectionsOfMatchingMocksStory {

	/**
	 * <b>Scenario:</b> Test has mocks of the same type as the generics
	 * <p>
	 * <b>Given</b> an {@link Object} under test (denoted by an {@link InjectMocks} annotation)<br />
	 * <b>And</b> the {@link Object} under test has a {@link List} of collaborators with generics<br />
	 * <b>And</b> the test Class defines mocks of the exact generic type<br />
	 * <b>When</b> I setup the test<br />
	 * <b>Then</b> the mocks are injected as a {@link Collection} into the {@link Object} under test<br />
	 * <b>And</b> the mocks in the {@link Collection} are in alphabetical order
	 * 
	 * @see ClassWithListOfCollaborators
	 */
	public void testHasMocksOfSameType();

	/**
	 * <b>Scenario:</b> Test has mocks of the same type as the generics
	 * <p>
	 * <b>Given</b> an {@link Object} under test (denoted by an {@link InjectMocks} annotation)<br />
	 * <b>And</b> the {@link Object} under test has a {@link List} of collaborators with generics<br />
	 * <b>And</b> the test Class defines mocks of a subtype of the generic type<br />
	 * <b>When</b> I setup the test<br />
	 * <b>Then</b> the mocks are injected as a {@link Collection} into the {@link Object} under test<br />
	 * <b>And</b> the mocks in the {@link Collection} are in alphabetical order
	 * 
	 * @see ClassWithListOfCollaborators
	 */
	public void testHasMocksOfSubType();

	/**
	 * <b>Scenario:</b> Test has mocks of different type to the generics
	 * <p>
	 * <b>Given</b> an {@link Object} under test (denoted by an {@link InjectMocks} annotation)<br />
	 * <b>And</b> the {@link Object} under test has a {@link List} of collaborators with generics<br />
	 * <b>And</b> the test Class defines mocks of a different type to the generic type<br />
	 * <b>When</b> I setup the test<br />
	 * <b>Then</b> no mocks are injected as a {@link Collection} into the {@link Object} under test
	 * 
	 * @see ClassWithListOfCollaborators
	 */
	public void testHasMocksOfDifferentType();
}
