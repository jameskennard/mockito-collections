package uk.co.webamoeba.mockito.collections;

public class MockitoCollectionInjector {

    private static CollectionInjector injector = new CollectionInjector(new CollectionFactory(),
	    new DefaultInjectableSelectionStrategy());

    private static MockitoInjectionDetailsFactory factory = new MockitoInjectionDetailsFactory();

    public static void inject(Object object) {
	injector.inject(factory.createInjectionDetails(object));
    }

}
