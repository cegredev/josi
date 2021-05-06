/*
 * MIT License

 * Copyright (c) 2021 cegredev
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package io.github.cegredev.josi;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Used to quickly and dirtily test features during development.
 */
public class LazyTesting {

	@Test
	public void testAny() {
		assertFalse(new OSConstraint(OS.WIN_95).isFamily(OS.Family.WINDOWS).isNot(OS.WIN_95).check(), "");

		assertEquals("Unix", new OSConstraint(OS.MAC_OS_BIG_SUR).isFamily(OS.Family.LINUX, OS.Family.MAC).store(
				"Unix").isFamily(OS.Family.WINDOWS).store("Windows").get(), "Did not get correct value!");

		assertThrows(UnsupportedOSException.class,
				() -> new OSConstraint(OS.UBUNTU).isNotFamily(OS.Family.LINUX).enforce());

	}

}
