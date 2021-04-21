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

public class OSNameTests {

	private static final String SEPARATOR = ":";

	// The problem with this is, that it relies on the naming of all the OSs in the OS enum to check the result of
	// the determine method. So if any of those names get changed, you'll have to modify them here as well
	private static final String[] windowsNames = {"UNKNOWN:Windows fj12l3j123", "95:Windows 95", "98:Windows 98",
			"XP:Windows XP", "VISTA:Windows Vista", "7:Windows 7", "8:Windows 8", "8_1:Windows 8.1", "10:Windows 10"};

	private static final String[] macNames = {":Mac"};

	private static final String[] linuxNames = {"LINUX:Linux"};

	private static final String[] otherNames = {"OTHER:an_os_that_does_not_exist"};

	@Test
	public void testWindowsDetermine() {
		for (String os : windowsNames) {
			String[] split = os.split(SEPARATOR);

			assertEquals(OS.valueOf("WIN_" + split[0]), OS.determine(split[1]),
					"Did not determine correct OS for name.");
		}
	}

	@Test
	public void testMacDetermine() {
		for (String os : macNames) {
			String[] split = os.split(SEPARATOR);

			assertEquals(OS.valueOf("MAC" + split[0]), OS.determine(split[1]),
					"Did not determine correct OS for name.");
		}
	}

	@Test
	public void testLinuxDetermine() {
		for (String os : linuxNames) {
			String[] split = os.split(SEPARATOR);

			assertEquals(OS.valueOf(split[0]), OS.determine(split[1]), "Did not determine correct OS for name.");
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
