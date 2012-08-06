package uk.co.webamoeba.mockito.collections.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.Collection;

import org.mockito.Mock;

/**
 * Annotation which when added to a {@link Field} in a test with a Mockito {@link Mock} annotation tells Mockito
 * Collections not to use this mock as an injectable. That is to say, you can prevent Mockito-Collections from using
 * certain fields when injecting {@link Collection Collections}. {@link IgnoreInjectable} always takes precedence, for
 * example a {@link Field} annotated with {@link Injectable} and {@link IgnoreInjectable} will be ignored.
 * 
 * @see Injectable
 * @author James Kennard
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface IgnoreInjectable {

}
