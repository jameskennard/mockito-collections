package uk.co.webamoeba.mockito.collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

/**
 * @author James Kennard
 */
public class DefaultInjectableSelectionStrategyTest {

    private DefaultInjectableSelectionStrategy strategy = new DefaultInjectableSelectionStrategy();

    @Test
    public void shouldGetInjectables() {
	// Given
	Object injectable1 = mock(InputStream.class);
	Object injectable2 = mock(OutputStream.class);
	Object injectable3 = mock(FileInputStream.class);
	Set<Object> injectables = new HashSet<Object>(Arrays.asList(injectable1, injectable2, injectable3));

	// When
	Set<InputStream> actualInjectables = strategy.getInjectables(injectables, InputStream.class);

	// Then
	assertEquals(2, actualInjectables.size());
	assertTrue(actualInjectables.contains(injectable1));
	assertTrue(actualInjectables.contains(injectable3));
    }

}
