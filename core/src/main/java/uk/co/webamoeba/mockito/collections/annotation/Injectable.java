package uk.co.webamoeba.mockito.collections.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.Collection;

import org.mockito.Mock;

/**
 * Mockito-Collections is intended primarily to inject Mockito {@link Mock Mocks} into {@link Collection Collections},
 * but using this annotation allows it to be used with other {@link Field Fields}. <i>Use of this annotation is not
 * expected to common</i>.
 * 
 * @see IgnoreInjectable
 * @author James Kennard
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Injectable {

}
