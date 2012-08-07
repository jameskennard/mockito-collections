package uk.co.webamoeba.mockito.collections.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.Collection;

/**
 * Allows Mockito-Collections to instantiate {@link Field Fields} with a {@link Collection} and prepopulate the
 * collection with a {@link #numberOfMocks()}.
 * 
 * <pre>
 * 
 * 
 * 
 * &#064;InjectableCollectionOfMocks(numberOfMocks = 2)
 * private Collection&lt;EventListener&gt; eventListeners;
 * 
 * &#064;Before
 * public void setup() {
 *     MockitoCollectionInjector(this);
 *     assert eventListeners.size() == 2;
 * }
 * </pre>
 * 
 * @author James Kennard
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface InjectableCollectionOfMocks {

    // FIXME complete this!
    int numberOfMocks() default 0;
}
