package uk.co.webamoeba.mockito.collections.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

import org.mockito.Mock;

/**
 * Annotation which when added to a {@link Field} in a test that would otherwise be considered an injectable, tells
 * Mockito-Collections not to use this as an injectable. {@link IgnoreInjectable} always takes precedence, for example a
 * {@link Field} annotated with {@link Injectable} or {@link Mock}, and annotated with {@link IgnoreInjectable} will be
 * ignored.
 * 
 * @see Injectable
 * @author James Kennard
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface IgnoreInjectable {

}
