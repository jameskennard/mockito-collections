package uk.co.webamoeba.mockito.collections.core.integrationtests;

import java.util.Collection;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import uk.co.webamoeba.mockito.collections.annotation.IgnoreMockForCollections;
import uk.co.webamoeba.mockito.collections.core.integrationtests.support.ClassWithCollectionOfCollaborators;

/**
 * <b>Narrative:</b> Inject {@link Collection Collections} of some mocks into an {@link Object} under test
 * <p>
 * <b>In order to</b> test the behaviour of an {@link Object} containing a {@link Collection} of collaborators <br />
 * <b>We will</b> inject mock {@link Object Objects} (denoted by the {@link Mock} annotation) <b>As a</b> developer<br />
 * <b>I want</b> to inject {@link Collection Collections} of some mocks (denoted by the {@link Mock} annotation an
 * absence of the {@link IgnoreMockForCollections} annotation) into an {@link Object} under test (denoted by an
 * {@link InjectMocks} annotation)<br />
 * <b>So that</b> I can select which {@link Mock Mocks} to inject into {@link Collection Collections}<br />
 * <b>And</b> mock the behaviour of the collaborator<br />
 * <b>And</b> validate the behaviour of the {@link Object} under test
 * 
 * @author James Kennard
 */
public interface InjectCollectionsOfSomeMocksIntoAnObjectUnderTestStory {

	/**
	 * <b>Scenario:</b> All {@link Mock Mocks} have the {@link IgnoreMockForCollections} annotation
	 * <p>
	 * <b>Given</b> a test Class with an object under test denoted by an {@link InjectMocks} annotation<br />
	 * <b>And</b> the Class of the {@link Object} under test has one {@link Collection} of collaborators with generics<br />
	 * <b>And</b> the test Class defines a mock of the exact generic type<br />
	 * <b>And</b> the that mock also has the {@link IgnoreMockForCollections} annotation<br />
	 * <b>When</b> I setup the JUnit test<br />
	 * <b>Then</b> the no mocks injected into the object under test
	 * 
	 * @see ClassWithCollectionOfCollaborators
	 */
	void allMocksHaveTheIgnoreMockForCollectionsAnnotation();

	/**
	 * <b>Scenario:</b> Some {@link Mock Mocks} have the {@link IgnoreMockForCollections} annotation
	 * <p>
	 * <b>Given</b> a test Class with an object under test denoted by an {@link InjectMocks} annotation<br />
	 * <b>And</b> the Class of the {@link Object} under test has one {@link Collection} of collaborators with generics<br />
	 * <b>And</b> the test Class defines a number of mocks of the exact generic type<br />
	 * <b>And</b> the some of those mocks also have the {@link IgnoreMockForCollections} annotation<br />
	 * <b>When</b> I setup the JUnit test<br />
	 * <b>Then</b> the mocks without the {@link IgnoreMockForCollections} annotation are injected into the object under
	 * test
	 * 
	 * @see ClassWithCollectionOfCollaborators
	 */
	void someMocksHaveTheIgnoreMockForCollectionsAnnotation();

}
