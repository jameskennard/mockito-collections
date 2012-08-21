package uk.co.webamoeba.mockito.collections;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.Collection;
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
	Set<InjectableCollection<Collection<Object>, Object>> injectableCollections = Collections.emptySet();
	InjectionDetails details = new InjectionDetails(injectees, injectables, injectableCollections);

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
	Set<InjectableCollection<Collection<Object>, Object>> injectableCollections = Collections.emptySet();
	InjectionDetails details = new InjectionDetails(injectees, injectables, injectableCollections);

	// When
	Set<Object> actualInjectables = details.getInjectables();

	// Then
	assertEquals(injectables, actualInjectables);
    }

    @Test
    public void shouldGetInjectableCollections() {
	// Given
	Set<Object> injectees = mock(Set.class);
	Set<Object> injectables = Collections.emptySet();
	InjectableCollection<Collection<Object>, Object> injectableCollection = mock(InjectableCollection.class);
	Set<InjectableCollection<Collection<Object>, Object>> injectableCollections = Collections
		.singleton(injectableCollection);
	InjectionDetails details = new InjectionDetails(injectees, injectables, injectableCollections);

	// When
	Set<InjectableCollection<Collection<Object>, Object>> actualInjectableCollections = details
		.getInjectableCollections();

	// Then
	assertEquals(injectableCollections, actualInjectableCollections);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToConstructGivenNullInjectees() {
	// Given
	Set<Object> injectees = null;
	Set<Object> injectables = Collections.emptySet();
	Set<InjectableCollection<Collection<Object>, Object>> injectableCollections = Collections.emptySet();

	// When
	new InjectionDetails(injectees, injectables, injectableCollections);

	// Then
	// Exception thrown
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToConstructGivenNullInjectables() {
	// Given
	Set<Object> injectees = Collections.emptySet();
	Set<Object> injectables = null;
	Set<InjectableCollection<Collection<Object>, Object>> injectableCollections = Collections.emptySet();

	// When
	new InjectionDetails(injectees, injectables, injectableCollections);

	// Then
	// Exception thrown
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToConstructGivenNullInjectableCollections() {
	// Given
	Set<Object> injectees = Collections.emptySet();
	Set<Object> injectables = Collections.emptySet();
	Set<InjectableCollection<Collection<Object>, Object>> injectableCollections = null;

	// When
	new InjectionDetails(injectees, injectables, injectableCollections);

	// Then
	// Exception thrown
    }
}
