package uk.co.webamoeba.mockito.collections.internal;

import java.lang.reflect.Field;
import java.util.Collection;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.co.webamoeba.mockito.collections.inject.CollectionFactory;
import uk.co.webamoeba.mockito.collections.inject.CollectionInjector;
import uk.co.webamoeba.mockito.collections.inject.CollectionOfMocksInitialiser;
import uk.co.webamoeba.mockito.collections.inject.DefaultSelectionStrategy;
import uk.co.webamoeba.mockito.collections.inject.DefaultMockStrategy;
import uk.co.webamoeba.mockito.collections.inject.InjectionDetailsFactory;
import uk.co.webamoeba.mockito.collections.inject.MockStrategy;
import uk.co.webamoeba.mockito.collections.util.AnnotatedFieldRetriever;
import uk.co.webamoeba.mockito.collections.util.GenericCollectionTypeResolver;

/**
 * Utility class, comparable to {@link MockitoAnnotations}, which orchestrates the injection of {@link Collection
 * Collections} of Mockito Mocks. This class will scan an {@link Object}, typically a Mockito style unit test, and
 * inject the values of {@link Field Fields} annotated with {@link Mock} into new {@link Collection Collections} on
 * {@link Field Fields} within {@link Field Fields} annotated with {@link InjectMocks}.
 * 
 * <pre class="code">
 * <code class="java">
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
 * </code>
 * </pre>
 * 
 * @author James Kennard
 */
public class Initialiser {

	private CollectionInjector injector;

	private InjectionDetailsFactory factory;

	private CollectionOfMocksInitialiser collectionOfMocksInitialiser;

	{
		GenericCollectionTypeResolver genericCollectionTypeResolver = new GenericCollectionTypeResolver();
		DefaultSelectionStrategy mockSelectionStrategy = new DefaultSelectionStrategy();
		CollectionFactory collectionFactory = new CollectionFactory();
		AnnotatedFieldRetriever annotatedFieldRetriever = new AnnotatedFieldRetriever();
		MockStrategy mockStrategy = new DefaultMockStrategy();

		injector = new CollectionInjector(collectionFactory, mockSelectionStrategy, genericCollectionTypeResolver);
		factory = new InjectionDetailsFactory(annotatedFieldRetriever, genericCollectionTypeResolver);
		collectionOfMocksInitialiser = new CollectionOfMocksInitialiser(annotatedFieldRetriever,
				genericCollectionTypeResolver, collectionFactory, mockStrategy);
	}

	public void initialise(Object object) {
		collectionOfMocksInitialiser.initialise(object);
		injector.inject(factory.createInjectionDetails(object));
	}

}
