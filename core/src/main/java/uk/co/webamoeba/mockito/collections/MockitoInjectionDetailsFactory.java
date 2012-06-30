package uk.co.webamoeba.mockito.collections;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.util.reflection.FieldReader;

/**
 * Factory that creates {@link InjectionDetails} from an {@link Object} based on the Mockito annotations
 * {@link InjectMocks} and {@link Mock}. Fields with the {@link InjectMocks} annotation are considered injectees. Fields
 * with the {@link Mock} annotation are considered injectables.
 */
public class MockitoInjectionDetailsFactory {

    public InjectionDetails createInjectionDetails(Object object) {
	Set<Object> injectees = getFieldValues(object, InjectMocks.class);
	Set<Object> injectables = getFieldValues(object, Mock.class);
	return new InjectionDetails(injectees, injectables);
    }

    private Set<Object> getFieldValues(Object object, Class<? extends Annotation> annotationClass) {
	Set<Field> fields = getAnnotatedFields(object, annotationClass);
	return getFieldValues(object, fields);
    }

    private Set<Object> getFieldValues(Object object, Set<Field> fields) {
	Set<Object> values = new HashSet<Object>();
	for (Field field : fields) {
	    values.add(new FieldReader(object, field).read());
	}
	return values;
    }

    private Set<Field> getAnnotatedFields(Object object, Class<? extends Annotation> annotationClass) {
	Set<Field> mockDependentFields = new HashSet<Field>();
	Class<?> clazz = object.getClass();
	while (clazz != Object.class) {
	    Field[] fields = clazz.getDeclaredFields();
	    for (Field field : fields) {
		if (null != field.getAnnotation(annotationClass)) {
		    mockDependentFields.add(field);
		}
	    }
	    clazz = clazz.getSuperclass();
	}
	return mockDependentFields;
    }
}
