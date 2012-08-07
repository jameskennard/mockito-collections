package uk.co.webamoeba.mockito.collections.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

import org.mockito.InjectMocks;

/**
 * Annotation which when added to a {@link Field} in a test that would otherwise be considered an injectee tells
 * Mockito-Collections not to use this as an injectee. {@link IgnoreInjectee} always takes precedence, for example a
 * {@link Field} annotated with {@link Injectee} or {@link InjectMocks}, and annotated with {@link IgnoreInjectee} will
 * be ignored.
 * 
 * @author James Kennard
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface IgnoreInjectee {
    // FIXME complete this!
}
