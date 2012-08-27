package uk.co.webamoeba.mockito.collections;

import static org.junit.Assert.assertTrue;

import java.util.Dictionary;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.internal.util.MockUtil;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author James Kennard
 */
@RunWith(MockitoJUnitRunner.class)
public class MockitoMockStrategyTest {

	@InjectMocks
	private MockitoMockStrategy strategy;

	private MockUtil mockUtil = new MockUtil();

	@Test
	@SuppressWarnings("rawtypes")
	public void shouldCreateMock() {
		// Given
		Class<Dictionary> clazz = Dictionary.class;

		// When
		Dictionary mock = strategy.createMock(clazz);

		// Then
		assertTrue(mockUtil.isMock(mock));
	}
}
