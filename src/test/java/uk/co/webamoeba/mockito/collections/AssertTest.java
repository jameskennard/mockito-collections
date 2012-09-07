package uk.co.webamoeba.mockito.collections;

import static org.mockito.Mockito.mock;

import java.io.Closeable;
import java.io.IOException;
import java.nio.CharBuffer;
import java.util.Collection;
import java.util.Collections;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.exceptions.verification.WantedButNotInvoked;
import org.mockito.exceptions.verification.junit.ArgumentsAreDifferent;

/**
 * @author James Kennard
 */
public class AssertTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void shouldVerifyGivenVerificationOfUnusedMethod() throws IOException {
		// Given
		Closeable mock = mock(Closeable.class);
		Collection<Closeable> collection = Collections.singleton(mock);

		thrown.expect(new ThrowableCausedByMatcher(WantedButNotInvoked.class));

		// When
		Assert.verify(collection).close();

		// Then
		// Exception thrown
	}

	@Test
	public void shouldVerifyGivenVerificationOfUsedMethod() throws IOException {
		// Given
		Runnable mock = mock(Runnable.class);
		mock.run();
		Collection<Runnable> collection = Collections.singleton(mock);

		// When
		Assert.verify(collection).run();

		// Then
		// Exception thrown
	}

	@Test
	public void shouldVerifyGivenVerificationOfUsedMethodWithDifferentParameters() throws IOException {
		// Given
		Readable mock = mock(Readable.class);
		CharBuffer cb1 = mock(CharBuffer.class);
		CharBuffer cb2 = mock(CharBuffer.class);
		mock.read(cb1);
		Collection<Readable> collection = Collections.singleton(mock);

		thrown.expect(new ThrowableCausedByMatcher(ArgumentsAreDifferent.class));

		// When
		Assert.verify(collection).read(cb2);

		// Then
		// Exception thrown
	}

	@Test
	public void shouldAlwaysPass() {
		thrown.expect(new ThrowableCausedByMatcher(WantedButNotInvoked.class));
	}
}
