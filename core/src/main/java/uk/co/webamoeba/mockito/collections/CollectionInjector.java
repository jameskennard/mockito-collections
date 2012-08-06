package uk.co.webamoeba.mockito.collections;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Set;

import uk.co.webamoeba.mockito.collections.util.FieldValueMutator;

/**
 * The {@link CollectionInjector} is responsible for performing the injection of injectees into injectables. The
 * collaborators are set on instantiation so as to allow different behaviour as required.
 * 
 * @author James Kennard
 */
public class CollectionInjector {

    private CollectionFactory collectionFactory;

    private InjectableSelectionStrategy strategy;

    private GenericCollectionTypeResolver genericCollectionTypeResolver;

    public CollectionInjector(CollectionFactory collectionFactory, InjectableSelectionStrategy strategy,
	    GenericCollectionTypeResolver genericCollectionTypeResolver) {
	this.collectionFactory = collectionFactory;
	this.strategy = strategy;
	this.genericCollectionTypeResolver = genericCollectionTypeResolver;
    }

    /**
     * Injects the {@link InjectionDetails#getInjectees() injectees} into the {@link InjectionDetails#getInjectables()
     * injectables}.
     * 
     * @param injectionDetails
     */
    public void inject(InjectionDetails injectionDetails) {
	for (Object injectee : injectionDetails.getInjectees()) {
	    inject(injectee, injectionDetails.getInjectables());
	}
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void inject(Object injectee, Set<Object> injectables) {
	Field[] fields = injectee.getClass().getDeclaredFields();
	for (Field field : fields) {
	    Type type = field.getGenericType();
	    if (type instanceof ParameterizedType) {
		ParameterizedType parameterizedType = (ParameterizedType) type;
		// should be safe, ParamerterizedType should only ever return a Class from this method
		Class rawType = (Class) parameterizedType.getRawType();
		if (Collection.class.isAssignableFrom(rawType)) {
		    Type collectionType = genericCollectionTypeResolver.getCollectionFieldType(field);
		    if (collectionType != null) {
			Set strategyInjectables = strategy.getInjectables(injectables, (Class) collectionType);
			if (!strategyInjectables.isEmpty()) {
			    Collection collection = collectionFactory.createCollection(rawType, strategyInjectables);
			    new FieldValueMutator(injectee, field).mutateTo(collection);
			}
		    }
		}
	    } else if (type instanceof Class) {
		Class clazz = (Class) type;
		if (clazz.isArray()) {
		    Set strategyInjectables = strategy.getInjectables(injectables, clazz.getComponentType());
		    if (!strategyInjectables.isEmpty()) {
			new FieldValueMutator(injectee, field).mutateTo(strategyInjectables.toArray());
		    }
		}
	    }
	}
    }
}
