package uk.co.webamoeba.mockito.collections;

import java.util.Collection;
import java.util.Set;

/**
 * Strategy that is intended to get {@link Object}s that can be placed in a {@link Collection} where the Generics on
 * that Collection are described as a particular type.
 */
public interface InjectableSelectionStrategy {

    public <T> Set<T> getInjectables(Set<Object> injectables, Class<T> injectableClass);

}
