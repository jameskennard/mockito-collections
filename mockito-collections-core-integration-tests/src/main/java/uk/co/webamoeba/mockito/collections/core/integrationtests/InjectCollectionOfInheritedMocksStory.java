package uk.co.webamoeba.mockito.collections.core.integrationtests;

import java.util.Collection;

import org.mockito.InjectMocks;

/**
 * <b>Narrative:</b> Inject {@link Collection Collections} of mocks where the mocks are inherited
 * <p>
 * <b>In order to</b> test the behaviour of an {@link Object} containing a {@link Collection} of collaborators where
 * there are inherited mocks<br />
 * <b>We will</b> inject {@link Collection Collections} of mocks using the inherited mocks
 * <p>
 * <b>Hierarchical and Alphabetical Order</b><br />
 * In relation to to {@link Collection Collections} of mocks, hierarchical and alphabetical means the parent test class'
 * mocks are first in the {@link Collection} in alphabetical order, followed by the child class' mocks, again in
 * alphabetical order. For example, given test class B extends test class A, and test class A contains suitable mocks
 * named aMock and yMock, and test class B contains suitable mocks named bMock and zMock, we would expect to them to be
 * ordered aMock, yMock, bMock and zMock.
 * 
 * @author James Kennard
 */
public interface InjectCollectionOfInheritedMocksStory {

	/**
	 * <b>Scenario:</b> Test inherits some mocks from a parent test class
	 * <p>
	 * <b>Given</b> an {@link Object} under test (denoted by an {@link InjectMocks} annotation)<br />
	 * <b>And</b> the {@link Object} under test has a {@link Collection} of collaborators with generics<br />
	 * <b>And</b> the test Class extends another test Class<br />
	 * <b>And</b> the test Class defines mocks suitable for the {@link Collection}<br />
	 * <b>And</b> the parent test Class defines mocks suitable for the {@link Collection} with names different to the
	 * child test class<br />
	 * <b>When</b> I setup the test<br />
	 * <b>Then</b> the mocks are injected as a {@link Collection} into the object under test<br />
	 * <b>And</b> the mocks in the {@link Collection} are in hierarchical and alphabetical order
	 */
	public void testClassInheritsSomeMocksFromAParentTestClass();

	/**
	 * <b>Scenario:</b> Test inherits some mocks from a parent test class with the same names
	 * <p>
	 * <b>Given</b> an object under test (denoted by an {@link InjectMocks} annotation)<br />
	 * <b>And</b> the Class of the {@link Object} under test has one {@link Collection} of collaborators with generics<br />
	 * <b>And</b> the test Class extends another test Class<br />
	 * <b>And</b> the test Class defines a number of mocks suitable for the {@link Collection}<br />
	 * <b>And</b> the parent test Class defines a number of mocks suitable for the {@link Collection} with names the
	 * same as the child classes suitable mocks<br />
	 * <b>When</b> I setup the test<br />
	 * <b>Then</b> the mocks are injected as a {@link Collection} into the object under test<br />
	 * <b>And</b> the mocks in the {@link Collection} are in hierarchical and alphabetical order
	 */
	public void testClassInheritsSomeMocksWithTheSameNamesFromAParentTestClass();
}
