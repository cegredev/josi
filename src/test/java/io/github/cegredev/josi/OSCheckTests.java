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
 * Tests concerned with functionality that checks if a given operating system (family) is a specific one.
 */
public class OSCheckTests {

	@Test
	public void testIsOS() {
		String message = "OS was not recognized!";
		assertTrue(OS.WIN_10.is(OS.WIN_95, OS.WIN_10, OS.MAC_UNKNOWN), message);
		assertFalse(OS.WIN_8.is(OS.WIN_95, OS.WIN_10, OS.MAC_UNKNOWN), message);
	}

	@Test
	public void testIsAtLeastOS() {
		String message = "OS was not recognized!";
		assertTrue(OS.WIN_10.isAtLeast(OS.WIN_95), message);
		assertTrue(OS.WIN_7.isAtLeast(OS.WIN_7), message);
		assertFalse(OS.WIN_8.isAtLeast(OS.WIN_10), message);
		assertTrue(OS.MAC_OS_BIG_SUR.isAtLeast(OS.MAC_OS_SIERRA), message);
		assertTrue(OS.MAC_OS_CATALINA.isAtLeast(OS.MAC_OS_CATALINA), message);
		assertFalse(OS.MAC_OSX_LION.isAtLeast(OS.MAC_OSX_MOUNTAIN_LION), message);

		String notThrowMessage = "Did not throw even though OS was not allowed!";
		assertThrows(UnsupportedOSException.class, () -> OS.UBUNTU.isAtLeast(OS.UBUNTU),
		              notThrowMessage);
		assertThrows(UnsupportedOSException.class, () -> OS.WIN_10.isAtLeast(OS.MAC_OS_SIERRA),
		              notThrowMessage);
		assertThrows(UnsupportedOSException.class, () -> OS.MAC_OS_BIG_SUR.isAtLeast(OS.WIN_7),
		              notThrowMessage);
	}

	@Test
	public void testIsFamily() {
		String message = "OS family was not recognized!";
		assertTrue(OS.WIN_10.isFamily(OS.Family.WINDOWS, OS.Family.MAC), message);
		assertFalse(OS.MAC_UNKNOWN.isFamily(OS.Family.LINUX, OS.Family.OTHER), message);
	}

	@Test
	public void testEnforceOS() {
		String threwMessage = "Threw even though OS was allowed!",
				notThrowMessage = "Did not throw even though OS was not allowed!";

		assertDoesNotThrow(() -> OS.WIN_10.enforce(OS.WIN_95, OS.WIN_10), threwMessage);
		assertThrows(UnsupportedOSException.class, () -> OS.MAC_UNKNOWN.enforce(OS.WIN_8, OS.LINUX_UNKNOWN),
				notThrowMessage);
		assertDoesNotThrow(() -> OS.LINUX_UNKNOWN.enforceNot(OS.WIN_10, OS.MAC_UNKNOWN), threwMessage);
		assertThrows(UnsupportedOSException.class, () -> OS.MAC_UNKNOWN.enforceNot(OS.MAC_UNKNOWN, OS.LINUX_UNKNOWN),
				notThrowMessage);
	}

	@Test
	public void testEnforceFamily() {
		String threwMessage = "Threw even though OS family was allowed!",
				notThrowMessage = "Did not throw even though OS family was not allowed!";

		assertDoesNotThrow(() -> OS.WIN_10.enforceFamily(OS.Family.WINDOWS, OS.Family.LINUX), threwMessage);
		assertThrows(UnsupportedOSException.class, () -> OS.LINUX_UNKNOWN.enforceFamily(OS.Family.MAC, OS.Family.OTHER),
				notThrowMessage);
		assertDoesNotThrow(() -> OS.WIN_10.enforceNotFamily(OS.Family.MAC, OS.Family.LINUX), threwMessage);
		assertThrows(UnsupportedOSException.class, () -> OS.MAC_UNKNOWN.enforceNotFamily(OS.Family.MAC,
				OS.Family.OTHER), notThrowMessage);
	}

}
