package uk.co.webamoeba.mockito.collections.inject;

import java.lang.annotation.ElementType;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.internal.util.reflection.FieldReader;

import uk.co.webamoeba.mockito.collections.annotation.IgnoreForCollections;
import uk.co.webamoeba.mockito.collections.exception.MockitoCollectionsException;
import uk.co.webamoeba.mockito.collections.util.AnnotatedFieldRetriever;
import uk.co.webamoeba.mockito.collections.util.GenericCollectionTypeResolver;
import uk.co.webamoeba.mockito.collections.util.OrderedSet;

/**
 * Factory that creates {@link InjectionDetails} from an {@link Object} based on {@link ElementType#FIELD field}
 * annotations. {@link Field Fields} with the {@link InjectMocks} annotation are considered for injection of
 * {@link Collection Collections}. {@link Field Fields} with the {@link Mock} annotation are considered {@link Mock
 * Mocks}. It is also possible to ignore fields that would otherwise be considered {@link Mock Mocks} using the
 * {@link IgnoreMockForCollections} annotation.
 * 
 * @see Mock
 * @see InjectMocks
 * @see IgnoreMockForCollections
 * @author James Kennard
 */
public class InjectionDetailsFactory {

	private AnnotatedFieldRetriever annotatedFieldRetriever;

	private GenericCollectionTypeResolver genericCollectionTypeResolver;

	public InjectionDetailsFactory(AnnotatedFieldRetriever annotatedFieldRetriever,
			GenericCollectionTypeResolver genericCollectionTypeResolver) {
		this.annotatedFieldRetriever = annotatedFieldRetriever;
		this.genericCollectionTypeResolver = genericCollectionTypeResolver;
	}

	/**
	 * @param object
	 * @return {@link InjectionDetails} created based on the Mockito annotations {@link Mock} and {@link InjectMocks}.
	 */
	public InjectionDetails createInjectionDetails(Object object) {
		Set<Object> injectCollections = getInjectCollections(object);
		OrderedSet<Object> mocks = getMocks(object);
		OrderedSet<Object> spies = getSpies(object);
		CollectionOfMocksFieldSet collectionOfMocksFieldSet = getInjectableCollectionSet(object);
		return new InjectionDetails(injectCollections, mocks, spies, collectionOfMocksFieldSet);
	}

	private Set<Object> getInjectCollections(Object object) {
		Set<Field> fields = annotatedFieldRetriever.getAnnotatedFields(object.getClass(), InjectMocks.class);
		return getFieldValues(object, fields);
	}

	private OrderedSet<Object> getMocks(Object object) {
		Set<Field> fields = annotatedFieldRetriever.getAnnotatedFields(object.getClass(), Mock.class);
		fields.removeAll(annotatedFieldRetriever.getAnnotatedFields(object.getClass(), IgnoreForCollections.class));
		return getFieldValues(object, fields);
	}

	private OrderedSet<Object> getSpies(Object object) {
		Set<Field> fields = annotatedFieldRetriever.getAnnotatedFields(object.getClass(), Spy.class);
		fields.removeAll(annotatedFieldRetriever.getAnnotatedFields(object.getClass(), IgnoreForCollections.class));
		return getFieldValues(object, fields);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private CollectionOfMocksFieldSet getInjectableCollectionSet(Object object) {
		CollectionOfMocksFieldSet collectionOfMocksFieldSet = new CollectionOfMocksFieldSet();
		Set<Field> fields = annotatedFieldRetriever.getAnnotatedFields(object.getClass(),
				uk.co.webamoeba.mockito.collections.annotation.CollectionOfMocks.class);
		for (Field field : fields) {
			Object fieldValue = new FieldReader(object, field).read();
			if (!(fieldValue instanceof Collection)) {
				throw new MockitoCollectionsException(
						"Found field with CollectionOfMocksField annotation, but the field is not a Collection, field is '"
								+ field.getName() + "'");
			}
			Collection<?> value = (Collection<?>) fieldValue;
			Class<Collection<?>> typeOfCollection = (Class<Collection<?>>) ((ParameterizedType) field.getGenericType())
					.getRawType();
			Class typeOfElements = genericCollectionTypeResolver.getCollectionFieldType(field);
			CollectionOfMocksField collectionOfMocksField = new CollectionOfMocksField(value, typeOfCollection,
					typeOfElements);
			collectionOfMocksFieldSet.add(collectionOfMocksField);
		}
		return collectionOfMocksFieldSet;
	}

	/**
	 * @param object
	 *            {@link Object} from which to retrieve values
	 * @param fields
	 *            The {@link Field Fields} from which to retrieve values
	 * @return Values of the {@link Field Fields} retrieved from the object
	 */
	private OrderedSet<Object> getFieldValues(Object object, Set<Field> fields) {
		TreeSet<Field> sortedFields = new TreeSet<Field>(new FieldComparator());
		sortedFields.addAll(fields);
		OrderedSet<Object> values = new OrderedSet<Object>();
		for (Field field : sortedFields) {
			Object fieldValue = new FieldReader(object, field).read();
			if (fieldValue == null) {
				throw new MockitoCollectionsException("The field " + field.getName()
						+ " is null, you must initialse the fields before using Mockito-Collections");
			}
			values.add(fieldValue);
		}
		return values;
	}

	/**
	 * A {@link Comparator} that compares {@link Field Fields} by their declaring class and then their declared name.
	 * 
	 * @author James Kennard
	 */
	private class FieldComparator implements Comparator<Field> {

		public int compare(Field o1, Field o2) {
			if (o1.getDeclaringClass() == o2.getDeclaringClass()) {
				return o1.getName().compareToIgnoreCase(o2.getName());
			} else if (o1.getDeclaringClass().isAssignableFrom(o2.getDeclaringClass())) {
				return -1;
			} else if (o2.getDeclaringClass().isAssignableFrom(o1.getDeclaringClass())) {
				return 1;
			}
			throw new IllegalArgumentException("The fields in the set are not from the same hierarchical tree");
		}
	}
}
