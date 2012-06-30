import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class InjectionDetailsTest {

	@Test
	public void shouldGetInjectees() {
		// Given
		Set<Object> injectees = mock(Set.class);
		Set<Object> injectables = mock(Set.class);
		InjectionDetails details = new InjectionDetails(injectees, injectables);

		// When
		Set<Object> actualInjectee = details.getInjectees();

		// Then
		assertSame(injectees, actualInjectee);
	}

	@Test
	public void shouldGetInjectables() {
		// Given
		Set<Object> injectees = mock(Set.class);
		Set<Object> injectables = new HashSet<Object>();
		InjectionDetails details = new InjectionDetails(injectees, injectables);

		// When
		Set<Object> actualInjectables = details.getInjectables();

		// Then
		assertSame(injectables, actualInjectables);
	}

	@Test
	public void shouldGetInjectablesGivenNullInConstruction() {
		// Given
		Set<Object> injectees = mock(Set.class);
		Set<Object> injectables = null;
		InjectionDetails details = new InjectionDetails(injectees, injectables);

		// When
		Set<Object> actualInjectables = details.getInjectables();

		// Then
		assertTrue(actualInjectables.isEmpty());
	}

	@Test(expected=IllegalArgumentException.class)
	public void shouldFailToConstructGivenNullInjectees() {
		// Given
		Set<Object> injectees = null;
		Set<Object> injectables = mock(Set.class);

		// When
		new InjectionDetails(injectees, injectables);

		// Then
		// Exception thrown
	}
}
