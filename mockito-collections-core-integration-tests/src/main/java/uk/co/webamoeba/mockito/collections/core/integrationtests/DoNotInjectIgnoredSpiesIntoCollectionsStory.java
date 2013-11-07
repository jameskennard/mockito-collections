package uk.co.webamoeba.mockito.collections.core.integrationtests;

import java.util.Collection;

import org.mockito.InjectMocks;
import org.mockito.Spy;

import uk.co.webamoeba.mockito.collections.core.integrationtests.support.ClassWithCollectionOfCollaborators;

/**
 * <b>Narrative:</b> Inject {@link Collection Collections} (interfaces) of some mocks into an {@link Object} under test
 * <p>
 * <b>As a</b> developer<br />
 * <b>I want</b> to inject {@link Collection Collections} of some mocks (denoted by the {@link Spy} annotation an
 * absence of the {@link IgnoreSpyForCollections} annotation) into an {@link Object} under test (denoted by an
 * {@link InjectMocks} annotation)<br />
 * <b>So that</b> I can select which {@link Spy Spys} to inject into {@link Collection Collections}<br />
 * <b>And</b> Spy the behaviour of the collaborator<br />
 * <b>And</b> validate the behaviour of the {@link Object} under test
 * 
 * @author Matt Reines
 */
public interface DoNotInjectIgnoredSpiesIntoCollectionsStory {

	/**
	 * <b>Scenario:</b> All {@link Spy Spys} have the {@link IgnoreForCollections} annotation
	 * <p>
	 * <b>Given</b> a test Class with an object under test denoted by an {@link InjectMocks} annotation<br />
	 * <b>And</b> the Class of the {@link Object} under test has one {@link Collection} of collaborators with generics<br />
	 * <b>And</b> the test Class defines a Spy of the exact generic type<br />
	 * <b>And</b> the that Spy also has the {@link IgnoreForCollections} annotation<br />
	 * <b>When</b> I setup the JUnit test<br />
	 * <b>Then</b> the no Spys injected into the object under test
	 * 
	 * @see ClassWithCollectionOfCollaborators
	 */
	void allSpiesHaveTheIgnoreInjectableAnnotation();

	/**
	 * <b>Scenario:</b> Some {@link Spy Spys} have the {@link IgnoreForCollections} annotation
	 * <p>
	 * <b>Given</b> a test Class with an object under test denoted by an {@link InjectMocks} annotation<br />
	 * <b>And</b> the Class of the {@link Object} under test has one {@link Collection} of collaborators with generics<br />
	 * <b>And</b> the test Class defines a number of Spys of the exact generic type<br />
	 * <b>And</b> the some of those Spys also have the {@link IgnoreForCollections} annotation<br />
	 * <b>When</b> I setup the JUnit test<br />
	 * <b>Then</b> the Spys without the {@link IgnoreForCollections} annotation are injected into the object under test
	 * 
	 * @see ClassWithCollectionOfCollaborators
	 */
	void someSpiesHaveTheIgnoreInjectableAnnotation();

}
