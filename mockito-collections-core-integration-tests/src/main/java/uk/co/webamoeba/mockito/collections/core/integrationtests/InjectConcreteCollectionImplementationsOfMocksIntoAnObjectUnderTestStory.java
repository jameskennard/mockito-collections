package uk.co.webamoeba.mockito.collections.core.integrationtests;

import java.util.Collection;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import uk.co.webamoeba.mockito.collections.core.integrationtests.support.ClassWithConcreteCollectionOfCollaborators;
import uk.co.webamoeba.mockito.collections.core.integrationtests.support.ClassWithConcreteCollectionWithInitialCapacityConstructorOfCollaborators;
import uk.co.webamoeba.mockito.collections.core.integrationtests.support.ConcreteCollection;
import uk.co.webamoeba.mockito.collections.core.integrationtests.support.ConcreteCollectionWithInitialCapacityConstructor;

/**
 * <b>Narrative:</b> Inject concrete {@link Collection} implementations of mocks into an {@link Object} under test
 * <p>
 * <b>As a</b> developer<br />
 * <b>I want</b> to inject concrete implementations of {@link Collection Collections} of mocks (denoted by the
 * {@link Mock} annotation) into an {@link Object} under test (denoted by an {@link InjectMocks} annotation)<br />
 * <b>So that</b> I can mock the behaviour of the collaborators<br />
 * <b>And</b> validate the behaviour of the {@link Object} under test
 * 
 * @author James Kennard
 */
public interface InjectConcreteCollectionImplementationsOfMocksIntoAnObjectUnderTestStory {

	/**
	 * <b>Scenario:</b> Class of object under test declares a concrete {@link Collection} with a default constructor of
	 * collaborators
	 * <p>
	 * <b>Given</b> a test Class with an object under test denoted by an {@link InjectMocks} annotation<br />
	 * <b>And</b> the Class of the {@link Object} under test declares one {@link Collection} of collaborators with
	 * generics<br />
	 * <b>And</b> the {@link Collection} is declared as a concrete implementation<br />
	 * <b>And</b> the implementation has a default constructor<br />
	 * <b>And</b> the test Class defines a number of mocks of the generic type<br />
	 * <b>When</b> I setup the JUnit test<br />
	 * <b>Then</b> the mocks are injected as a into the object under test<br />
	 * <b>And</b> they are injected in a new instance of the concrete Collection< br/>
	 * <b>And</b> the mocks in the {@link Collection} are in alphabetical order (this only applies if the implementation
	 * retains insertion order)
	 * 
	 * @see ConcreteCollection
	 * @see ClassWithConcreteCollectionOfCollaborators
	 */
	public void classOfObjectUnderTestDeclaresAConcreteCollectionWithADefaultConstructorOfCollaborators();

	/**
	 * <b>Scenario:</b> Class of object under test declares a concrete {@link Collection} with an initial capacity
	 * constructor of collaborators
	 * <p>
	 * <b>Given</b> a test Class with an object under test denoted by an {@link InjectMocks} annotation<br />
	 * <b>And</b> the Class of the {@link Object} under test declares one {@link Collection} of collaborators with
	 * generics<br />
	 * <b>And</b> the {@link Collection} is declared as a concrete implementation<br />
	 * <b>And</b> the implementation has a constructor with a integer initial capacity argument<br />
	 * <b>And</b> the test Class defines a number of mocks of the generic type<br />
	 * <b>When</b> I setup the JUnit test<br />
	 * <b>Then</b> the mocks are injected as a into the object under test<br />
	 * <b>And</b> they are injected in a new instance of the concrete Collection< br/>
	 * <b>And</b> the mocks in the {@link Collection} are in alphabetical order (this only applies if the implementation
	 * retains insertion order)
	 * 
	 * @see ConcreteCollectionWithInitialCapacityConstructor
	 * @see ClassWithConcreteCollectionWithInitialCapacityConstructorOfCollaborators
	 */
	public void classOfObjectUnderTestDeclaresAConcreteCollectionWithAnInitialCapacityConstructorOfCollaborators();
}
