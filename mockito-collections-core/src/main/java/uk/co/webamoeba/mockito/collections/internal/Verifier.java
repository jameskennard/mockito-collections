package uk.co.webamoeba.mockito.collections.internal;

import static org.mockito.Mockito.times;

import java.lang.reflect.Method;
import java.util.Collection;

import org.mockito.Mockito;
import org.mockito.cglib.proxy.MethodInterceptor;
import org.mockito.cglib.proxy.MethodProxy;
import org.mockito.internal.creation.jmock.ClassImposterizer;
import org.mockito.verification.VerificationMode;

/**
 * @author James Kennard
 */
// TODO Not happy with the name of this class, perhaps this should be in a MockitoCollections class?
public class Assert {

	/**
	 * Verifies certain behaviour of all the mocks in the {@link Collection} <b>occurred once</b>.
	 * <p>
	 * Alias to {@link #collectiveVerify(Class, Collection, VerificationMode)} where VerificationMode is
	 * <code>times(1)</code>, for example:
	 * 
	 * <pre class="code">
	 * <code class="java">
	 *   // Lines are synonymous
	 *   collectiveVerify(SomeClass.class, collectionOfMocks).someMethod("some arg");
	 *   collectiveVerify(SomeClass.class, collectionOfMocks, times(1)).someMethod("some arg");
	 * </code>
	 * </pre>
	 * 
	 * @see #collectiveVerify(Class, Collection, VerificationMode)
	 * @see Mockito#verify(Object)
	 * @param mockClass
	 * @param collection
	 * @return
	 */
	public static <T> T collectiveVerify(Class<T> mockClass, Collection<T> collection) {
		return collectiveVerify(mockClass, collection, times(1));
	}

	/**
	 * Verifies certain behaviour of all the mocks in the {@link Collection} occurs. Amongst others, the
	 * {@link VerificationMode} can be specified using {@link Mockito#times(int)}, {@link Mockito#never()},
	 * {@link Mockito#atLeast(int)}, {@link Mockito#atLeastOnce()}, {@link Mockito#atMost(int)}, and
	 * {@link Mockito#only()} (only acts slightly differently). For example:
	 * 
	 * <pre class="code">
	 * <code class="java">
	 *   collectiveVerify(SomeClass.class, collectionOfMocks, times(3)).method("arg");
	 *   collectiveVerify(SomeClass.class, collectionOfMocks, never()).otherMethod("other arg");
	 *   collectiveVerify(SomeClass.class, collectionOfMocks, only()).anotherMethod("another arg");
	 * </code>
	 * </pre>
	 * 
	 * @see #collectiveVerify(Class, Collection)
	 * @see Mockito#verify(Object, VerificationMode)
	 * @param mockClass
	 * @param collection
	 * @param mode
	 * @return
	 */
	public static <T> T collectiveVerify(Class<T> mockClass, Collection<T> collection, VerificationMode mode) {
		MethodInterceptor interceptor = new CollectiveVerifyMethodInterceptor(collection, mode);
		T verifier = ClassImposterizer.INSTANCE.imposterise(interceptor, mockClass);
		return verifier;
	}

	/**
	 * Checks if any of the mocks in the {@link Collection Collections} have any unverified interaction.
	 * <p>
	 * In practice this an alias to {@link #collectiveVerifyNoMoreInteractions(Collection...)}.
	 * 
	 * @param mocks
	 */
	public static <T extends Object> void collectiveVerifyZeroInteractions(Collection<T>... mocks) {
		collectiveVerifyNoMoreInteractions(mocks);
	}

	/**
	 * Checks if any of the mocks in the {@link Collection Collections} have any unverified interaction. You use this
	 * after verifying your mocks to ensure that nothing else was invoked.
	 * <p>
	 * This method will detect all unverified invocations. For example invocations that occurred before the test method,
	 * for example: in a <code>&#064;Before</code> method.
	 * 
	 * @param mocks
	 */
	public static <T extends Object> void collectiveVerifyNoMoreInteractions(Collection<T>... mocks) {
		for (Collection<T> collection : mocks) {
			for (T item : collection) {
				Mockito.verifyNoMoreInteractions(item);
			}
		}
	}

	private static class CollectiveVerifyMethodInterceptor implements MethodInterceptor {

		private Collection<?> collection;

		private VerificationMode mode;

		public CollectiveVerifyMethodInterceptor(Collection<?> collection, VerificationMode mode) {
			this.collection = collection;
			this.mode = mode;
		}

		public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
			for (Object item : collection) {
				Object verify = Mockito.verify(item, mode);
				method.invoke(verify, args);
			}
			return null;
		}
	}

}
