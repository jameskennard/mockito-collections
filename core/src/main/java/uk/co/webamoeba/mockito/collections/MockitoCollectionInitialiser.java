package uk.co.webamoeba.mockito.collections;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.mockito.Mock;
import org.mockito.Mockito;

import uk.co.webamoeba.mockito.collections.annotation.InjectableCollectionOfMocks;
import uk.co.webamoeba.mockito.collections.exception.MockitoCollectionsException;
import uk.co.webamoeba.mockito.collections.util.AnnotatedFieldRetriever;
import uk.co.webamoeba.mockito.collections.util.FieldValueMutator;
import uk.co.webamoeba.mockito.collections.util.GenericCollectionTypeResolver;

/**
 * The {@link MockitoCollectionInitialiser} is responsible for handling the instantiation of {@link Collection
 * Collections} and Mockito {@link Mock}s within those {@link Collection Collections} on {@link Field Fields} annotated
 * with the {@link InjectableCollectionOfMocks} annotation.
 * 
 * @author James Kennard
 */
public class MockitoCollectionInitialiser {

    private AnnotatedFieldRetriever annotatedFieldRetriever;

    private GenericCollectionTypeResolver genericCollectionTypeResolver;

    private CollectionFactory collectionFactory;

    public MockitoCollectionInitialiser(AnnotatedFieldRetriever annotatedFieldRetriever,
	    GenericCollectionTypeResolver genericCollectionTypeResolver, CollectionFactory collectionFactory) {
	this.annotatedFieldRetriever = annotatedFieldRetriever;
	this.genericCollectionTypeResolver = genericCollectionTypeResolver;
	this.collectionFactory = collectionFactory;
    }

    /**
     * Initialises an {@link Object}, generally anticipated to be a test class, with {@link Collection Collections} for
     * {@link Field Fields} annotated with the {@link InjectableCollectionOfMocks} annotation.
     * 
     * @param object
     */
    public void initialise(Object object) {
	Set<Field> fields = annotatedFieldRetriever.getAnnotatedFields(object.getClass(),
		InjectableCollectionOfMocks.class);
	// TODO tidy this up, looks like a nasty big block of code...
	for (Field field : fields) {
	    Type type = field.getGenericType();
	    ParameterizedType parameterizedType = (ParameterizedType) type; // FIXME ensure is paramerterized type
	    // should be safe, ParamerterizedType should only ever return a Class from this method
	    Class rawType = (Class) parameterizedType.getRawType();
	    Type collectionType = genericCollectionTypeResolver.getCollectionFieldType(field); // FIXME null check
	    InjectableCollectionOfMocks annotation = field.getAnnotation(InjectableCollectionOfMocks.class);
	    assert annotation != null : "Field is missing InjectableCollectionOfMocks annotation, unexpected field retrieved from annotatedFieldRetriever";
	    int numberOfMocks = annotation.numberOfMocks();
	    if (numberOfMocks < 0) {
		throw new MockitoCollectionsException(
			"Unexpected numberOfMocks, the minimum number of mocks you can specify using the "
				+ InjectableCollectionOfMocks.class + " is zero.");
	    }
	    Collection<?> mocks = createMocks((Class) collectionType, numberOfMocks); // TODO is the cast
										      // to Class okay?
	    Collection collection = collectionFactory.createCollection(rawType, mocks);
	    new FieldValueMutator(object, field).mutateTo(collection);
	}
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private Collection<?> createMocks(Class collectionType, int numberOfMocks) {
	Set mocks = new HashSet();
	for (int i = 0; i < numberOfMocks; i++) {
	    Object mock = Mockito.mock(collectionType);
	    mocks.add(mock);
	}
	return mocks;
    }
}
