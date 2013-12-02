Mockito-Collections
===================

This project aims to compliment the Java [Mockito](https://github.com/mockito/mockito) library by adding the ability to inject Collections of Mocks and Spies into objects under test; Thereby eradicating the need to manually set Collections of Mocks and Spies. See https://groups.google.com/forum/#!topic/mockito/CfxbGNOewuI for initial ideas.

[![Build Status](https://buildhive.cloudbees.com/job/jameskennard/job/mockito-collections/badge/icon)](https://buildhive.cloudbees.com/job/jameskennard/job/mockito-collections/)

The Problem
-----------

When you use Mockito's @InjectMocks annotation or MockitoAnnotations.initMocks(Object) mocks will not be injected into Collections. This results in the need to do so manually for example:

    @InjectMocks
    private MyDelegate delegate;
    
    @Mock
    private MyListener listener1;
    
    @Spy
    private MyListener listener2 = new MyListener();
    
    @Before
    public void setup() {
        Set<MyListener> listeners = new LinkedHashSet<MyListener>();
        listeners.add(listener1);
        listeners.add(listener2);
        delegate.setListeners(listeners);
    }

We end up writing boilerplate code again and again specifically to deal with this scenario. This is laborious and a pain to maintain, especially when injecting many Collections.

The Solution
------------

The solution proposed by this project is to inspect the generics of the Collection fields in the class under test, _denoted by the @InjectMocks annotation_. Once the type of the elements of the Collection have been established, create a new Collection and add all of the mocks and spies of the correct type to the Collection. And finally, inject the new Collection into the relevant field.

In an ideal scenario, we would be able to rely on the MockitoJUnitRunner to deal with this for us by specifying some sort of injection strategy. This is something Mockito does not yet provide. For now, the workaround is to provide something similar to MockitoAnnotations.injectMocks(). Okay so there is still some boilerplate code, but it is far less verbose.

    @InjectMocks
    private MyDelegate delegate;
    
    @Mock
    private MyListener listener1;
    
    @Spy
    private MyListener listener2 = new MyListener();
    
    @Before
    public void setup() {
        MockitoCollections.initialise(this);
    }

Ignoring a Mock when or Spy injecting into a Collection
-------------------------------------------------------

Fields annotated with the @Mock or @Spy annotation are considered for injection into Collection fields. It is possible to prevent a field annotated with @Mock or @Spy being considered for this purpose using the @IgnoreForCollections annotation. 

    @Mock
    @IgnoreForCollections
    private MyListener listener1;
    
    @Spy
    @IgnoreForCollections
    private MyListener listener2 = new MyListener();

Verbatim Collections
--------------------

The @CollectionOfMocks annotation can be used to create a Collection containing a specified number of mocks (by default this is one). In addition to creating a Collection of Mocks, the Collection will be considered for injection into Collection fields verbatim. These Collections will be injected when the generics are identical. This means Collection&lt;InputStream&gt; and Collection&lt;FileInputStream&gt; would not be considered equal nor would Collection&lt;InputStream&gt; and Set&lt;InputStream&gt; but List&lt;InputStream&gt; and List&lt;InputStream&gt; would.
    
    @CollectionOfMocks(numberOfMocks = 2)
    private Set<MyListener> listeners;

Collective Verification
-----------------------

To verify the same thing on a number of mocks or spies we would typically make a call to the static Mockito.verify* methods for each mock/spy. Mockito-Collections provides equivalent static methods allowing us to make verifications in a single step.

Imagine we have a Collection of Listeners and we want to verify that eventOccured(someEvent) was invoked once for every Listener. We would use the static MockitoCollections.collectiveVerify(Class, Collection) method.

    MockitoCollections.collectiveVerify(MyListener.class, listeners).eventOccured(someEvent);
    
Other collective verifications we can make using Mockito-Collections include verifying with a specified VerificationMode (for example times(int) and atLeastOnce()), verifying zero interactions, and verifying no more interactions.
