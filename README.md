mockito-collections
===================

Project to investigate the possibility of injecting objects into Collections using generics to determine which objects can be placed into the Collections. See https://groups.google.com/forum/#!topic/mockito/CfxbGNOewuI for details. Whilst this project is primarily led by a desire to add such functionality to Mockito, it would be nice to think that the solution would not need to be so tightly coupled to Mockito that it would be Mockito specific.

[![Build Status](https://buildhive.cloudbees.com/job/jameskennard/job/mockito-collections/badge/icon)](https://buildhive.cloudbees.com/job/jameskennard/job/mockito-collections/)

The Problem
-----------

When you use Mockito's @InjectMocks annotation it will not inject mocks into Collections. This results in the need to do so manually for example:

    @InjectMocks
    private MyDelegate delegate;
    
    @Mock
    private MyListener listener1;
    
    @Mock
    private MyListener listener2;
    
    @Before
    public void setup() {
        Set<MyListener> listeners = new HashSet<MyListener>(Arrays.asList(listener1, listener2));
        delegate.setListeners(listeners);
    }

We end up writing boiler plate code again and again specifically to deal with this scenario. This is laborious and a pain to maintain, especially when injecting many Collections.

The Solution
-------------

The solution proposed by this project is to inspect the generics of the Collection fields on @InjectMocks annotated fields. Once the type of the elements of the Collection has been established, create a new Collection and add all of the mocks of the correct type to the Collection. And finally inject the new Collection into the relevant field.

In an ideal scenario, we would be able to rely on the MockitoJUnitRunner to deal with this for us by specifying some sort of injection strategy. This is something Mockito does not yet provide. For now, the workaround is to provide something similar to MockitoAnnotations.injectMocks(). Okay so there is still some boilerplate code, but it is far less verbose.

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

Ignoring a Mock when injecting into a Collection
------------------------------------------------

Fields annotated with the @Mock annotation are considered for injection into Collection fields. It is possible to prevent a field annotated with @Mock being considered for this purpose using the @IgnoreMockForCollections annotation. 

    @Mock
    @IgnoreMockForCollections
    private MyListener listener1;

Verbatim Collections
--------------------

The @CollectionOfMocks annotation can be used to create a Collection containing a specified number of mocks (by default this is one). In addition to creating a Collection of Mocks, the Collection will be considered for injection into Collection fields verbatim. The obvious consequence of this is that fields annotated with @CollectionOfMocks must be Collection fields. These Collections will be injected when the generics and raw type are identical. This means Collection&lt;InputStream&gt; and Collection&lt;FileInputStream&gt; would not be considered equal nor would Collection&lt;InputStream&gt and Set&lt;InputStream&gt;.
    
    @CollectionOfMocks(numberOfMocks = 2)
    private Set<MyListener> listeners;

