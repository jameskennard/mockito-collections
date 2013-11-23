package uk.co.webamoeba.mockito.collections.core.integrationtests;

import java.util.Collection;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

import uk.co.webamoeba.mockito.collections.core.integrationtests.support.ClassWithCollectionOfCollaborators;

/**
 * <b>Story:</b> Inject {@link Collection Collections} of mocks
 * <p>
 * <b>In order to</b> test the behaviour of an {@link Object} containing a {@link Collection} of collaborators <br />
 * <b>We will</b> inject mock {@link Object Objects} (denoted by the {@link Mock} annotation) <b>And</b> inject spy
 * {@link Object Objects} (denoted by the {@link Spy} annotation)
 * 
 * @see InjectConcreteCollectionImplementationsOfMocksIntoAnObjectUnderTestStory
 * @author James Kennard
 */
public interface InjectMocksAndSpiesIntoCollectionsStory {

	/**
	 * <b>Scenario:</b> Test has mocks and spies of the exact type as the generics
	 * <p>
	 * <b>Given</b> an {@link Object} under test (denoted by an {@link InjectMocks} annotation)<br />
	 * <b>And</b> the {@link Object} under test has a {@link Collection} of collaborators with generics<br />
	 * <b>And</b> the test Class defines mocks and spies of the exact generic type<br />
	 * <b>When</b> I setup the test<br />
	 * <b>Then</b> the mocks and spies are injected as a {@link Collection} into the {@link Object} under test<br />
	 * <b>And</b> the mocks and spies in the {@link Collection} are in alphabetical order
	 * 
	 * @see ClassWithCollectionOfCollaborators
	 */
	public void testHasMocksAndSpiesOfExactType();

	/**
	 * <b>Scenario:</b> Test has mocks and spies that are a subtype of the generics
	 * <p>
	 * <b>Given</b> an {@link Object} under test (denoted by an {@link InjectMocks} annotation)<br />
	 * <b>And</b> the {@link Object} under test has a {@link Collection} of collaborators with generics<br />
	 * <b>And</b> the test Class defines mocks and spies of a subtype of the generic type<br />
	 * <b>When</b> I setup the test<br />
	 * <b>Then</b> the mocks and spies are injected as a {@link Collection} into the {@link Object} under test<br />
	 * <b>And</b> the mocks and spies in the {@link Collection} are in alphabetical order
	 * 
	 * @see ClassWithCollectionOfCollaborators
	 */
	public void testHasMocksAndSpiesOfSubType();

	/**
	 * <b>Scenario:</b> Test has mocks and spies of different types to the generics
	 * <p>
	 * <b>Given</b> an {@link Object} under test (denoted by an {@link InjectMocks} annotation)<br />
	 * <b>And</b> the {@link Object} under test has a {@link Collection} of collaborators with generics<br />
	 * <b>And</b> the test Class defines mocks and spies of a different type to the generic type<br />
	 * <b>When</b> I setup the test<br />
	 * <b>Then</b> no mocks and spies are injected as a {@link Collection} into the {@link Object} under test
	 * 
	 * @see ClassWithCollectionOfCollaborators
	 */
	public void testHasMocksAndSpiesOfDifferentType();
}
