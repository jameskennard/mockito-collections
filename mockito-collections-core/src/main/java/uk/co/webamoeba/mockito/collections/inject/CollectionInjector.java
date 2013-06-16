package uk.co.webamoeba.mockito.collections.inject;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Set;

import uk.co.webamoeba.mockito.collections.util.FieldValueMutator;
import uk.co.webamoeba.mockito.collections.util.GenericCollectionTypeResolver;
import uk.co.webamoeba.mockito.collections.util.OrderedSet;

/**
 * The {@link CollectionInjector} is responsible for performing the injection of {@link Collection Collections}. The
 * collaborators are set on instantiation so as to allow different behaviour as required.
 * 
 * @author James Kennard
 */
public class CollectionInjector {

	private CollectionFactory collectionFactory;

	private MockSelectionStrategy strategy;

	private GenericCollectionTypeResolver genericCollectionTypeResolver;

	public CollectionInjector(CollectionFactory collectionFactory, MockSelectionStrategy strategy,
			GenericCollectionTypeResolver genericCollectionTypeResolver) {
		this.collectionFactory = collectionFactory;
		this.strategy = strategy;
		this.genericCollectionTypeResolver = genericCollectionTypeResolver;
	}

	/**
	 * Injects {@link Collection Collections} into the {@link InjectionDetails#getInjectCollections()}.
	 * 
	 * @param injectionDetails
	 */
	public void inject(InjectionDetails injectionDetails) {
		for (Object injectCollections : injectionDetails.getInjectCollections()) {
			inject(injectCollections, injectionDetails.getMocks(), injectionDetails.getInjectableCollectionSet(),
					injectCollections.getClass());
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void inject(Object injectCollections, OrderedSet<Object> mocks,
			CollectionOfMocksFieldSet collectionOfMocksFieldSet, Class<? extends Object> fieldClazz) {
		Field[] fields = fieldClazz.getDeclaredFields();
		for (Field field : fields) {
			Type type = field.getGenericType();
			if (type instanceof ParameterizedType) {
				ParameterizedType parameterizedType = (ParameterizedType) type;
				// should be safe, ParamerterizedType should only ever return a Class from this method
				Class rawType = (Class) parameterizedType.getRawType();
				if (Collection.class.isAssignableFrom(rawType)) {
					Type collectionType = genericCollectionTypeResolver.getCollectionFieldType(field);
					if (collectionType != null) {
						Collection collection = getCollection(mocks, collectionOfMocksFieldSet, rawType, collectionType);
						if (collection != null) {
							new FieldValueMutator(injectCollections, field).mutateTo(collection);
						}
					}
				}
			} else if (type instanceof Class) {
				Class clazz = (Class) type;
				if (clazz.isArray()) {
					Set strategyInjectables = strategy.selectMocks(mocks, clazz.getComponentType());
					if (!strategyInjectables.isEmpty()) {
						new FieldValueMutator(injectCollections, field).mutateTo(strategyInjectables.toArray());
					}
				}
			}
		}
		Class<?> superclass = fieldClazz.getSuperclass();
		if (superclass != Object.class && superclass != null) {
			inject(injectCollections, mocks, collectionOfMocksFieldSet, superclass);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Collection getCollection(OrderedSet<Object> mocks, CollectionOfMocksFieldSet collectionOfMocksFieldSet,
			Class rawType, Type collectionType) {
		Collection collection = null;
		CollectionOfMocksField collectionOfMocksField = strategy.getCollectionOfMocksField(collectionOfMocksFieldSet,
				rawType, (Class) collectionType);
		final OrderedSet strategyInjectables;
		if (collectionOfMocksField != null) {
			strategyInjectables = new OrderedSet(collectionOfMocksField.getValue());
		} else {
			strategyInjectables = strategy.selectMocks(mocks, (Class) collectionType);
		}
		if (!strategyInjectables.isEmpty()) {
			collection = collectionFactory.createCollection(rawType, strategyInjectables);
		}
		return collection;
	}
}
