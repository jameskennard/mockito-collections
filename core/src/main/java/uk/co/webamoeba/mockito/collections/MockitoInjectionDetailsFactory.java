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
 * 
 * @author James Kennard
 */
public class MockitoInjectionDetailsFactory {

    /**
     * @param object
     * @return {@link InjectionDetails} created based on the Mockito annotations {@link Mock} and {@link InjectMocks}.
     */
    public InjectionDetails createInjectionDetails(Object object) {
	Set<Object> injectees = getFieldValues(object, InjectMocks.class);
	Set<Object> injectables = getFieldValues(object, Mock.class);
	return new InjectionDetails(injectees, injectables);
    }

    private Set<Object> getFieldValues(Object object, Class<? extends Annotation> annotationClass) {
	Set<Field> fields = getAnnotatedFields(object.getClass(), annotationClass);
	return getFieldValues(object, fields);
    }

    /**
     * @param object
     *            {@link Object} from which to retrieve values
     * @param fields
     *            The {@link Field Fields} from which to retrieve values
     * @return Values of the {@link Field Fields} retrieved from the object
     */
    private Set<Object> getFieldValues(Object object, Set<Field> fields) {
	Set<Object> values = new HashSet<Object>();
	for (Field field : fields) {
	    values.add(new FieldReader(object, field).read());
	}
	return values;
    }

    /**
     * @param clazz
     *            The {@link Class} from which we want to retrieve annotated {@link Field Fields}
     * @param annotationClass
     *            The annotation to use to identify the {@link Field Fields} we want to retrieve
     * @return The {@link Field Fields} from the {@link Class} that are annotated with the {@link Annotation}
     *         {@link Class}.
     */
    private Set<Field> getAnnotatedFields(Class<?> clazz, Class<? extends Annotation> annotationClass) {
	Set<Field> mockDependentFields = new HashSet<Field>();
	while (clazz != Object.class) {
	    // We use getDeclaredFields because we want to get fields that are not otherwise visible. This means we also
	    // want to look through the inheritance hierarchy because getDeclaredFields will not do
	    // this for us
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
