package uk.co.webamoeba.mockito.collections;

import java.util.Collection;
import java.util.Set;

/**
 * Strategy that is intended to get {@link Object}s that can be placed in a {@link Collection} where the Generics on
 * that Collection are described as a particular type.
 * 
 * @author James Kennard
 */
public interface InjectableSelectionStrategy {

    /**
     * Gets the injectables from the provided {@link Set} of injectables that can be injected into a {@link Collection}
     * where the generic type is of the specified type. In the case that there are no suitable injectables, this method
     * will return an empty {@link Set}. <b>This method must never return <code>null</code></b>.
     * 
     * @param injectables
     * @param injectableClass
     * @return
     */
    public <T> Set<T> getInjectables(Set<Object> injectables, Class<T> injectableClass);
}
