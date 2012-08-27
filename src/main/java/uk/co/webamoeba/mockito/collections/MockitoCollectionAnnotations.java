package uk.co.webamoeba.mockito.collections;

import java.lang.reflect.Field;
import java.util.Collection;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.co.webamoeba.mockito.collections.annotation.Injectable;
import uk.co.webamoeba.mockito.collections.annotation.Injectee;
import uk.co.webamoeba.mockito.collections.util.AnnotatedFieldRetriever;
import uk.co.webamoeba.mockito.collections.util.GenericCollectionTypeResolver;

/**
 * Utility class, comparable to {@link MockitoAnnotations}, which orchestrate the injection of {@link Collection
 * Collections} of Mockito Mocks. This class will scan an {@link Object}, typically a Mockito style unit test, and
 * inject the values of {@link Field Fields} annotated with {@link Mock} or {@link Injectable} into new
 * {@link Collection Collections} on {@link Field Fields} within {@link Field Fields} annotated with {@link InjectMocks}
 * or {@link Injectee}.
 * 
 * <pre>
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * &#064;InjectMocks
 * private MyClassWithEventListeners objectUnderTest;
 * 
 * &#064;Mock
 * private EventListener eventListener;
 * 
 * &#064;Before
 * public void setup() {
 * 	MockitoCollectionAnnotations.inject(this);
 * 	assert objectUnderTest.getEventListeners().contains(eventListener);
 * }
 * </pre>
 * 
 * @author James Kennard
 */
public class MockitoCollectionAnnotations {

	private static CollectionInjector injector;

	private static MockitoInjectionDetailsFactory factory;

	private static CollectionInitialiser collectionInitialiser;

	{
		GenericCollectionTypeResolver genericCollectionTypeResolver = new GenericCollectionTypeResolver();
		DefaultInjectableSelectionStrategy injectableSelectionStrategy = new DefaultInjectableSelectionStrategy();
		CollectionFactory collectionFactory = new CollectionFactory();
		AnnotatedFieldRetriever annotatedFieldRetriever = new AnnotatedFieldRetriever();
		MockStrategy mockStrategy = new MockitoMockStrategy();

		injector = new CollectionInjector(collectionFactory, injectableSelectionStrategy, genericCollectionTypeResolver);
		factory = new MockitoInjectionDetailsFactory(annotatedFieldRetriever, genericCollectionTypeResolver);
		collectionInitialiser = new CollectionInitialiser(annotatedFieldRetriever, genericCollectionTypeResolver,
				collectionFactory, mockStrategy);
	}

	public static void inject(Object object) {
		if (collectionInitialiser == null) {
			// Force the execution of the static block.
			new MockitoCollectionAnnotations();
		}
		collectionInitialiser.initialise(object);
		injector.inject(factory.createInjectionDetails(object));
	}

}
