package uk.co.webamoeba.mockito.collections;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * An {@link InjectableCollection} contains a complete {@link Collection} that can be injected verbatim. For example
 * given the {@link Field field} <tt>private Set<SomeCollaborator> collaborators</tt> an {@link InjectableCollection}
 * with a {@link #typeOfCollection} equal to {@link Set} and {@link #typeOfElements} equal to <tt>SomeCollaborator</tt>
 * could be used to inject a {@link #value} directly into the {@link Field}.
 * <p>
 * It is important to note that the types must be equal, the {@link #value} should not be used in instances where the
 * {@link Field fields} types are subtypes. However it should also be noted that the actual {@link #value} is likely to
 * be a subtype of {@link #typeOfCollection}, given that {@link Collection Collections} are generally declared at
 * interface level, i.e. {@link Set} as opposed to {@link HashSet}.
 * 
 * @author James Kennard
 */
public class InjectableCollection<C extends Collection, E extends Object> {

	/**
	 * The {@link Collection} that is of the specified {@link #typeOfCollection type} and contains elements of the
	 * specified {@link #typeOfElements type}.
	 */
	private C value;

	private Class<C> typeOfCollection;

	private Class<E> typeOfElements;

	public InjectableCollection(C value, Class<C> typeOfCollection, Class<E> typeOfElements) {
		this.value = value;
		this.typeOfCollection = typeOfCollection;
		this.typeOfElements = typeOfElements;
	}

	public C getValue() {
		return value;
	}

	public Class<C> getTypeOfCollection() {
		return typeOfCollection;
	}

	public Class<E> getTypeOfElements() {
		return typeOfElements;
	}

	/**
	 * Determines if this {@link InjectableCollection} matches the provided {@link InjectableCollection}. Two
	 * {@link InjectableCollection InjectableCollections} are considered to match if they have the same
	 * {@link #getTypeOfCollection() type of collection} and same {@link #getTypeOfElements() type of elements}. This is
	 * not an {@link #equals(Object)} method, it does not check for equality of the contained {@link #getValue() value}.
	 * 
	 * @param injectableCollection
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public boolean matches(InjectableCollection injectableCollection) {
		if (this == injectableCollection) {
			return true;
		}
		return getTypeOfCollection().equals(injectableCollection.getTypeOfCollection())
				&& getTypeOfElements().equals(injectableCollection.getTypeOfElements());
	}
}
