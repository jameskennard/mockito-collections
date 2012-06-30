import java.util.HashSet;
import java.util.Set;


/**
 * Strategy that is used to get {@link Object}s that can be placed in a {@link Collection} where the
 * Generics on that Collection are described as a particular type.
 */
public class InjectableSelectionStrategy {

	public <T> Set<T> getInjectables(Set<Object> injectables, Class<T> injectableClass) {
		Set<T> matchingInjectables = new HashSet<T>();
		for (Object object : injectables) {
			if (injectableClass.isAssignableFrom(object.getClass())) {
				matchingInjectables.add((T)object);
			}
		}
		return matchingInjectables;
	}

}
