package uk.co.webamoeba.mockito.collections;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * The default implementation of {@link InjectableSelectionStrategy}. Considers any injectable that is assignable to the
 * injectable type to be suitable for injecting into a {@link Collection} of that type.
 * 
 * @author James Kennard
 */
public class DefaultInjectableSelectionStrategy implements InjectableSelectionStrategy {

    @SuppressWarnings("unchecked")
    public <T> Set<T> getInjectables(Set<Object> injectables, Class<T> injectableClass) {
	Set<T> matchingInjectables = new HashSet<T>();
	for (Object object : injectables) {
	    if (injectableClass.isAssignableFrom(object.getClass())) {
		matchingInjectables.add((T) object);
	    }
	}
	return matchingInjectables;
    }

}
