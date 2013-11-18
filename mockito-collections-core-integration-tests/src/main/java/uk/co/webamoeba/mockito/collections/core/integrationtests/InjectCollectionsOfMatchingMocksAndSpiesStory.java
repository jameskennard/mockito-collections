package uk.co.webamoeba.mockito.collections.core.integrationtests;

import java.util.Collection;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

import uk.co.webamoeba.mockito.collections.core.integrationtests.support.ClassWithListOfCollaborators;


/**
 * <b>Story:</b> Inject {@link Collection Collections} of mocks
 * <p>
 * <b>In order to</b> test the behaviour of an {@link Object} containing a {@link Collection} of collaborators <br />
 * <b>We will</b> inject mock {@link Object Objects} (denoted by the {@link Mock} annotation)
 * <b>And</b> inject spy {@link Object Objects} (denoted by the {@link Spy} annotation)
 * 
 * @see InjectConcreteCollectionImplementationsOfMocksIntoAnObjectUnderTestStory
 * @author James Kennard
 */public interface InjectCollectionsOfMatchingMocksAndSpiesStory {
		/**
		 * <b>Scenario:</b> Test has mocks and spies of the same type as the generics
		 * <p>
		 * <b>Given</b> an {@link Object} under test (denoted by an {@link InjectMocks} annotation)<br />
		 * <b>And</b> the {@link Object} under test has a {@link List} of collaborators with generics<br />
		 * <b>And</b> the test Class defines mocks or spies of the exact generic type<br />
		 * <b>When</b> I setup the test<br />
		 * <b>Then</b> the mocks and spies are injected as a {@link Collection} into the {@link Object} under test<br />
		 * <b>And</b> the mocks and spies in the {@link Collection} are in alphabetical order
		 * 
		 * @see ClassWithListOfCollaborators
		 */
		public void testHasMocksAndSpiesOfSameType();

		/**
		 * <b>Scenario:</b> Test has mocks and spies of the same type as the generics
		 * <p>
		 * <b>Given</b> an {@link Object} under test (denoted by an {@link InjectMocks} annotation)<br />
		 * <b>And</b> the {@link Object} under test has a {@link List} of collaborators with generics<br />
		 * <b>And</b> the test Class defines mocks of a subtype of the generic type<br />
		 * <b>When</b> I setup the test<br />
		 * <b>Then</b> the mocks and spies are injected as a {@link Collection} into the {@link Object} under test<br />
		 * <b>And</b> the mocks and spies in the {@link Collection} are in alphabetical order
		 * 
		 * @see ClassWithListOfCollaborators
		 */
		public void testHasMocksAndSpiesOfSubType();

		/**
		 * <b>Scenario:</b> Test has mocks and spies of different type to the generics
		 * <p>
		 * <b>Given</b> an {@link Object} under test (denoted by an {@link InjectMocks} annotation)<br />
		 * <b>And</b> the {@link Object} under test has a {@link List} of collaborators with generics<br />
		 * <b>And</b> the test Class defines mocks of a different type to the generic type<br />
		 * <b>When</b> I setup the test<br />
		 * <b>Then</b> no mocks and spies are injected as a {@link Collection} into the {@link Object} under test
		 * 
		 * @see ClassWithListOfCollaborators
		 */
		public void testHasMocksAndSpiesOfDifferentType();
}
