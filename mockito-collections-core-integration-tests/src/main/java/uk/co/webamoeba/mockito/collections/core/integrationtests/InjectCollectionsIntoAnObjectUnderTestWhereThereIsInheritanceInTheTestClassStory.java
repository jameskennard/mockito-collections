package uk.co.webamoeba.mockito.collections.core.integrationtests;

import java.util.Collection;

import org.mockito.InjectMocks;
import org.mockito.Mock;

/**
 * <b>Narrative:</b> Inject {@link Collection Collections} of mocks into an {@link Object} under test where there is
 * inheritance in the test class
 * <p>
 * <b>As a</b> developer<br />
 * <b>I want</b> to inject {@link Collection Collections} of mocks (denoted by the {@link Mock} annotation) into an
 * {@link Object} under test (denoted by an {@link InjectMocks} annotation) where there is inheritance in the test class
 * <br />
 * <b>So that</b> I can mock the behaviour of the inherited mock collaborators<br />
 * <b>And</b> validate the behaviour of the {@link Object} under test
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
public interface InjectCollectionsIntoAnObjectUnderTestWhereThereIsInheritanceInTheTestClassStory {

	/**
	 * <b>Scenario:</b> Test Class inherits some mocks from a parent test class
	 * <p>
	 * <b>Given</b> a test Class with an object under test denoted by an {@link InjectMocks} annotation<br />
	 * <b>And</b> the Class of the {@link Object} under test has one {@link Collection} of collaborators with generics<br />
	 * <b>And</b> the test Class extends another test Class<br />
	 * <b>And</b> the test Class defines a number of mocks suitable for the {@link Collection}<br />
	 * <b>And</b> the parent test Class defines a number of mocks suitable for the {@link Collection} with names
	 * different to the child test class<br />
	 * <b>When</b> I setup the test<br />
	 * <b>Then</b> the mocks are injected as a {@link Collection} into the object under test<br />
	 * <b>And</b> the mocks in the {@link Collection} are in hierarchical and alphabetical order
	 */
	public void testClassInheritsSomeMocksFromAParentTestClass();

	/**
	 * <b>Scenario:</b> Test Class inherits some mocks from a parent test class with the same names
	 * <p>
	 * <b>Given</b> a test Class with an object under test denoted by an {@link InjectMocks} annotation<br />
	 * <b>And</b> the Class of the {@link Object} under test has one {@link Collection} of collaborators with generics<br />
	 * <b>And</b> the test Class extends another test Class<br />
	 * <b>And</b> the test Class defines a number of mocks suitable for the {@link Collection}<br />
	 * <b>And</b> the parent test Class defines a number of mocks suitable for the {@link Collection} with names the
	 * same as the child classes suitable mocks<br />
	 * <b>When</b> I setup the test<br />
	 * <b>Then</b> the mocks are injected as a {@link Collection} into the object under test<br />
	 * <b>And</b> the mocks in the {@link Collection} are in hierarchical and alphabetical order
	 */
	public void testClassInheritsSomeMocksFromAParentTestClassWithTheSameNames();
}
