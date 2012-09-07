package uk.co.webamoeba.mockito.collections;

import static org.mockito.Mockito.times;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.mockito.Mockito;
import org.mockito.cglib.proxy.MethodInterceptor;
import org.mockito.cglib.proxy.MethodProxy;
import org.mockito.internal.creation.jmock.ClassImposterizer;
import org.mockito.verification.VerificationMode;

import uk.co.webamoeba.mockito.collections.exception.MockitoCollectionsException;

public class Assert {

	private static Pattern cglibClassPattern = Pattern
			.compile("\\$?([a-zA-Z\\._0-9]+)\\$\\$EnhancerByMockitoWithCGLIB\\$\\$[0-9a-f]+");

	public static <T extends Object> T verify(Collection<T> collection) {
		return verify(collection, times(1));
	}

	public static <T extends Object> T verify(Collection<T> collection, VerificationMode mode) {
		MethodInterceptor interceptor = new VerifyMethodInterceptor(collection, mode);
		Class<T> mockedType = getMockedClass(collection);
		T verifier = ClassImposterizer.INSTANCE.imposterise(interceptor, mockedType);
		return verifier;
	}

	// TODO This feels *very* brittle
	@SuppressWarnings("unchecked")
	private static <T> Class<T> getMockedClass(Collection<T> collection) {
		Class<T> mockedType;
		String name = collection.iterator().next().getClass().getCanonicalName();
		Matcher matcher = cglibClassPattern.matcher(name);
		matcher.matches();
		try {
			mockedType = (Class<T>) Class.forName(matcher.group(1));
		} catch (ClassNotFoundException e) {
			throw new MockitoCollectionsException("Failed to create object to verify collection mocking", e);
		}
		return mockedType;
	}

	public static <T extends Object> void verifyNoMoreInteractions(Collection<T>... mocks) {
		for (Collection<T> collection : mocks) {
			for (T item : collection) {
				Mockito.verifyNoMoreInteractions(item);
			}
		}
	}

	public static <T extends Object> void verifyZeroInteractions(Collection<T>... mocks) {
		verifyNoMoreInteractions(mocks);
	}

	private static class VerifyMethodInterceptor extends VerifyCollectionInterceptor {

		private VerificationMode mode;

		public VerifyMethodInterceptor(Collection<?> collection, VerificationMode mode) {
			super(collection);
			this.mode = mode;
		}

		@Override
		protected void intercept(Object obj, Method method, Object[] args, MethodProxy proxy, Object item)
				throws Throwable {
			Object verify = Mockito.verify(item, mode);
			method.invoke(verify, args);
		}
	}

	private static abstract class VerifyCollectionInterceptor implements MethodInterceptor {

		private Collection<?> collection;

		public VerifyCollectionInterceptor(Collection<?> collection) {
			this.collection = collection;
		}

		public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
			for (Object item : collection) {
				intercept(obj, method, args, proxy, item);
			}
			return null;
		}

		protected abstract void intercept(Object obj, Method method, Object[] args, MethodProxy proxy, Object item)
				throws Throwable;
	}

}
