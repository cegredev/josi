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

public class OSCheckTests {

	@Test
	public void testIsOS() {
		assertTrue(OS.WIN_10.is(OS.WIN_95, OS.WIN_10, OS.MAC), "OS was not recognized!");
		assertFalse(OS.WIN_8.is(OS.WIN_95, OS.WIN_10, OS.MAC), "OS was not recognized!");
	}

	@Test
	public void testIsFamily() {
		assertTrue(OS.WIN_10.isFamily(OS.Family.WINDOWS, OS.Family.MAC), "OS family was not recognized!");
		assertFalse(OS.MAC.isFamily(OS.Family.LINUX, OS.Family.OTHER), "OS family was not recognized!");
	}

	@Test
	public void testEnforceOS() {
		assertDoesNotThrow(() -> OS.WIN_10.enforce(OS.WIN_95, OS.WIN_10), "Threw even though OS was allowed!");
		assertThrows(UnsupportedOSException.class, () -> OS.MAC.enforce(OS.WIN_8, OS.LINUX),
				"Did not throw even though OS was not allowed!");
		assertDoesNotThrow(() -> OS.LINUX.enforceNot(OS.WIN_10, OS.MAC),
				"Threw even though OS was allowed!");
		assertThrows(UnsupportedOSException.class, () -> OS.MAC.enforceNot(OS.MAC, OS.LINUX),
				"Did not throw even though OS was not allowed!");
	}

	@Test
	public void testEnforceFamily() {
		assertDoesNotThrow(() -> OS.WIN_10.enforceFamily(OS.Family.WINDOWS, OS.Family.LINUX),
				"Threw even though OS family was allowed!");
		assertThrows(UnsupportedOSException.class, () -> OS.LINUX.enforceFamily(OS.Family.MAC,
				OS.Family.OTHER), "Did not throw even though OS family was not allowed!");
		assertDoesNotThrow(() -> OS.WIN_10.enforceNotFamily(OS.Family.MAC, OS.Family.LINUX),
				"Threw even though OS family was allowed!");
		assertThrows(UnsupportedOSException.class, () -> OS.MAC.enforceNotFamily(OS.Family.MAC,
				OS.Family.OTHER), "Did not throw even though OS family was not allowed!");
	}

}
