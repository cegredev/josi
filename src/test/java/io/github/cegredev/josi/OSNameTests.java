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

public class OSNameTests {

	private static final String SEPARATOR = ":";

	private static final String[] windowsNames = {"UNKNOWN:Windows fj12l3j123", "95:Windows 95", "98:Windows 98",
			"XP:Windows XP", "VISTA:Windows Vista", "7:Windows 7", "8:Windows 8", "8_1:Windows 8.1", "10:Windows 10"};

	private static final String[] macNames = {"UNKNOWN:Mac"};

	private static final String[] linuxNames = {"LINUX_UNKNOWN:Linux"};

	private static final String[] otherNames = {"OTHER:an_os_that_does_not_exist"};

	@Test
	public void testWindowsDetermine() {
		// The problem with this is, that it relies on the naming of all the OSs in the OS enum to check the result of
		// the method. So if any of these names get changed, you'll at least have to modify the array above.
		for (String os : windowsNames) {
			String[] split = os.split(SEPARATOR);

			assertEquals("WIN_" + split[0], OS.determine(split[1]).toString(), "Did not determine" +
					"correct OS for name.");
		}
	}

	@Test
	public void testMacDetermine() {
		for (String os : macNames) {
			String[] split = os.split(SEPARATOR);

			assertEquals("MAC_" + split[0], OS.determine(split[1]).toString(), "Did not determine" +
					"correct OS for name.");
		}
	}

	@Test
	public void testLinuxDetermine() {
		for (String os : linuxNames) {
			String[] split = os.split(SEPARATOR);

			assertEquals(split[0], OS.determine(split[1]).toString(), "Did not determine correct OS for name.");
		}
	}

	@Test
	public void testOtherDetermine() {
		for (String os : otherNames) {
			String[] split = os.split(SEPARATOR);

			assertEquals(split[0], OS.determine(split[1]).toString(), "Did not determine correct OS for name.");
		}
	}

}
