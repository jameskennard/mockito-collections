package uk.co.webamoeba.mockito.collections.annotation;

import java.lang.reflect.Field;
import java.util.Collection;

import org.mockito.InjectMocks;
import org.mockito.Mock;

/**
 * Mockito-Collections primarily identifies {@link Field Fields} with the {@link InjectMocks} annotation as being
 * injectees. In the event that you want Mockito-Collections to inject {@link Collection Collections} into a
 * {@link Field} that you don't want Mockito to inject {@link Mock Mocks} into, you can use this annotation to identify
 * the {@link Field} as an injectee.
 * 
 * @author James Kennard
 */
public @interface Injectee {
    // FIXME complete this!
}
