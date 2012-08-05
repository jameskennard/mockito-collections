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

    private GenericCollectionTypeResolver genericCollectionTypeResolver;

    public CollectionInjector(CollectionFactory collectionFactory, InjectableSelectionStrategy strategy,
	    GenericCollectionTypeResolver genericCollectionTypeResolver) {
	this.collectionFactory = collectionFactory;
	this.strategy = strategy;
	this.genericCollectionTypeResolver = genericCollectionTypeResolver;
    }

    public void inject(InjectionDetails injectionDetails) {
	for (Object injectee : injectionDetails.getInjectees()) {
	    inject(injectee, injectionDetails);
	}
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void inject(Object injectee, InjectionDetails injectionDetails) {
	Field[] fields = injectee.getClass().getDeclaredFields();
	for (Field field : fields) {
	    Type genericType = field.getGenericType();
	    if (genericType instanceof ParameterizedType) {
		ParameterizedType type = (ParameterizedType) genericType;
		Class rawType = (Class) type.getRawType();

		if (Collection.class.isAssignableFrom(rawType)) {
		    Collection collection = collectionFactory.createCollection(rawType);
		    Type collectionType = genericCollectionTypeResolver.getCollectionFieldType(field);

		    Set injectables = strategy
			    .getInjectables(injectionDetails.getInjectables(), (Class) collectionType);
		    collection.addAll(injectables);
		    new FieldSetter(injectee, field).set(collection);
		}
	    }
	}
    }
}
