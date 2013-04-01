package uk.co.webamoeba.mockito.collections.support;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

/**
 * {@link Matcher} capable of determining if the passed object is of type {@link Throwable} and if the cause of the
 * {@link Throwable} is the specified type. This {@link Matcher} will iterate over the entirety of the causes, that is
 * to say it matches irrespective of how many levels down the cause happens to be.
 * 
 * @author James Kennard
 */
public class ThrowableCausedByMatcher extends BaseMatcher<Throwable> {

	private Class<? extends Throwable> causedByClass;

	public ThrowableCausedByMatcher(Class<? extends Throwable> causedByClass) {
		super();
		this.causedByClass = causedByClass;
	}

	public boolean matches(Object object) {
		if (object == null) {
			return false;
		}
		if (!Throwable.class.isAssignableFrom(object.getClass())) {
			return false;
		}
		Throwable causedBy = ((Throwable) object).getCause();
		while (causedBy != null) {
			if (causedBy.getClass().equals(causedByClass)) {
				return true;
			}
			causedBy = causedBy.getCause();
		}
		return false;
	}

	public void describeTo(Description description) {
		description.appendText("throwable caused by " + causedByClass);
	}

}
