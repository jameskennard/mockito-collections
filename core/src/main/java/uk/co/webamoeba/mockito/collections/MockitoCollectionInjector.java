package uk.co.webamoeba.mockito.collections;

import java.lang.reflect.Field;
import java.util.Collection;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import uk.co.webamoeba.mockito.collections.annotation.Injectable;
import uk.co.webamoeba.mockito.collections.annotation.Injectee;
import uk.co.webamoeba.mockito.collections.util.AnnotatedFieldRetriever;
import uk.co.webamoeba.mockito.collections.util.GenericCollectionTypeResolver;

/**
 * Utility class to orchestrate the injection of {@link Collection Collections} of Mockito Mocks. This class will scan
 * an {@link Object}, typically a Mockito style unit test, and inject the values of {@link Field Fields} annotated with
 * {@link Mock} or {@link Injectable} into new {@link Collection Collections} on {@link Field Fields} within
 * {@link Field Fields} annotated with {@link InjectMocks} or {@link Injectee}.
 * 
 * <pre>
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
 *     MockitoCollectionInjector(this);
 *     assert objectUnderTest.getEventListeners().contains(eventListener);
 * }
 * </pre>
 * 
 * @author James Kennard
 */
public class MockitoCollectionInjector {

    private static CollectionInjector injector = new CollectionInjector(new CollectionFactory(),
	    new DefaultInjectableSelectionStrategy(), new GenericCollectionTypeResolver());

    private static MockitoInjectionDetailsFactory factory = new MockitoInjectionDetailsFactory(
	    new AnnotatedFieldRetriever());

    public static void inject(Object object) {
	injector.inject(factory.createInjectionDetails(object));
    }

}
