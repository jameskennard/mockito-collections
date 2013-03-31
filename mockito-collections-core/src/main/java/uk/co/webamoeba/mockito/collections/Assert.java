package uk.co.webamoeba.mockito.collections;

import static org.mockito.Mockito.times;

import java.lang.reflect.Method;
import java.util.Collection;

import org.mockito.Mockito;
import org.mockito.cglib.proxy.MethodInterceptor;
import org.mockito.cglib.proxy.MethodProxy;
import org.mockito.internal.creation.jmock.ClassImposterizer;
import org.mockito.verification.VerificationMode;

public class Assert {

	public static <T> T collectiveVerify(Class<T> mockClass, Collection<T> collection) {
		return collectiveVerify(mockClass, collection, times(1));
	}

	public static <T> T collectiveVerify(Class<T> mockClass, Collection<T> collection, VerificationMode mode) {
		MethodInterceptor interceptor = new CollectiveVerifyMethodInterceptor(collection, mode);
		T verifier = ClassImposterizer.INSTANCE.imposterise(interceptor, mockClass);
		return verifier;
	}

	public static <T extends Object> void collectiveVerifyZeroInteractions(Collection<T>... mocks) {
		collectiveVerifyNoMoreInteractions(mocks);
	}

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
