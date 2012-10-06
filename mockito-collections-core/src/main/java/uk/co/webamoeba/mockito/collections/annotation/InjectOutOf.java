package uk.co.webamoeba.mockito.collections.annotation;

public @interface InjectOutOf {
	// TODO This is an idea to inject a Collection (or any object) from the @InjectMocks annotated object into the test.
	// For example:
	// @InjectMocks
	// private MyDelegate delegate;
	//
	// @InjectOutOf
	// private Collection<MyListener> listeners;
	//
	// @Test
	// public void shuoldAdd() {
	// // Given
	// MyListener listener = mock(MyListener.class);
	//
	// // When
	// delegate.add(listener);
	//
	// // Then
	// assertTrue(listeners.contains(listener));
	// }
}
