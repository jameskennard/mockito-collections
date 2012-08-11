package uk.co.webamoeba.mockito.collections.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

/**
 * @author James Kennard
 */
public class AnnotatedFieldRetriever {

    /**
     * @param clazz
     *            The {@link Class} from which we want to retrieve annotated {@link Field Fields}
     * @param annotationClass
     *            The annotation to use to identify the {@link Field Fields} we want to retrieve
     * @return The {@link Field Fields} from the {@link Class} that are annotated with the {@link Annotation}
     *         {@link Class}.
     */
    public Set<Field> getAnnotatedFields(Class<?> clazz, Class<? extends Annotation> annotationClass) {
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
