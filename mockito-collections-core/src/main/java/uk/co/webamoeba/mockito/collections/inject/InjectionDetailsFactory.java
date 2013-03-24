package uk.co.webamoeba.mockito.collections.inject;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.util.reflection.FieldReader;

import uk.co.webamoeba.mockito.collections.annotation.IgnoreInjectable;
import uk.co.webamoeba.mockito.collections.annotation.InjectCollections;
import uk.co.webamoeba.mockito.collections.annotation.Injectable;
import uk.co.webamoeba.mockito.collections.exception.MockitoCollectionsException;
import uk.co.webamoeba.mockito.collections.util.AnnotatedFieldRetriever;
import uk.co.webamoeba.mockito.collections.util.GenericCollectionTypeResolver;
import uk.co.webamoeba.mockito.collections.util.HashOrderedSet;
import uk.co.webamoeba.mockito.collections.util.OrderedSet;

/**
 * Factory that creates {@link InjectionDetails} from an {@link Object} based on {@link ElementType#FIELD field}
 * annotations. {@link Field Fields} with the {@link InjectMocks} or {@link InjectCollections} annotation are considered
 * for injection of {@link Collection Collections}. {@link Field Fields} with the {@link Mock} or {@link Injectable}
 * annotation are considered injectables. It is also possible to ignore fields that would other wise be considered
 * injectables or for injecting {@link Collection Collections} using the {@link IgnoreInjectable} annotation.
 * 
 * @see Mock
 * @see InjectMocks
 * @see Injectable
 * @see InjectCollections
 * @see IgnoreInjectable
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
		OrderedSet<Object> injectables = getInjectables(object);
		InjectableCollectionSet injectableCollectionSet = getInjectableCollectionSet(object);
		return new InjectionDetails(injectCollections, injectables, injectableCollectionSet);
	}

	private Set<Object> getInjectCollections(Object object) {
		Set<Object> injectCollections = getFieldValues(object, InjectMocks.class);
		injectCollections.addAll(getFieldValues(object, InjectCollections.class));
		return injectCollections;
	}

	private OrderedSet<Object> getInjectables(Object object) {
		Set<Field> fields = annotatedFieldRetriever.getAnnotatedFields(object.getClass(), Mock.class);
		fields.addAll(annotatedFieldRetriever.getAnnotatedFields(object.getClass(), Injectable.class));
		fields.removeAll(annotatedFieldRetriever.getAnnotatedFields(object.getClass(), IgnoreInjectable.class));
		return getFieldValues(object, fields);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private InjectableCollectionSet getInjectableCollectionSet(Object object) {
		InjectableCollectionSet injectableCollectionSet = new InjectableCollectionSet();
		Set<Field> fields = annotatedFieldRetriever.getAnnotatedFields(object.getClass(),
				uk.co.webamoeba.mockito.collections.annotation.CollectionOfMocks.class);
		for (Field field : fields) {
			Object fieldValue = new FieldReader(object, field).read();
			if (!(fieldValue instanceof Collection)) {
				throw new MockitoCollectionsException(
						"Found field with InjectableCollection annotation, but the field is not a Collection, field is '"
								+ field.getName() + "'");
			}
			Collection<?> value = (Collection<?>) fieldValue;
			Class<Collection<?>> typeOfCollection = (Class<Collection<?>>) ((ParameterizedType) field.getGenericType())
					.getRawType();
			Class typeOfElements = genericCollectionTypeResolver.getCollectionFieldType(field);
			InjectableCollection injectableCollection = new InjectableCollection(value, typeOfCollection,
					typeOfElements);
			injectableCollectionSet.add(injectableCollection);
		}
		return injectableCollectionSet;
	}

	private OrderedSet<Object> getFieldValues(Object object, Class<? extends Annotation> annotationClass) {
		Set<Field> fields = annotatedFieldRetriever.getAnnotatedFields(object.getClass(), annotationClass);
		return getFieldValues(object, fields);
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
		OrderedSet<Object> values = new HashOrderedSet<Object>();
		for (Field field : sortedFields) {
			values.add(new FieldReader(object, field).read());
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
