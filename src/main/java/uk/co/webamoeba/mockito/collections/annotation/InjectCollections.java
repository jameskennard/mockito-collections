package uk.co.webamoeba.mockito.collections.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.Collection;

import org.mockito.InjectMocks;
import org.mockito.Mock;

/**
 * Mockito-Collections primarily identifies {@link Field Fields} with the {@link InjectMocks} annotation as being
 * suitable to inject {@link Collection Collections}. In the event that you want Mockito-Collections to inject
 * {@link Collection Collections} into a {@link Field} that you don't want Mockito to inject {@link Mock Mocks} into,
 * you can use this annotation to identify the {@link Field}.
 * 
 * @author James Kennard
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface InjectCollections {
}
