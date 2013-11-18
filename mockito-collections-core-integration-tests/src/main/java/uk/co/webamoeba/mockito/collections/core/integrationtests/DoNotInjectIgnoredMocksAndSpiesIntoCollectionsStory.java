package uk.co.webamoeba.mockito.collections.core.integrationtests;

import java.util.Collection;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

import uk.co.webamoeba.mockito.collections.annotation.IgnoreForCollections;
import uk.co.webamoeba.mockito.collections.core.integrationtests.support.ClassWithCollectionOfCollaborators;

/**
 * <b>Narrative:</b> Do not inject mocks and spies that are ignored into {@link Collection Collections}
 * <p>
 * <b>In order to</b> be able to inject {@link Collection Collections} of mocks and spies, and still use the
 * {@link Mock} and {@link Spy} annotations on fields without injecting them into collections<br />
 * <b>We will</b> not inject {@link Mock Mocks} and {@link Spy Spies} into {@link Collection Collections} if they are
 * also annotated with {@link IgnoreForCollections}
 * </p>
 * 
 * @author James Kennard
 */
public interface DoNotInjectIgnoredMocksAndSpiesIntoCollectionsStory {

	/**
	 * <b>Scenario:</b> All {@link Mock Mocks} and {@link Spy Spies} have the {@link IgnoreForCollections} annotation
	 * <p>
	 * <b>Given</b> an {@link Object} under test (denoted by an {@link InjectMocks} annotation)<br />
	 * <b>And</b> the {@link Object} under test has one {@link Collection} of collaborators with generics<br />
	 * <b>And</b> the test defines {@link Mock Mocks} and {@link Spy Spies} of a suitable type for the generics<br />
	 * <b>And</b> the {@link Mock Mocks} and {@link Spy Spies} have the {@link IgnoreForCollections} annotation<br />
	 * <b>When</b> I setup the JUnit test<br />
	 * <b>Then</b> no {@link Collection Collections} are injected
	 * 
	 * @see ClassWithCollectionOfCollaborators
	 */
	void allMocksAndSpiesHaveTheIgnoreForCollectionsAnnotation();

	/**
	 * <b>Scenario:</b> Some {@link Mock Mocks} and some {@link Spy Spies} have the {@link IgnoreForCollections}
	 * annotation
	 * <p>
	 * <b>Given</b> an {@link Object} under test (denoted by an {@link InjectMocks} annotation)<br />
	 * <b>And</b> the {@link Object} under test has a {@link Collection} of collaborators with generics<br />
	 * <b>And</b> the test defines {@link Mock Mocks} and {@link Spy Spies} of a suitable type for the generics<br />
	 * <b>And</b> the some of those {@link Mock Mocks} have the {@link IgnoreForCollections} annotation<br />
	 * <b>And</b> the some of those {@link Spy Spies} have the {@link IgnoreForCollections} annotation<br />
	 * <b>When</b> I setup the JUnit test<br />
	 * <b>Then</b> the {@link Mock Mocks} without the {@link IgnoreForCollections} annotation are injected<br />
	 * <b>And</b> the {@link Spy Spies} without the {@link IgnoreForCollections} annotation are injected
	 * 
	 * @see ClassWithCollectionOfCollaborators
	 */
	void someMocksAndSomeSpiesHaveTheIgnoreForCollectionsAnnotation();

}
