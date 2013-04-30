/**
 * The JUnit-addons Software License, Version 1.0
 *     (based on the Apache Software License, Version 1.1)
 *
 * Copyright (c) 2002-2003 Vladimir R. Bossicard.  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution, if
 *    any, must include the following acknowlegement:
 *       "This product includes software developed by Vladimir R.
 *        Bossicard as well as other contributors
 *        (http://junit-addons.sourceforge.net/)."
 *    Alternately, this acknowlegement may appear in the software itself,
 *    if and wherever such third-party acknowlegements normally appear.
 *
 * 4. The name "JUnit-addons" must not be used to endorse or promote
 *    products derived from this software without prior written
 *    permission. For written permission, please contact
 *    vbossica@users.sourceforge.net.
 *
 * 5. Products derived from this software may not be called "JUnit-addons"
 *    nor may "JUnit-addons" appear in their names without prior written
 *    permission of the project managers.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ======================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals.  For more information on the JUnit-addons Project, please
 * see <http://junit-addons.sourceforge.net/>.
 */
package uk.co.webamoeba.mockito.collections.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.fail;
import junit.framework.AssertionFailedError;

import org.junit.Before;
import org.junit.Test;

/**
 * Base class to test functional compliance with the <code>equals</code> and <code>hashCode</code> contract.
 * <p>
 * Ported from java-addons and updated for junit 4.
 * 
 * @see java.lang.Object#equals(Object)
 * @see java.lang.Object#hashCode()
 * @author <a href="mailto:pholser@yahoo.com">Paul Holser</a>
 * @author James Kennard
 */
// TODO Would be nice to think about moving this to a separate project
public abstract class EqualsHashCodeTestCase {

	private Object eq1;

	private Object eq2;

	private Object eq3;

	private Object neq;

	private static final int NUM_ITERATIONS = 20;

	/**
	 * Creates and returns an instance of the class under test.
	 * 
	 * @return a new instance of the class under test; each object returned from this method should compare equal to
	 *         each other.
	 * @throws Exception
	 */
	protected abstract Object createInstance() throws Exception;

	/**
	 * Creates and returns an instance of the class under test.
	 * 
	 * @return a new instance of the class under test; each object returned from this method should compare equal to
	 *         each other, but not to the objects returned from {@link #createInstance() createInstance}.
	 * @throws Exception
	 */
	protected abstract Object createNotEqualInstance() throws Exception;

	@Before
	public void setUp() throws Exception {
		eq1 = createInstance();
		eq2 = createInstance();
		eq3 = createInstance();
		neq = createNotEqualInstance();

		// We want these assertions to yield errors, not failures.
		try {
			assertNotNull("createInstance() returned null", eq1);
			assertNotNull("2nd createInstance() returned null", eq2);
			assertNotNull("3rd createInstance() returned null", eq3);
			assertNotNull("createNotEqualInstance() returned null", neq);

			assertNotSame(eq1, eq2);
			assertNotSame(eq1, eq3);
			assertNotSame(eq1, neq);
			assertNotSame(eq2, eq3);
			assertNotSame(eq2, neq);
			assertNotSame(eq3, neq);

			assertEquals("1st and 2nd equal instances of different classes", eq1.getClass(), eq2.getClass());
			assertEquals("1st and 3rd equal instances of different classes", eq1.getClass(), eq3.getClass());
			assertEquals("1st equal instance and not-equal instance of different classes", eq1.getClass(),
					neq.getClass());
		} catch (AssertionFailedError ex) {
			throw new IllegalArgumentException(ex.getMessage());
		}
	}

	@Test
	public void shouldNotEqualGivenNewObject() {
		Object o = new Object();

		assertNotEquals(eq1, o);
		assertNotEquals(eq2, o);
		assertNotEquals(eq3, o);
		assertNotEquals(neq, o);
	}

	@Test
	public void shouldNotEqualGivenNull() {
		assertNotEquals("1st vs. null", eq1, null);
		assertNotEquals("2nd vs. null", eq2, null);
		assertNotEquals("3rd vs. null", eq3, null);
		assertNotEquals("not-equal vs. null", neq, null);
	}

	@Test
	public void shouldNotEqualGivenUnequalObjects() {
		assertNotEquals("1st vs. not-equal", eq1, neq);
		assertNotEquals("2nd vs. not-equal", eq2, neq);
		assertNotEquals("3rd vs. not-equal", eq3, neq);

		assertNotEquals("not-equal vs. 1st", neq, eq1);
		assertNotEquals("not-equal vs. 2nd", neq, eq2);
		assertNotEquals("not-equal vs. 3rd", neq, eq3);
	}

	@Test
	public void shouldEqualConsistentlyAcrossInvocations() {
		for (int i = 0; i < NUM_ITERATIONS; ++i) {
			shouldNotEqualGivenNewObject();
			shouldNotEqualGivenNull();
			shouldNotEqualGivenUnequalObjects();
			shouldEqualSelf();
			shouldEqualSymmetricallyAndTransitively();
		}
	}

	@Test
	public void shouldEqualSelf() {
		assertEquals("1st equal instance", eq1, eq1);
		assertEquals("2nd equal instance", eq2, eq2);
		assertEquals("3rd equal instance", eq3, eq3);
		assertEquals("not-equal instance", neq, neq);
	}

	@Test
	public void shouldEqualSymmetricallyAndTransitively() {
		assertEquals("1st vs. 2nd", eq1, eq2);
		assertEquals("2nd vs. 1st", eq2, eq1);

		assertEquals("1st vs. 3rd", eq1, eq3);
		assertEquals("3rd vs. 1st", eq3, eq1);

		assertEquals("2nd vs. 3rd", eq2, eq3);
		assertEquals("3rd vs. 2nd", eq3, eq2);
	}

	@Test
	public void shouldMaintainHashCodeContract() {
		assertEquals("1st vs. 2nd", eq1.hashCode(), eq2.hashCode());
		assertEquals("1st vs. 3rd", eq1.hashCode(), eq3.hashCode());
		assertEquals("2nd vs. 3rd", eq2.hashCode(), eq3.hashCode());
	}

	@Test
	public void shouldMaintainHashCodeConsistentlyAcrossInvocations() {
		int eq1Hash = eq1.hashCode();
		int eq2Hash = eq2.hashCode();
		int eq3Hash = eq3.hashCode();
		int neqHash = neq.hashCode();

		for (int i = 0; i < NUM_ITERATIONS; ++i) {
			assertEquals("1st equal instance", eq1Hash, eq1.hashCode());
			assertEquals("2nd equal instance", eq2Hash, eq2.hashCode());
			assertEquals("3rd equal instance", eq3Hash, eq3.hashCode());
			assertEquals("not-equal instance", neqHash, neq.hashCode());
		}
	}

	/**
	 * Asserts that two objects are not equal. Throws an <tt>AssertionFailedError</tt> if they are equal.
	 */
	private void assertNotEquals(Object expected, Object actual) {
		assertNotEquals(null, expected, actual);
	}

	/**
	 * Asserts that two objects are not equal. Throws an <tt>AssertionFailedError</tt> if they are equal.
	 */
	private void assertNotEquals(String message, Object expected, Object actual) {
		if ((expected == null && actual == null) || (expected != null && expected.equals(actual))) {
			String formatted = "";
			if (message != null) {
				formatted = message + " ";
			}
			fail(formatted + "expected not equals to: <" + expected + ">");
		}
	}
}