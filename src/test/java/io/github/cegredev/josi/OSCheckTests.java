/*
 * Copyright 2021 cegredev
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language governing permissions and limitations
 * under the License.
 */
package io.github.cegredev.josi;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OSCheckTests {

	@Test
	public void testIsOS() {
		assertTrue(OS.WIN_10.is(OS.WIN_95, OS.WIN_10, OS.MAC_UNKNOWN), "OS was not recognized!");
		assertFalse(OS.WIN_8.is(OS.WIN_95, OS.WIN_10, OS.MAC_UNKNOWN), "OS was not recognized!");
	}

	@Test
	public void testIsFamily() {
		assertTrue(OS.WIN_10.isFamily(OS.Family.WINDOWS, OS.Family.MAC), "OS family was not recognized!");
		assertFalse(OS.MAC_UNKNOWN.isFamily(OS.Family.LINUX, OS.Family.OTHER), "OS family was not recognized!");
	}

	@Test
	public void testEnforceOS() {
		assertDoesNotThrow(() -> OS.WIN_10.enforce(OS.WIN_95, OS.WIN_10), "Threw even though OS was allowed!");
		assertThrows(UnsupportedOSException.class, () -> OS.MAC_UNKNOWN.enforce(OS.WIN_8, OS.LINUX_UNKNOWN),
				"Did not throw even though OS was not allowed!");
		assertDoesNotThrow(() -> OS.LINUX_UNKNOWN.enforceNot(OS.WIN_10, OS.MAC_UNKNOWN),
				"Threw even though OS was allowed!");
		assertThrows(UnsupportedOSException.class, () -> OS.MAC_UNKNOWN.enforceNot(OS.MAC_UNKNOWN, OS.LINUX_UNKNOWN),
				"Did not throw even though OS was not allowed!");
	}

	@Test
	public void testEnforceFamily() {
		assertDoesNotThrow(() -> OS.WIN_10.enforceFamily(OS.Family.WINDOWS, OS.Family.LINUX),
				"Threw even though OS family was allowed!");
		assertThrows(UnsupportedOSException.class, () -> OS.LINUX_UNKNOWN.enforceFamily(OS.Family.MAC,
				OS.Family.OTHER), "Did not throw even though OS family was not allowed!");
		assertDoesNotThrow(() -> OS.WIN_10.enforceNotFamily(OS.Family.MAC, OS.Family.LINUX),
				"Threw even though OS family was allowed!");
		assertThrows(UnsupportedOSException.class, () -> OS.MAC_UNKNOWN.enforceNotFamily(OS.Family.MAC,
				OS.Family.OTHER), "Did not throw even though OS family was not allowed!");
	}

}
