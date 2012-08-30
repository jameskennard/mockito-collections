package uk.co.webamoeba.mockito.collections;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.util.reflection.FieldReader;

import uk.co.webamoeba.mockito.collections.annotation.IgnoreInjectable;
import uk.co.webamoeba.mockito.collections.annotation.IgnoreInjectee;
import uk.co.webamoeba.mockito.collections.annotation.Injectable;
import uk.co.webamoeba.mockito.collections.annotation.Injectee;
import uk.co.webamoeba.mockito.collections.exception.MockitoCollectionsException;
import uk.co.webamoeba.mockito.collections.inject.InjectableCollection;
import uk.co.webamoeba.mockito.collections.inject.InjectableCollectionSet;
import uk.co.webamoeba.mockito.collections.inject.InjectionDetails;
import uk.co.webamoeba.mockito.collections.util.AnnotatedFieldRetriever;
import uk.co.webamoeba.mockito.collections.util.GenericCollectionTypeResolver;

/**
 * Factory that creates {@link InjectionDetails} from an {@link Object} based on {@link ElementType#FIELD field}
 * annotations. {@link Field Fields} with the {@link InjectMocks} or {@link Injectee} annotation are considered
 * injectees. {@link Field Fields} with the {@link Mock} or {@link Injectable} annotation are considered injectables. It
 * is also possible to ignore fields that would other wise be considered injectables or injectees using the
 * {@link IgnoreInjectable} and {@link IgnoreInjectee} annotations respectively.
 * 
 * @see Mock
 * @see InjectMocks
 * @see Injectable
 * @see Injectee
 * @see IgnoreInjectable
 * @see IgnoreInjectee
 * @author James Kennard
 */
public class MockitoInjectionDetailsFactory {

	private AnnotatedFieldRetriever annotatedFieldRetriever;

	private GenericCollectionTypeResolver genericCollectionTypeResolver;

	public MockitoInjectionDetailsFactory(AnnotatedFieldRetriever annotatedFieldRetriever,
			GenericCollectionTypeResolver genericCollectionTypeResolver) {
		this.annotatedFieldRetriever = annotatedFieldRetriever;
		this.genericCollectionTypeResolver = genericCollectionTypeResolver;
	}

	/**
	 * @param object
	 * @return {@link InjectionDetails} created based on the Mockito annotations {@link Mock} and {@link InjectMocks}.
	 */
	public InjectionDetails createInjectionDetails(Object object) {
		Set<Object> injectees = getInjectees(object);
		Set<Object> injectables = getInjectables(object);
		InjectableCollectionSet injectableCollectionSet = getInjectableCollectionSet(object);
		return new InjectionDetails(injectees, injectables, injectableCollectionSet);
	}

	private Set<Object> getInjectees(Object object) {
		Set<Object> injectees = getFieldValues(object, InjectMocks.class);
		injectees.addAll(getFieldValues(object, Injectee.class));
		injectees.removeAll(getFieldValues(object, IgnoreInjectee.class));
		return injectees;
	}

	private Set<Object> getInjectables(Object object) {
		Set<Object> injectables = getFieldValues(object, Mock.class);
		injectables.addAll(getFieldValues(object, Injectable.class));
		injectables.removeAll(getFieldValues(object, IgnoreInjectable.class));
		return injectables;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private InjectableCollectionSet getInjectableCollectionSet(Object object) {
		InjectableCollectionSet injectableCollectionSet = new InjectableCollectionSet();
		Set<Field> fields = annotatedFieldRetriever.getAnnotatedFields(object.getClass(),
				uk.co.webamoeba.mockito.collections.annotation.InjectableCollection.class);
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

	private Set<Object> getFieldValues(Object object, Class<? extends Annotation> annotationClass) {
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
	private Set<Object> getFieldValues(Object object, Set<Field> fields) {
		Set<Object> values = new HashSet<Object>();
		for (Field field : fields) {
			values.add(new FieldReader(object, field).read());
		}
		return values;
	}

}
