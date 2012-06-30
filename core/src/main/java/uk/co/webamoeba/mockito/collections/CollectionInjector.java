package uk.co.webamoeba.mockito.collections;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Set;

import org.mockito.internal.util.reflection.FieldSetter;

public class CollectionInjector {

    private CollectionFactory collectionFactory;

    private InjectableSelectionStrategy strategy;

    public CollectionInjector(CollectionFactory collectionFactory, InjectableSelectionStrategy strategy) {
	this.collectionFactory = collectionFactory;
	this.strategy = strategy;
    }

    public void inject(InjectionDetails injectionDetails) {
	for (Object injectee : injectionDetails.getInjectees()) {
	    inject(injectee, injectionDetails);
	}
    }

    private void inject(Object injectee, InjectionDetails injectionDetails) {
	Field[] fields = injectee.getClass().getDeclaredFields();
	for (Field field : fields) {
	    Type genericType = field.getGenericType();
	    if (genericType instanceof ParameterizedType) {
		ParameterizedType type = (ParameterizedType) genericType;
		Class rawType = (Class) type.getRawType();

		if (Collection.class.isAssignableFrom(rawType)) {

		    getCollectionType(rawType);
		    Collection collection = collectionFactory.createCollection(rawType);
		    Type[] typeArguments = type.getActualTypeArguments();
		    Set injectables = strategy.getInjectables(injectionDetails.getInjectables(),
			    (Class) typeArguments[0]);
		    collection.addAll(injectables);
		    new FieldSetter(injectee, field).set(collection);
		}
	    }
	}
    }

    private Type getCollectionType(Class rawType) {
	for (Type genericInterface : rawType.getGenericInterfaces()) {
	    if (((ParameterizedType) genericInterface).getRawType().equals(Collection.class)) {
		return genericInterface;
	    }
	}
	// FIXME
	throw new RuntimeException("Should not have happened...");
    }
}
