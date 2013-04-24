package uk.co.webamoeba.mockito.collections.util;

/*
 * Copyright 2002-2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.Collection;
import java.util.Map;

/**
 * Helper class for determining element types of {@link Collection} and {@link Map} {@link Field}s.
 * <p>
 * Ported from Spring Core.
 * https://github.com/SpringSource/spring-framework/blob/master/spring-core/src/main/java/org/springframework
 * /core/GenericCollectionTypeResolver.java
 * <p>
 * Includes modifications, mainly with the intent to reduce the capabilities of the class. We want to deal exclusively
 * with {@link Collection} {@link Field}s, and we want to get away from the static nature of the original class.
 * 
 * @author Juergen Hoeller
 * @author James Kennard
 */
public class GenericCollectionTypeResolver {

	@SuppressWarnings("rawtypes")
	private static final Class<Collection> COLLECTION_CLASS = Collection.class;

	/**
	 * Determine the generic element type of the given {@link Collection} {@link Field}.
	 * 
	 * @param collectionField
	 *            the {@link Collection} {@link Field} to introspect
	 * @return the generic type, or <code>null</code> if none
	 */
	public Class<?> getCollectionFieldType(Field collectionField) {
		return extractType(collectionField.getGenericType(), COLLECTION_CLASS);
	}

	/**
	 * Extract the generic type from the given Type object.
	 * 
	 * @param type
	 *            the Type to check
	 * @param source
	 *            the source {@link Collection}/{@link Map} Class that we check
	 * @return the generic type as Class, or <code>null</code> if none
	 */
	@SuppressWarnings("rawtypes")
	private Class<?> extractType(Type type, Class<?> source) {
		Type resolvedType = type;
		if (resolvedType instanceof ParameterizedType) {
			return extractTypeFromParameterizedType((ParameterizedType) resolvedType, source);
		} else if (resolvedType instanceof Class) {
			return extractTypeFromClass((Class) resolvedType, source);
		} else {
			return null;
		}
	}

	/**
	 * Extract the generic type from the given {@link ParameterizedType} object.
	 * 
	 * @param ptype
	 *            the {@link ParameterizedType} to check
	 * @param source
	 *            the expected raw source type (can be <code>null</code>)
	 * @return the generic type as {@link Class}, or <code>null</code> if none
	 */
	@SuppressWarnings("rawtypes")
	private Class<?> extractTypeFromParameterizedType(ParameterizedType ptype, Class<?> source) {
		Class rawType = (Class) ptype.getRawType();
		Type[] paramTypes = ptype.getActualTypeArguments();
		Class fromSuperclassOrInterface = extractTypeFromClass(rawType, source);
		if (fromSuperclassOrInterface != null) {
			return fromSuperclassOrInterface;
		}
		Type paramType = paramTypes[0];
		if (paramType instanceof WildcardType) {
			WildcardType wildcardType = (WildcardType) paramType;
			Type[] upperBounds = wildcardType.getUpperBounds();
			if (upperBounds != null && upperBounds.length > 0 && !Object.class.equals(upperBounds[0])) {
				paramType = upperBounds[0];
			} else {
				Type[] lowerBounds = wildcardType.getLowerBounds();
				if (lowerBounds != null && lowerBounds.length > 0 && !Object.class.equals(lowerBounds[0])) {
					paramType = lowerBounds[0];
				}
			}
		}
		if (paramType instanceof GenericArrayType) {
			// A generic array type... Let's turn it into a straight array type if possible.
			Type compType = ((GenericArrayType) paramType).getGenericComponentType();
			if (compType instanceof Class) {
				return Array.newInstance((Class) compType, 0).getClass();
			}
		} else if (paramType instanceof Class) {
			// We finally got a straight Class...
			return (Class) paramType;
		}
		return null;
	}

	/**
	 * Extract the generic type from the given {@link Class} object.
	 * 
	 * @param clazz
	 *            the {@link Class} to check
	 * @param source
	 *            the expected raw source type (can be <code>null</code>)
	 * @param typeIndex
	 *            the index of the actual type argument
	 * @param nestingLevel
	 *            the nesting level of the target type
	 * @param currentLevel
	 *            the current nested level
	 * @return the generic type as {@link Class}, or <code>null</code> if none
	 */
	@SuppressWarnings("rawtypes")
	private Class<?> extractTypeFromClass(Class<?> clazz, Class<?> source) {
		if (clazz.getName().startsWith("java.util.")) {
			return null;
		}
		Type[] ifcs = clazz.getGenericInterfaces();
		if (ifcs != null) {
			for (Type ifc : ifcs) {
				Type rawType = ifc;
				if (ifc instanceof ParameterizedType) {
					rawType = ((ParameterizedType) ifc).getRawType();
				}
				if (rawType instanceof Class && isIntrospectionCandidate((Class) rawType)) {
					return extractType(ifc, source);
				}
			}
		}
		return null;
	}

	/**
	 * Determine whether the given class is a potential candidate that defines generic collection or map types.
	 * 
	 * @param clazz
	 *            the class to check
	 * @return whether the given class is assignable to Collection or Map
	 */
	@SuppressWarnings("rawtypes")
	private boolean isIntrospectionCandidate(Class clazz) {
		return (COLLECTION_CLASS.isAssignableFrom(clazz) || Map.class.isAssignableFrom(clazz));
	}

}