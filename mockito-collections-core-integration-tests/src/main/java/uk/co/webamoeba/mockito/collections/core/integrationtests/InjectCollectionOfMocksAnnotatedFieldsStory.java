package uk.co.webamoeba.mockito.collections.core.integrationtests;

import java.util.Collection;

import org.mockito.InjectMocks;

import uk.co.webamoeba.mockito.collections.annotation.CollectionOfMocks;
import uk.co.webamoeba.mockito.collections.core.integrationtests.support.ClassWithListOfCollaborators;

/**
 * <b>Story:</b> Inject {@link CollectionOfMocks} annotated fields
 * <p>
 * <b>In order to</b> test the behaviour of an {@link Object} containing a {@link Collection} of collaborators <br />
 * <b>We will</b> provide an annotation for use in tests to identify {@link Collection} fields in the test we want to
 * initialise and inject into the Object under test.
 * 
 * @see InjectCollectionsOfMocksStory
 * @author James Kennard
 */
public interface InjectCollectionOfMocksAnnotatedFieldsStory {

	/**
	 * <b>Scenario:</b> test has a {@link Collection} of mocks denoted by the {@link CollectionOfMocks}
	 * <p>
	 * <b>Given</b> a test with a {@link Collection} field with generics annotated with {@link CollectionOfMocks}
	 * <b>And</a> the {@link Object} under test (denoted by an {@link InjectMocks} annotation) has a {@link Collection}
	 * of collaborators of the same type<br />
	 * <b>When</b> I setup the test<br />
	 * <b>Then</b> the field in the test is initialised with a {@link Collection} of one mock <b>And</b> the mocks are
	 * injected into the {@link Object} under test
	 * 
	 * @see ClassWithListOfCollaborators
	 */
	public void objectUnderTestHasCollectionOfCollaborators();

}
