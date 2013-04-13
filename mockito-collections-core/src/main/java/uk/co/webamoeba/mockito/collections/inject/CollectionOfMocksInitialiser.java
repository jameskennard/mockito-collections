package uk.co.webamoeba.mockito.collections.inject;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Set;

import org.mockito.Mock;

import uk.co.webamoeba.mockito.collections.annotation.CollectionOfMocks;
import uk.co.webamoeba.mockito.collections.exception.MockitoCollectionsException;
import uk.co.webamoeba.mockito.collections.util.AnnotatedFieldRetriever;
import uk.co.webamoeba.mockito.collections.util.FieldValueMutator;
import uk.co.webamoeba.mockito.collections.util.GenericCollectionTypeResolver;
import uk.co.webamoeba.mockito.collections.util.HashOrderedSet;
import uk.co.webamoeba.mockito.collections.util.OrderedSet;

/**
 * The {@link CollectionOfMocksInitialiser} is responsible for handling the instantiation of {@link Collection
 * Collections} and Mockito {@link Mock}s within those {@link Collection Collections} on {@link Field Fields} annotated
 * with the {@link CollectionOfMocks} annotation.
 * 
 * @author James Kennard
 */
public class CollectionOfMocksInitialiser {

	private AnnotatedFieldRetriever annotatedFieldRetriever;

	private GenericCollectionTypeResolver genericCollectionTypeResolver;

	private CollectionFactory collectionFactory;

	private MockStrategy mockStrategy;

	public CollectionOfMocksInitialiser(AnnotatedFieldRetriever annotatedFieldRetriever,
			GenericCollectionTypeResolver genericCollectionTypeResolver, CollectionFactory collectionFactory,
			MockStrategy mockStrategy) {
		this.annotatedFieldRetriever = annotatedFieldRetriever;
		this.genericCollectionTypeResolver = genericCollectionTypeResolver;
		this.collectionFactory = collectionFactory;
		this.mockStrategy = mockStrategy;
	}

	/**
	 * Initialises an {@link Object}, generally anticipated to be a test class, with {@link Collection Collections} for
	 * {@link Field Fields} annotated with the {@link CollectionOfMocks} annotation.
	 * 
	 * @param object
	 */
	@SuppressWarnings("rawtypes")
	public void initialise(Object object) {
		Set<Field> fields = annotatedFieldRetriever.getAnnotatedFields(object.getClass(), CollectionOfMocks.class);
		for (Field field : fields) {
			Collection collection = createCollectionForField(field);
			new FieldValueMutator(object, field).mutateTo(collection);
		}
	}

	private Collection createCollectionForField(Field field) {
		OrderedSet<?> mocks = createMocks(getMockClass(field), getNumberOfMocks(field));
		Class collectionClass = getCollectionClass(field.getGenericType());
		return collectionFactory.createCollection(collectionClass, mocks);
	}

	private Class getMockClass(Field field) {
		Class mockClass = genericCollectionTypeResolver.getCollectionFieldType(field); // FIXME null check
		return mockClass;
	}

	private Class getCollectionClass(Type type) {
		ParameterizedType parameterizedType = (ParameterizedType) type;
		// FIXME ensure is paramerterized type
		// should be safe, ParamerterizedType should only ever return a Class from this method
		// FIXME ensure is Collection class
		return (Class) parameterizedType.getRawType();
	}

	/**
	 * @param field
	 * @return The {@link CollectionOfMocks#numberOfMocks() number of mocks} declared on the annotation.
	 */
	private int getNumberOfMocks(Field field) {
		CollectionOfMocks annotation = field.getAnnotation(CollectionOfMocks.class);
		int numberOfMocks = annotation.numberOfMocks();
		if (numberOfMocks < 0) {
			throw new MockitoCollectionsException(
					"Unexpected numberOfMocks, the minimum number of mocks you can specify using "
							+ CollectionOfMocks.class + " is zero.");
		}
		return numberOfMocks;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private OrderedSet<?> createMocks(Class collectionType, int numberOfMocks) {
		OrderedSet mocks = new HashOrderedSet();
		for (int i = 0; i < numberOfMocks; i++) {
			Object mock = mockStrategy.createMock(collectionType);
			mocks.add(mock);
		}
		return mocks;
	}
}
