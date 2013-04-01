package uk.co.webamoeba.mockito.collections.internal;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import uk.co.webamoeba.mockito.collections.inject.CollectionInjector;
import uk.co.webamoeba.mockito.collections.inject.CollectionOfMocksInitialiser;
import uk.co.webamoeba.mockito.collections.inject.InjectionDetails;
import uk.co.webamoeba.mockito.collections.inject.InjectionDetailsFactory;

@RunWith(MockitoJUnitRunner.class)
public class InitialiserTest {

	@InjectMocks
	private Initialiser initialiser;

	@Mock
	private CollectionInjector injector;

	@Mock
	private InjectionDetailsFactory factory;

	@Mock
	private CollectionOfMocksInitialiser collectionOfMocksInitialiser;

	@Test
	public void shouldInitialise() {
		// Given
		Object object = mock(Object.class);
		InjectionDetails injectionDetails = mock(InjectionDetails.class);
		given(factory.createInjectionDetails(object)).willReturn(injectionDetails);

		// When
		initialiser.initialise(object);

		// Then
		verify(collectionOfMocksInitialiser).initialise(object);
		verify(injector).inject(injectionDetails);
	}

}
