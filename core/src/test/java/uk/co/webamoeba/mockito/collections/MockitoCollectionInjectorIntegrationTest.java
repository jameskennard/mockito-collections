package uk.co.webamoeba.mockito.collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

/**
 * @author James Kennard
 */
public class MockitoCollectionInjectorIntegrationTest {

    @Test
    public void shouldInject() {
	// Given
	MockitoAnnotatedClass object = new MockitoAnnotatedClass();

	// When
	MockitoCollectionInjector.inject(object);

	// Then
	assertNotNull(object.injecteeClass.outputStreams);
	assertNotNull(object.injecteeClass.inputStreams);
	assertEquals(2, object.injecteeClass.outputStreams.size());
	assertEquals(1, object.injecteeClass.inputStreams.size());
	assertTrue(object.injecteeClass.outputStreams.contains(object.outputStream1));
	assertTrue(object.injecteeClass.outputStreams.contains(object.outputStream2));
	assertTrue(object.injecteeClass.inputStreams.contains(object.inputStream));
    }

    private class MockitoAnnotatedClass {

	@InjectMocks
	private InjecteeClass injecteeClass = new InjecteeClass();

	@Mock
	private OutputStream outputStream1 = mock(OutputStream.class);

	@Mock
	private OutputStream outputStream2 = mock(OutputStream.class);

	@Mock
	private InputStream inputStream = mock(InputStream.class);
    }

    private class InjecteeClass {

	private Set<OutputStream> outputStreams;

	private Set<InputStream> inputStreams;
    }
}
