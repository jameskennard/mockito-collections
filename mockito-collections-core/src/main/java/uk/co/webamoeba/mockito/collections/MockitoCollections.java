package uk.co.webamoeba.mockito.collections;

import java.util.Collection;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.verification.VerificationMode;

import uk.co.webamoeba.mockito.collections.annotation.CollectionOfMocks;
import uk.co.webamoeba.mockito.collections.internal.Initialiser;
import uk.co.webamoeba.mockito.collections.internal.Verifier;

/**
 * Class for Mockito-Collections that is comparable to the {@link Mockito} and {@link MockitoAnnotations} classes.
 * <p>
 * The most important method is the {@link #initialise(Object)} method. This method acts in a similar way to
 * {@link MockitoAnnotations#initMocks(Object)}. Use this method to initialise the Mockito-Collections annotations, for
 * example:
 * 
 * <pre class="code">
 * <code class="java">
 * &#064;InjectMocks
 * private MyClassWithEventListeners objectUnderTest;
 * 
 * &#064;Mock
 * private EventListener eventListener;
 * 
 * &#064;Before
 * public void setup() {
 * 	MockitoCollections.initialise(this);
 * 	assert objectUnderTest.getEventListeners().contains(eventListener);
 * }
 * </code>
 * </pre>
 * <p>
 * You can use this class to verify the behaviour of a {@link Collection} of {@link Mock mocks}. The {@link Collection}
 * of mocks can be any {@link Collection} of {@link Mock mocks}, that is to say, it does not have to be from a
 * {@link CollectionOfMocks} field. For example:
 * 
 * <pre class="code">
 * <code class="java">
 * 	MockitoCollections.verify(listeners).notify(event);
 * </code>
 * </pre>
 * 
 * @author James Kennard
 */
public class MockitoCollections {

	private static Initialiser INITIALISER = new Initialiser();

	private static Verifier VERIFIER = new Verifier();

	/**
	 * {@link Initialiser#initialise(Object)}
	 * 
	 * @param object
	 */
	public static void initialise(Object object) {
		INITIALISER.initialise(object);
	}

	/**
	 * {@link Verifier#collectiveVerify(Class, Collection)}
	 * 
	 * @param mockClass
	 * @param collection
	 * @return
	 */
	public static <T> T collectiveVerify(Class<T> mockClass, Collection<T> collection) {
		return VERIFIER.collectiveVerify(mockClass, collection);
	}

	/**
	 * {@link Verifier#collectiveVerify(Class, Collection, VerificationMode)}
	 * 
	 * @param mockClass
	 * @param collection
	 * @param mode
	 * @return
	 */
	public static <T> T collectiveVerify(Class<T> mockClass, Collection<T> collection, VerificationMode mode) {
		return VERIFIER.collectiveVerify(mockClass, collection, mode);
	}

	/**
	 * {@link Verifier#collectiveVerifyZeroInteractions(Collection...)}
	 * 
	 * @param mocks
	 */
	public static <T> void collectiveVerifyZeroInteractions(Collection<T>... mocks) {
		VERIFIER.collectiveVerifyZeroInteractions(mocks);
	}

	/**
	 * {@link Verifier#collectiveVerifyNoMoreInteractions(Collection...)}
	 * 
	 * @param mocks
	 */
	public static <T> void collectiveVerifyNoMoreInteractions(Collection<T>... mocks) {
		VERIFIER.collectiveVerifyNoMoreInteractions(mocks);
	}
}
