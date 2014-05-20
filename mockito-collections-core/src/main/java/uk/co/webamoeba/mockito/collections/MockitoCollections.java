package uk.co.webamoeba.mockito.collections;

import java.util.Collection;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.verification.VerificationMode;

import uk.co.webamoeba.mockito.collections.internal.Initialiser;
import uk.co.webamoeba.mockito.collections.internal.Verifier;

/**
 * Class for Mockito-Collections that is comparable to the {@link Mockito} and {@link MockitoAnnotations} classes.
 * <p>
 * The most important methods are the {@link #initialise(Object)} and {@link #initialiseAll(Object)} methods. These
 * methods initialise the test class, setting up mocks, spies, and injecting {@link Collection Collections}. The
 * distinction between the two methods is that {@link #initialiseAll(Object)} will also execute the
 * {@link MockitoAnnotations#initMocks(Object)} method. Using {@link #initialiseAll(Object)} allows you to initialise
 * everything in a single line of code. The {@link #initialise(Object)} method is useful if you want to do any
 * additional setup between these two steps or if you want to explicitly use the {@link MockitoJUnitRunner}.
 * </p>
 * <p>
 * The following example uses {@link #initialiseAll(Object)} to setup the test:
 * </p>
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
 * 	MockitoCollections.initialiseAll(this);
 *  assert MockUtil.isMock(eventListener);
 * 	assert objectUnderTest.getEventListeners().contains(eventListener);
 * }
 * </code>
 * </pre>
 * <p>
 * The following alternative example explicitly calls {@link MockitoAnnotations#initMocks(Object)} and uses
 * {@link #initialise(Object)} to setup the test. Notice the EventListener mock is initialised prior to the call to
 * {@link MockitoCollections#initialise(Object)}:
 * </p>
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
 *  MockitoAnnotations.initMocks(this);
 *  assert MockUtil.isMock(eventListener);
 * 	MockitoCollections.initialise(this);
 * 	assert objectUnderTest.getEventListeners().contains(eventListener);
 * }
 * </code>
 * </pre>
 * <p>
 * You can also use this class to verify the behaviour of a {@link Collection} of {@link Mock mocks} or {@link Spy
 * Spies}.
 * </p>
 * 
 * <pre class="code">
 * <code class="java">
 * 	MockitoCollections.collectiveVerify(listeners).notify(event);
 * </code>
 * </pre>
 * 
 * @author James Kennard
 */
public class MockitoCollections {

	private static final Initialiser INITIALISER = new Initialiser();

	private static final Verifier VERIFIER = new Verifier();

	/**
	 * Initialises the {@link Object}
	 * 
	 * @see #initialise(Object)
	 * @param object
	 *            The object containing relevant annotations to initialize
	 */
	public static void initialiseAll(Object object) {
		MockitoAnnotations.initMocks(object);
		INITIALISER.initialise(object);
	}

	/**
	 * {@link Initialiser#initialise(Object)}
	 * 
	 * @see MockitoCollections#initialiseAll(Object)
	 * @param object
	 *            The object (containing JUnit tests) to initialize
	 */
	public static void initialise(Object object) {
		INITIALISER.initialise(object);
	}

	/**
	 * {@link Verifier#collectiveVerify(Class, Collection)}
	 * 
	 * @param mockClass
	 * @param collection
	 * @return Object used for verification of all the mocks in the supplied collection
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
	 * @return Object used for verification of all the mocks in the supplied collection
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
