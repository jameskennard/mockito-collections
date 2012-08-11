package uk.co.webamoeba.mockito.collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Collections;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import uk.co.webamoeba.mockito.collections.annotation.IgnoreInjectable;
import uk.co.webamoeba.mockito.collections.annotation.IgnoreInjectee;
import uk.co.webamoeba.mockito.collections.annotation.Injectable;
import uk.co.webamoeba.mockito.collections.annotation.Injectee;
import uk.co.webamoeba.mockito.collections.util.AnnotatedFieldRetriever;

/**
 * @author James Kennard
 */
@RunWith(MockitoJUnitRunner.class)
public class MockitoInjectionDetailsFactoryTest {

    @InjectMocks
    private MockitoInjectionDetailsFactory factory;

    @Mock
    private AnnotatedFieldRetriever annotatedFieldRetriever;

    @Test
    public void shouldCreateInjectionDetailsGivenInjectMocks() {
	// Given
	ClassWithAnnnotations object = new ClassWithAnnnotations();
	Field injecteeField = getField(object.getClass(), "injectee1");
	given(annotatedFieldRetriever.getAnnotatedFields(object.getClass(), InjectMocks.class)).willReturn(
		Collections.singleton(injecteeField));

	// When
	InjectionDetails injectionDetails = factory.createInjectionDetails(object);

	// Then
	assertEquals(0, injectionDetails.getInjectables().size());
	assertEquals(1, injectionDetails.getInjectees().size());
	assertTrue(injectionDetails.getInjectees().contains(object.injectee1));
    }

    @Test
    public void shouldCreateInjectionDetailsGivenInjectee() {
	// Given
	ClassWithAnnnotations object = new ClassWithAnnnotations();
	Field injecteeField = getField(object.getClass(), "injectee2");
	given(annotatedFieldRetriever.getAnnotatedFields(object.getClass(), Injectee.class)).willReturn(
		Collections.singleton(injecteeField));

	// When
	InjectionDetails injectionDetails = factory.createInjectionDetails(object);

	// Then
	assertEquals(0, injectionDetails.getInjectables().size());
	assertEquals(1, injectionDetails.getInjectees().size());
	assertTrue(injectionDetails.getInjectees().contains(object.injectee2));
    }

    @Test
    public void shouldCreateInjectionDetailsGivenIgnoredInjectees() {
	// Given
	ClassWithAnnnotations object = new ClassWithAnnnotations();
	Field injecteeField1 = getField(object.getClass(), "injectee1");
	Field injecteeField2 = getField(object.getClass(), "injectee2");
	given(annotatedFieldRetriever.getAnnotatedFields(object.getClass(), Injectee.class)).willReturn(
		Collections.singleton(injecteeField1));
	given(annotatedFieldRetriever.getAnnotatedFields(object.getClass(), InjectMocks.class)).willReturn(
		Collections.singleton(injecteeField2));
	given(annotatedFieldRetriever.getAnnotatedFields(object.getClass(), IgnoreInjectee.class)).willReturn(
		Collections.singleton(injecteeField2));

	// When
	InjectionDetails injectionDetails = factory.createInjectionDetails(object);

	// Then
	assertEquals(0, injectionDetails.getInjectables().size());
	assertEquals(1, injectionDetails.getInjectees().size());
	assertTrue(injectionDetails.getInjectees().contains(object.injectee1));
    }

    @Test
    public void shouldCreateInjectionDetailsGivenMock() {
	// Given
	ClassWithAnnnotations object = new ClassWithAnnnotations();
	Field injectableField = getField(object.getClass(), "injectable1");
	given(annotatedFieldRetriever.getAnnotatedFields(object.getClass(), Mock.class)).willReturn(
		Collections.singleton(injectableField));

	// When
	InjectionDetails injectionDetails = factory.createInjectionDetails(object);

	// Then
	assertEquals(0, injectionDetails.getInjectees().size());
	assertEquals(1, injectionDetails.getInjectables().size());
	assertTrue(injectionDetails.getInjectables().contains(object.injectable1));
    }

    @Test
    public void shouldCreateInjectionDetailsGivenInjectable() {
	// Given
	ClassWithAnnnotations object = new ClassWithAnnnotations();
	Field injectableField = getField(object.getClass(), "injectable2");
	given(annotatedFieldRetriever.getAnnotatedFields(object.getClass(), Injectable.class)).willReturn(
		Collections.singleton(injectableField));

	// When
	InjectionDetails injectionDetails = factory.createInjectionDetails(object);

	// Then
	assertEquals(0, injectionDetails.getInjectees().size());
	assertEquals(1, injectionDetails.getInjectables().size());
	assertTrue(injectionDetails.getInjectables().contains(object.injectable2));
    }

    @Test
    public void shouldCreateInjectionDetailsGivenIgnoredInjectables() {
	// Given
	ClassWithAnnnotations object = new ClassWithAnnnotations();
	Field injectableField1 = getField(object.getClass(), "injectable1");
	Field injectableField2 = getField(object.getClass(), "injectable2");
	given(annotatedFieldRetriever.getAnnotatedFields(object.getClass(), Mock.class)).willReturn(
		Collections.singleton(injectableField1));
	given(annotatedFieldRetriever.getAnnotatedFields(object.getClass(), Injectable.class)).willReturn(
		Collections.singleton(injectableField2));
	given(annotatedFieldRetriever.getAnnotatedFields(object.getClass(), IgnoreInjectable.class)).willReturn(
		Collections.singleton(injectableField1));

	// When
	InjectionDetails injectionDetails = factory.createInjectionDetails(object);

	// Then
	assertEquals(0, injectionDetails.getInjectees().size());
	assertEquals(1, injectionDetails.getInjectables().size());
	assertTrue(injectionDetails.getInjectables().contains(object.injectable2));
    }

    private Field getField(Class<?> clazz, String name) {
	Field field;
	try {
	    field = clazz.getDeclaredField(name);
	} catch (Exception e) {
	    throw new IllegalArgumentException("No such field exists");
	}
	return field;
    }

    private class ClassWithAnnnotations {

	Object injectee1 = mock(Object.class);

	private Object injectee2 = mock(Object.class);

	protected Object injectable1 = mock(InputStream.class);

	public Object injectable2 = mock(InputStream.class);
    }
}
