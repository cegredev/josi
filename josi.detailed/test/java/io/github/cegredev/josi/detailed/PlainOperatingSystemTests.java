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
package io.github.cegredev.josi.detailed;

import io.github.cegredev.josi.min.OSFamily.*;
import org.junit.jupiter.api.Test;

import static io.github.cegredev.josi.min.OSFamily.*;
import static org.junit.jupiter.api.Assertions.*;

public class PlainOperatingSystemTests {

	private static final OperatingSystem win = new WinOS(WinOS.Release.WIN_7), mac = new MacOS(11, 0),
			linux = new LinuxOS(LinuxOS.Distribution.UBUNTU), other = new OtherOS(OtherOS.Identity.SOLARIS);


	@Test
	public void testIsFamily() {
		String isFamilyMsg = "OS was not recognized as part of the given families!",
				isNotFamilyMsg = "Os was wrongly recognized as part of the given families!";

		assertTrue(win.isFamily(MAC, WINDOWS), isFamilyMsg);
		assertTrue(win.isFamily(LINUX, WINDOWS, UNKNOWN), isFamilyMsg);
		assertFalse(win.isFamily(MAC, LINUX), isNotFamilyMsg);
		assertFalse(win.isFamily(UNKNOWN, LINUX), isNotFamilyMsg);

		assertTrue(mac.isFamily(WINDOWS, MAC), isFamilyMsg);
		assertTrue(mac.isFamily(LINUX, MAC, UNKNOWN), isFamilyMsg);
		assertFalse(mac.isFamily(WINDOWS, LINUX), isNotFamilyMsg);
		assertFalse(mac.isFamily(UNKNOWN, WINDOWS, LINUX), isNotFamilyMsg);

		assertTrue(linux.isFamily(WINDOWS, LINUX), isFamilyMsg);
		assertTrue(linux.isFamily(LINUX, MAC, UNKNOWN), isFamilyMsg);
		assertFalse(linux.isFamily(WINDOWS, MAC), isNotFamilyMsg);
		assertFalse(linux.isFamily(UNKNOWN, WINDOWS, MAC), isNotFamilyMsg);

		assertTrue(other.isFamily(WINDOWS, UNKNOWN), isFamilyMsg);
		assertTrue(other.isFamily(LINUX, UNKNOWN, MAC), isFamilyMsg);
		assertFalse(other.isFamily(WINDOWS, MAC), isNotFamilyMsg);
		assertFalse(other.isFamily(LINUX, WINDOWS, MAC), isNotFamilyMsg);
	}

	@Test
	public void testEquals() {
		String areEqualMsg = "Returned false for equal OSs!", areNotEqualMsg = "Returned true for unequal OSs!";

		assertEquals(win, win, areEqualMsg);
		assertEquals(mac, mac, areEqualMsg);
		assertEquals(linux, linux, areEqualMsg);
		assertEquals(other, other, areEqualMsg);

		assertNotEquals(new WinOS(WinOS.Release.WIN_8_1), win, areNotEqualMsg);
		assertNotEquals(new MacOS(10, 12), mac, areNotEqualMsg);
		assertNotEquals(new LinuxOS(LinuxOS.Distribution.DEBIAN), linux, areNotEqualMsg);
		assertNotEquals(new OtherOS(OtherOS.Identity.UNKNOWN), other, areNotEqualMsg);
	}

}
