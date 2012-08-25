package uk.co.webamoeba.mockito.collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;

import java.util.Collections;
import java.util.Set;

import org.junit.Test;

/**
 * @author James Kennard
 */
@SuppressWarnings("unchecked")
public class InjectionDetailsTest {

    @Test
    public void shouldGetInjectees() {
	// Given
	Object injectee = "Some Injectee";
	Set<Object> injectees = Collections.singleton(injectee);
	Set<Object> injectables = mock(Set.class);
	InjectableCollectionSet injectableCollectionSet = mock(InjectableCollectionSet.class);
	InjectionDetails details = new InjectionDetails(injectees, injectables, injectableCollectionSet);

	// When
	Set<Object> actualInjectees = details.getInjectees();

	// Then
	assertEquals(injectees, actualInjectees);
    }

    @Test
    public void shouldGetInjectables() {
	// Given
	Set<Object> injectees = mock(Set.class);
	Object injectable = "Some Injectable";
	Set<Object> injectables = Collections.singleton(injectable);
	InjectableCollectionSet injectableCollectionSet = mock(InjectableCollectionSet.class);
	InjectionDetails details = new InjectionDetails(injectees, injectables, injectableCollectionSet);

	// When
	Set<Object> actualInjectables = details.getInjectables();

	// Then
	assertEquals(injectables, actualInjectables);
    }

    @Test
    public void shouldGetInjectableCollectionSet() {
	// Given
	Set<Object> injectees = mock(Set.class);
	Set<Object> injectables = Collections.emptySet();
	InjectableCollectionSet injectableCollectionSet = mock(InjectableCollectionSet.class);
	InjectionDetails details = new InjectionDetails(injectees, injectables, injectableCollectionSet);

	// When
	InjectableCollectionSet actualInjectableCollectionSet = details.getInjectableCollectionSet();

	// Then
	assertSame(injectableCollectionSet, actualInjectableCollectionSet);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToConstructGivenNullInjectees() {
	// Given
	Set<Object> injectees = null;
	Set<Object> injectables = Collections.emptySet();
	InjectableCollectionSet injectableCollectionSet = mock(InjectableCollectionSet.class);

	// When
	new InjectionDetails(injectees, injectables, injectableCollectionSet);

	// Then
	// Exception thrown
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToConstructGivenNullInjectables() {
	// Given
	Set<Object> injectees = Collections.emptySet();
	Set<Object> injectables = null;
	InjectableCollectionSet injectableCollectionSet = mock(InjectableCollectionSet.class);

	// When
	new InjectionDetails(injectees, injectables, injectableCollectionSet);

	// Then
	// Exception thrown
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToConstructGivenNullInjectableCollections() {
	// Given
	Set<Object> injectees = Collections.emptySet();
	Set<Object> injectables = Collections.emptySet();
	InjectableCollectionSet injectableCollectionSet = null;

	// When
	new InjectionDetails(injectees, injectables, injectableCollectionSet);

	// Then
	// Exception thrown
    }
}
