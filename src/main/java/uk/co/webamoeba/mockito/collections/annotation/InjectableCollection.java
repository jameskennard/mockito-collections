package uk.co.webamoeba.mockito.collections.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

/**
 * A {@link Field} annotated with {@link InjectableCollection} is considered for injection verbatim. That is to say,
 * unlike {@link Injectable}, and {@link InjectableCollection} will be injected as a whole not as an element in a
 * Collection. This annotation is expected to be used in conjunction with {@link CollectionOfMocks}.
 * 
 * @author James Kennard
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface InjectableCollection {

}
