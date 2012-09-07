package uk.co.webamoeba.mockito.collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.EventListener;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.util.MockUtil;

import uk.co.webamoeba.mockito.collections.annotation.CollectionOfMocks;
import uk.co.webamoeba.mockito.collections.annotation.IgnoreInjectable;
import uk.co.webamoeba.mockito.collections.annotation.IgnoreInjectee;
import uk.co.webamoeba.mockito.collections.annotation.Injectable;

/**
 * @author James Kennard
 */
public class MockitoCollectionAnnotationIntegrationTest {

	private MockUtil mockUtil = new MockUtil();

	@Test
	public void shouldInject() {
		// Given
		MockitoAnnotatedClass object = new MockitoAnnotatedClass();

		// When
		MockitoCollectionAnnotations.inject(object);

		// Then
		assertNotNull(object.injectCollectionsClass.outputStreams);
		assertNotNull(object.injectCollectionsClass.inputStreams);
		assertEquals(2, object.injectCollectionsClass.outputStreams.size());
		assertEquals(1, object.injectCollectionsClass.inputStreams.size());
		assertTrue(object.injectCollectionsClass.outputStreams.contains(object.outputStream1));
		assertTrue(object.injectCollectionsClass.outputStreams.contains(object.outputStream2));
		assertTrue(object.injectCollectionsClass.inputStreams.contains(object.inputStream1));
		assertCollectionOfMocks(object.eventListeners1, 2);
		assertSame(object.eventListeners1, object.injectCollectionsClass.eventListeners);

		assertNull(object.injectCollectionsClass2.outputStreams);
		assertNull(object.injectCollectionsClass2.inputStreams);
		assertNull(object.injectCollectionsClass2.eventListeners);
	}

	@SuppressWarnings("rawtypes")
	private void assertCollectionOfMocks(Collection collection, int expectedNumberOfMocks) {
		assertEquals(expectedNumberOfMocks, collection.size());
		for (Object object : collection) {
			assertTrue(mockUtil.isMock(object));
		}
	}

	private class MockitoAnnotatedClass {

		@InjectMocks
		private InjectCollectionsClass injectCollectionsClass = new InjectCollectionsClass();

		@InjectMocks
		@IgnoreInjectee
		private InjectCollectionsClass injectCollectionsClass2 = new InjectCollectionsClass();

		@Mock
		private OutputStream outputStream1 = mock(OutputStream.class);

		@Mock
		private OutputStream outputStream2 = mock(OutputStream.class);

		@Injectable
		private InputStream inputStream1 = mock(InputStream.class);

		@Injectable
		@IgnoreInjectable
		@SuppressWarnings("unused")
		private InputStream inputStream2 = mock(InputStream.class);

		@CollectionOfMocks(numberOfMocks = 2)
		private List<EventListener> eventListeners1;
	}

	private class InjectCollectionsClass {

		private Set<OutputStream> outputStreams;

		private Set<InputStream> inputStreams;

		private List<EventListener> eventListeners;
	}
}
