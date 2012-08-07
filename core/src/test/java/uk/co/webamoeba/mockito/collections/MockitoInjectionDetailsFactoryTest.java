package uk.co.webamoeba.mockito.collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.io.InputStream;
import java.io.OutputStream;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import uk.co.webamoeba.mockito.collections.annotation.IgnoreInjectable;
import uk.co.webamoeba.mockito.collections.annotation.IgnoreInjectee;
import uk.co.webamoeba.mockito.collections.annotation.Injectable;
import uk.co.webamoeba.mockito.collections.annotation.Injectee;

/**
 * @author James Kennard
 */
public class MockitoInjectionDetailsFactoryTest {

    private MockitoInjectionDetailsFactory factory = new MockitoInjectionDetailsFactory();

    @Test
    public void shouldCreateInjectionDetails() {
	// Given
	ClassWithAnnnotations object = new ClassWithAnnnotations();

	// When
	InjectionDetails injectionDetails = factory.createInjectionDetails(object);

	// Then
	assertEquals(1, injectionDetails.getInjectees().size());
	assertSame(object.injectee, injectionDetails.getInjectees().iterator().next());
	assertEquals(2, injectionDetails.getInjectables().size());
	assertTrue(injectionDetails.getInjectables().contains(object.injectable1));
	assertTrue(injectionDetails.getInjectables().contains(object.injectable2));
    }

    @Test
    public void shouldCreateInjectionDetailsGivenInheritance() {
	// Given
	ExtendedClassWithAnnnotations object = new ExtendedClassWithAnnnotations();

	// When
	InjectionDetails injectionDetails = factory.createInjectionDetails(object);

	// Then
	assertEquals(1, injectionDetails.getInjectees().size());
	assertSame(((ClassWithAnnnotations) object).injectee, injectionDetails.getInjectees().iterator().next());
	assertEquals(3, injectionDetails.getInjectables().size());
	assertTrue(injectionDetails.getInjectables().contains(object.extendedInjectable));
	assertTrue(injectionDetails.getInjectables().contains(object.injectable1));
	assertTrue(injectionDetails.getInjectables().contains(object.injectable2));
    }

    @Test
    public void shouldCreateInjectionDetailsGivenMultipleInjectMocksAnnotations() {
	// Given
	ClassWithMultipleInjectees object = new ClassWithMultipleInjectees();

	// When
	InjectionDetails injectionDetails = factory.createInjectionDetails(object);

	// Then
	assertEquals(3, injectionDetails.getInjectees().size());
	assertTrue(injectionDetails.getInjectees().contains(object.injectee1));
	assertTrue(injectionDetails.getInjectees().contains(object.injectee2));
	assertTrue(injectionDetails.getInjectees().contains(object.injectee3));
    }

    private class ClassWithAnnnotations {

	@InjectMocks
	private OutputStream injectee = mock(OutputStream.class);

	@Mock
	protected InputStream injectable1 = mock(InputStream.class);

	@Injectable
	public InputStream injectable2 = mock(InputStream.class);

	@Mock
	@IgnoreInjectable
	protected InputStream ignoredInjectable1 = mock(InputStream.class);

	@Injectable
	@IgnoreInjectable
	public InputStream ignoredInjectable2 = mock(InputStream.class);

	@InjectMocks
	@IgnoreInjectee
	InputStream ignoredInjectee1 = mock(InputStream.class);

	@Injectee
	@IgnoreInjectee
	public InputStream ignoredInjectee2 = mock(InputStream.class);
    }

    private class ExtendedClassWithAnnnotations extends ClassWithAnnnotations {

	@Mock
	private OutputStream extendedInjectable = mock(OutputStream.class);
    }

    private class ClassWithMultipleInjectees {

	@InjectMocks
	OutputStream injectee1 = mock(OutputStream.class);

	@InjectMocks
	private InputStream injectee2 = mock(InputStream.class);

	@Injectee
	public InputStream injectee3 = mock(InputStream.class);
    }
}
