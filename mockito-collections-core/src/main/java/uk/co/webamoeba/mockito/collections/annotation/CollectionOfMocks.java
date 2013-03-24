package uk.co.webamoeba.mockito.collections.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.Collection;

import org.mockito.Mock;

/**
 * Allows Mockito-Collections to instantiate {@link Field Fields} with a {@link Collection} and pre-populate the
 * {@link Collection} with a specified {@link #numberOfMocks() number of mocks}. This is similar to using the Mockito
 * {@link Mock} annotation except that the {@link Collection} itself will not be a mock, it's contents will be.
 * 
 * <pre>
 * 
 * &#064;CollectionOfMocks(numberOfMocks = 2)
 * private Collection&lt;EventListener&gt; eventListeners;
 * 
 * &#064;Before
 * public void setup() {
 * 	MockitoCollectionInjector(this);
 * 	assert eventListeners.size() == 2;
 * }
 * </pre>
 * 
 * A {@link Field} annotated with {@link CollectionOfMocks} is considered for injection verbatim. That is to say, a
 * {@link CollectionOfMocks} will be injected as a whole not as an element in a {@link Collection}.
 * 
 * @author James Kennard
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CollectionOfMocks {

	int numberOfMocks() default 1;

}
