package uk.co.webamoeba.mockito.collections.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

import org.mockito.InjectMocks;
import org.mockito.Mock;

/**
 * A {@link Field} annotated with {@link InjectableCollection} is considered for injection verbatim. That is to say,
 * unlike {@link Injectable}, and {@link InjectableCollection} will be injected as a whole not as an element in a
 * Collection. This annotation is expected to be used in conjunction with {@link CollectionOfMocks}.
 * <p>
 * There is no <i>IgnoreInjectableCollection</i> style annotation because it is expected that only {@link Field Fields}
 * annotated with {@link InjectableCollection} will ever be considered as injectableCollection candidates. This differs
 * from {@link Injectee Injectees} and {@link Injectable Injectables} where it is expected that other annotations such
 * as {@link Mock} and {@link InjectMocks} will also be used to identify candidate {@link Field Fields}.
 * 
 * @author James Kennard
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface InjectableCollection {

}
