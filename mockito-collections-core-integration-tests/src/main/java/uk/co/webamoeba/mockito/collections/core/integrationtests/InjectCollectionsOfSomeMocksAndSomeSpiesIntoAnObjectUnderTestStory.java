package uk.co.webamoeba.mockito.collections.core.integrationtests;

import java.util.Collection;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

import uk.co.webamoeba.mockito.collections.annotation.IgnoreForCollections;

/**
 * <b>Narrative:</b> Inject {@link Collection Collections} (interfaces) of some mocks and spies into an {@link Object} under test
 * <p>
 * <b>As a</b> developer<br />
 * <b>I want</b> to inject {@link Collection Collections} of some mocks (denoted by the {@link Mock} annotation an
 * absence of the {@link IgnoreForCollections} annotation) and spies (denoted by the {@link Spy} annotation an
 * absence of the {@link IgnoreForCollections} annotation) into an {@link Object} under test (denoted by an
 * {@link InjectMocks} annotation)<br />
 * <b>So that</b> I can select which {@link Mock Mocks} or {@link Spy Spies} to inject into {@link Collection Collections}<br />
 * <b>And</b> mock the behaviour of the collaborator or spy on the behaviour of the collaborator<br />
 * <b>And</b> validate the behaviour of the {@link Object} under test
 * 
 * @author Matt Reines
 */
public interface InjectCollectionsOfSomeMocksAndSomeSpiesIntoAnObjectUnderTestStory {

}
