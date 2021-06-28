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
package io.github.cegredev.josi.min;

import io.github.cegredev.josi.min.OSFamily;
import io.github.cegredev.josi.min.UnsupportedOSFamilyException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * "Tests" {@link UnsupportedOSFamilyException}. This is really just here for the coverage.
 */
public class UnsupportedOSFamilyExceptionTests {

	@Test
	public void testIncludesOSName() {
		// This is... Interesting. I mean it does what the test name suggests but should there really be a test for it?
		// Gotta get that 100% coverage I guess o.O
		assertTrue(new UnsupportedOSFamilyException(OSFamily.WINDOWS).getMessage().contains(OSFamily.WINDOWS.toString()));
	}

	@Test
	public void testUsesCurrent() {
		assertTrue(new UnsupportedOSFamilyException().getMessage().contains(OSFamily.current().toString()));
	}

}
