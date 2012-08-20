package uk.co.webamoeba.mockito.collections;

/**
 * Strategy that can be used to create mocks, the idea is that not only should you be able to instantiate Mockito style
 * mocks, but any other type of mock.
 * 
 * @author James Kennard
 */
public interface MockStrategy {

    /**
     * @param clazz
     * @return A new mock in place of the specified type.
     */
    public <T extends Object> T createMock(Class<T> clazz);

}
