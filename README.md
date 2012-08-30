mockito-collections
===================

Project to investigate the possibility of injecting objects into Collections using generics to determine which objects can be placed into the Collections. See https://groups.google.com/forum/#!topic/mockito/CfxbGNOewuI for details. Whilst this project is primarily led by a desire to add such functionality to Mockito, it would be nice to think that the solution would not need to be so tightly coupled to Mockito that it would be Mockito specific.

[![Build Status](https://buildhive.cloudbees.com/job/jameskennard/job/mockito-collections/badge/icon)](https://buildhive.cloudbees.com/job/jameskennard/job/mockito-collections/)

The Problem
-----------

When you use Mockito's @InjectMocks annotation it will not inject mocks into Collections. This results in the need to manually do so for example:

    @InjectMocks
    private MyDelegate delegate;
    
    @Mock
    private MyListener listener1;
    
    @Mock
    private MyListener listener2;
    
    @Before
    public void setup() {
        Set<MyListener> lisenters = new HashSet<MyListener>(Arrays.asList(listener1, listener2));
        delegate.setListeners(lisenters);
    }

We end up writing boiler plate code again and again specifically to deal with this scenario. This is laborious and a pain to maintain, especially when injecting many Collections.

The Solution
-------------

The solution proposed by this project is to inspect the generics of the Collection fields on @InjectMocks annotated fields. Once the type of the elements of the Collection has been established, create a new Collection and add all of the mocks of the correct type to the Collection. And finally inject the new Collection into the relevant field.

In an ideal scenario, we would be able to rely on the MockitoJUnitRunner to deal with this for us by specifying some sort of injection strategy. This is something Mockito does not yet provide. The workaround is to provide something similar to MockitoAnnotations.injectMocks(). Okay so there is still some boilerplate code, but it is far less verbose.

    @InjectMocks
    private MyDelegate delegate;
    
    @Mock
    private MyListener listener1;
    
    @Mock
    private MyListener listener2;
    
    @Before
    public void setup() {
        MockitoCollectionAnnotations.inject(this);
    }

Beyond the Basics
=================

In addition to the basic usage described above, mockito-collections provides a number of its own annotations that can provide further support for dealing with Collections in tests. There are three key concepts:

* An <i>injectCollections</i> is an Object into which we want to inject Collections of injectables.
* An <i>injectable</i> is an Object that can be injected into Collections in injectCollections.
* An <i>injectableCollection</i> is a {@link Collection} that can be injected verbatim into Collections in injectCollections.

Fields annotated with the @InjectMocks annotation are considered to be injectCollections. It is possible to prevent a field annotated with @InjectMocks being considered an injectCollections using the @IgnoreInjectee annotation. And it is also possible to make fields that are not annotated with @InjectMocks considered to be injectCollections using the @InjectCollections annotation.

    @InjectMocks
    @IgnoreInjectee
    private MyDelegate delegate;
    
    @InjectCollections
    private MyDelegate otherDelegate = new MyDelegate;

Fields annotated with the @Mock annotation are considered to be injectables. Like fields annotated with @InjectMocks, it is possible to prevent a field annotated with @Mock being considered an injectable using the @IgnoreInjectable annotation. And again, it is possible to make fields that are not annotated with @Mock considered to be injectables using the @Injectable annotation.

    @Mock
    @IgnoreInjectable
    private MyListener listener1;
    
    @Injectable
    private MyListener listener2 = new MyListener();

The @InjectableCollection takes a different slant on the approach shown so far. Rather that injecting elements into a Collection field, it can be used to inject a Collection verbatim into a Collection field. The obvious consequence of this is that fields annotated with @InjectableCollection must be Collection fields. These Collections will be injected when the generics and raw type are equal to that of an injectCollections field. This means Collection<InputStream> and Collection<FileInputStream> would not be considered equal nor would Collection<InputStream> and Set<InputStream>.

    @InjectableCollection
    private Collection<MyListener> listeners = Collections.emptyList();

In this form this annotation could be useful when you want to check the internal state of an object that would otherwise require reflection or use of getters. For example, testing an add method:

    @InjectMocks
    private MyDelegate delegate;

    @InjectableCollection
    private Collection<MyListener> listeners = Collections.emptyList();
    
    @Test
    public void shuoldAdd() {
        // Given
        MyListener listener = mock(MyListener.class);
        
        // When
        delegate.add(listener);
        
        // Then
        assertTrue(listeners.contains(listener));
    }

The last annotation is intended to compliment the @InjectableCollection annotation. It is different from all the other annotations in that it is not related to injection, but setup of mocks. The @CollectionOfMocks annotation can be used to create a Collection containing a specified number of mocks (by default this is one).
    
    @CollectionOfMocks(numberOfMocks = 2)
    @InjectableCollection
    private Set<MyListener> listeners;

