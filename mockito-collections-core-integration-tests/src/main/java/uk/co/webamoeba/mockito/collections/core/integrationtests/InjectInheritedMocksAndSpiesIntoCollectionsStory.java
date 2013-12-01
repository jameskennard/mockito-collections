package uk.co.webamoeba.mockito.collections.core.integrationtests;

import java.util.Collection;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

/**
 * <b>Narrative:</b> Inject inherited mocks and spies into {@link Collection Collections}
 * <p>
 * <b>In order to</b> take advantage of inheritance within test classes when testing the behaviour of an {@link Object}
 * containing a {@link Collection} of collaborators<br />
 * <b>We will</b> inject {@link Collection Collections} populating them with inherited mock {@link Object Objects}
 * (denoted by the {@link Mock} annotation) <b>And</b> inject spy {@link Object Objects} (denoted by the {@link Spy}
 * annotation).
 * <p>
 * <strong>Hierarchical and Alphabetical Order</strong><br />
 * In relation to the injected {@link Collection Collections}, hierarchical and alphabetical means the parent test
 * class' mocks are first in the {@link Collection} in alphabetical order, followed by the child class' mocks, again in
 * alphabetical order. For example, given test class B extends test class A, and test class A contains suitable
 * mocks/spies named aMock, yMock, and test class B contains suitable mocks/spies named bMock and zMock, we would expect
 * them to be ordered aMock, yMock, bMock and zMock.
 * 
 * @author James Kennard
 */
public interface InjectInheritedMocksAndSpiesIntoCollectionsStory {

	/**
	 * <b>Scenario:</b> Test inherits some mocks and spies from a parent class
	 * <p>
	 * <b>Given</b> an {@link Object} under test (denoted by an {@link InjectMocks} annotation)<br />
	 * <b>And</b> the {@link Object} under test has a {@link Collection} of collaborators with generics<br />
	 * <b>And</b> the test Class extends another test Class<br />
	 * <b>And</b> the test Class defines mocks and spies suitable for the {@link Collection}<br />
	 * <b>And</b> the parent test Class defines mocks and spies suitable for the {@link Collection} with names different
	 * to the child test class<br />
	 * <b>When</b> I setup the test<br />
	 * <b>Then</b> the mocks and spies are injected as a {@link Collection} into the object under test<br />
	 * <b>And</b> the mocks and spies in the {@link Collection} are in hierarchical and alphabetical order
	 */
	public void testInheritsSomeMocksAndSpiesFromAParentClass();

	/**
	 * <b>Scenario:</b> Test inherits some mocks and spies from a parent test class with the same names
	 * <p>
	 * <b>Given</b> an object under test (denoted by an {@link InjectMocks} annotation)<br />
	 * <b>And</b> the Class of the {@link Object} under test has one {@link Collection} of collaborators with generics<br />
	 * <b>And</b> the test Class extends a parent Class<br />
	 * <b>And</b> the parent Class defines a number of mocks and spies suitable for the {@link Collection}<br />
	 * <b>And</b> the test Class defines mocks and spies with the same names as the parent class<br />
	 * <b>When</b> I setup the test<br />
	 * <b>Then</b> the mocks and spies are injected as a {@link Collection} into the object under test<br />
	 * <b>And</b> the mocks and spies in the {@link Collection} are in hierarchical and alphabetical order
	 */
	public void testInheritsSomeMocksAndSpiesWithTheSameNamesFromAParentTestClass();
}
