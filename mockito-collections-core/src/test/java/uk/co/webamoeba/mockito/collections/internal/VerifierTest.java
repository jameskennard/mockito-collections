package uk.co.webamoeba.mockito.collections.internal;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.exceptions.verification.NoInteractionsWanted;
import org.mockito.exceptions.verification.WantedButNotInvoked;

import uk.co.webamoeba.mockito.collections.support.ThrowableCausedByMatcher;

/**
 * @author James Kennard
 */
public class VerifierTest {

	private Verifier verification = new Verifier();

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void shouldCollectiveVerifyGivenWantedButNotInvoked() throws IOException {
		// Given
		Closeable mock = mock(Closeable.class);
		Collection<Closeable> collection = Collections.singleton(mock);

		thrown.expect(new ThrowableCausedByMatcher(WantedButNotInvoked.class));

		// When
		verification.collectiveVerify(Closeable.class, collection).close();

		// Then
		// Exception thrown
	}

	@Test
	public void shouldCollectiveVerifyGivenWantedAndInvoked() throws IOException {
		// Given
		Closeable mock = mock(Closeable.class);
		mock.close();
		Collection<Closeable> collection = Collections.singleton(mock);

		// When
		verification.collectiveVerify(Closeable.class, collection).close();

		// Then
		// No Exception thrown
	}

	@Test
	public void shouldCollectiveVerifyGivenWantedButNotAllInvoked() throws IOException {
		// Given
		Closeable mock1 = mock(Closeable.class);
		Closeable mock2 = mock(Closeable.class);
		mock1.close();
		Collection<Closeable> collection = Arrays.asList(mock1, mock2);

		thrown.expect(new ThrowableCausedByMatcher(WantedButNotInvoked.class));

		// When
		verification.collectiveVerify(Closeable.class, collection).close();

		// Then
		// Exception thrown
	}

	@Test
	@SuppressWarnings("unchecked")
	public void shouldCollectiveVerifyNoMoreInteractions() throws IOException {
		// Given
		InputStream aMock = mock(InputStream.class);
		aMock.read();
		Collection<InputStream> collection = Arrays.<InputStream> asList(aMock);
		verification.collectiveVerify(InputStream.class, collection).read();

		// When
		verification.collectiveVerifyNoMoreInteractions(collection);

		// Then
		// No Exception thrown
	}

	@Test
	@SuppressWarnings("unchecked")
	public void shouldCollectiveVerifyNoMoreInteractionsGivenMoreInteractionsArePresent() throws IOException {
		// Given
		InputStream aMock = mock(InputStream.class);
		aMock.read();
		aMock.close();
		Collection<InputStream> collection = Arrays.<InputStream> asList(aMock);
		verification.collectiveVerify(InputStream.class, collection).read();

		// When
		try {
			verification.collectiveVerifyNoMoreInteractions(collection);

			// Then
			fail("expected exception to be thrown");
		} catch (NoInteractionsWanted e) {
			assertTrue(e.getMessage().contains("No interactions wanted"));
		}
	}

	@Test
	@SuppressWarnings("unchecked")
	public void shouldCollectiveVerifyZeroInteractions() {
		// Given
		Collection<Object> collection = Arrays.asList(mock(Object.class), mock(Object.class));

		// When
		verification.collectiveVerifyZeroInteractions(collection);

		// Then
		// No Exception thrown
	}

	@Test
	@SuppressWarnings("unchecked")
	public void shouldCollectiveVerifyZeroInteractionsGivenHasInteractions() throws IOException {
		// Given
		OutputStream aMock = mock(OutputStream.class);
		aMock.close();
		Collection<OutputStream> collection = Arrays.<OutputStream> asList(aMock);

		// When
		try {
			verification.collectiveVerifyZeroInteractions(collection);

			// Then
			fail("expected exception to be thrown");
		} catch (NoInteractionsWanted e) {
			assertTrue(e.getMessage().contains("No interactions wanted"));
		}
	}
}
