package uk.co.webamoeba.mockito.collections;

import java.lang.reflect.Field;
import java.util.Collection;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.co.webamoeba.mockito.collections.annotation.InjectCollections;
import uk.co.webamoeba.mockito.collections.inject.CollectionFactory;
import uk.co.webamoeba.mockito.collections.inject.CollectionInitialiser;
import uk.co.webamoeba.mockito.collections.inject.CollectionInjector;
import uk.co.webamoeba.mockito.collections.inject.DefaultInjectableSelectionStrategy;
import uk.co.webamoeba.mockito.collections.inject.DefaultMockStrategy;
import uk.co.webamoeba.mockito.collections.inject.InjectionDetailsFactory;
import uk.co.webamoeba.mockito.collections.inject.MockStrategy;
import uk.co.webamoeba.mockito.collections.util.AnnotatedFieldRetriever;
import uk.co.webamoeba.mockito.collections.util.GenericCollectionTypeResolver;

/**
 * Utility class, comparable to {@link MockitoAnnotations}, which orchestrate the injection of {@link Collection
 * Collections} of Mockito Mocks. This class will scan an {@link Object}, typically a Mockito style unit test, and
 * inject the values of {@link Field Fields} annotated with {@link Mock} into new {@link Collection Collections} on
 * {@link Field Fields} within {@link Field Fields} annotated with {@link InjectMocks} or {@link InjectCollections}.
 * 
 * <pre>
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

	private static InjectionDetailsFactory factory;

	private static CollectionInitialiser collectionInitialiser;

	static {
		GenericCollectionTypeResolver genericCollectionTypeResolver = new GenericCollectionTypeResolver();
		DefaultInjectableSelectionStrategy injectableSelectionStrategy = new DefaultInjectableSelectionStrategy();
		CollectionFactory collectionFactory = new CollectionFactory();
		AnnotatedFieldRetriever annotatedFieldRetriever = new AnnotatedFieldRetriever();
		MockStrategy mockStrategy = new DefaultMockStrategy();

		injector = new CollectionInjector(collectionFactory, injectableSelectionStrategy, genericCollectionTypeResolver);
		factory = new InjectionDetailsFactory(annotatedFieldRetriever, genericCollectionTypeResolver);
		collectionInitialiser = new CollectionInitialiser(annotatedFieldRetriever, genericCollectionTypeResolver,
				collectionFactory, mockStrategy);
	}

	public static void inject(Object object) {
		collectionInitialiser.initialise(object);
		injector.inject(factory.createInjectionDetails(object));
	}

}
