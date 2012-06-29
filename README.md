mockito-collections
===================

Project to investigate the possibility of injecting objects into Collections using generics to determine which objects can be placed into the Collections. See https://groups.google.com/forum/#!topic/mockito/CfxbGNOewuI for details. Whilst this project is primarily led by a desire to add such functionality of Mockito, it would be nice to think that the solution would not need to be so tightly coupled to Mockito that it would be Mockito specific.

The Problem
-----------

When you use Mockito's @InjectMocks annotation it will not inject mocks into Collections. This results in the need to manually do so for example:

    @InjectMocks
    private MyDelegate delegate = new MyDelegate();
    
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

The Soloution
-------------

Inspect the generics of Collection fields and if any mocks exist of suitable types a Collection (of the relevant type) be injected containing those mocks. In an ideal scenario, we would be able to rely on Mockito to deal with all of this for us, however we probably don't always want Mockito to do this. So ideally we would be able to specify an injection strategy for Mockito to use, this is something Mockito does not yet provide. The temporary workaround is to provide something similar to the MockitoAnnotations.injectMocks(). Okay so there is still some boilerplate code, but it is less verbose:

    @InjectMocks
    private MyDelegate delegate = new MyDelegate();
    
    @Mock
    private MyListener listener1;
    
    @Mock
    private MyListener listener2;
    
    @Before
    public void setup() {
        MockitoCollectionInjector.inject(this);
    }
