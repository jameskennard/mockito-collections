package uk.co.webamoeba.mockito.collections.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

import org.mockito.Mock;

/**
 * Annotation which when added to a {@link Field} in a test that would otherwise be considered for injection, tells
 * Mockito-Collections not to inject this {@link Mock}. {@link IgnoreForCollections} always takes precedence, for
 * example a {@link Field} annotated with {@link Mock}, and annotated with {@link IgnoreForCollections} will be
 * ignored.
 * 
 * @author James Kennard
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface IgnoreForCollections {

}
