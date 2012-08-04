package uk.co.webamoeba.mockito.collections.exception;

public class MockitoCollectionsException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public MockitoCollectionsException(String message, Throwable throwable) {
	super(message, throwable);
    }

    public MockitoCollectionsException(String message) {
	super(message);
    }
}
