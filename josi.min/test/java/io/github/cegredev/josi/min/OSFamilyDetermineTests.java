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

import org.junit.jupiter.api.Test;

import java.util.Locale;

import static io.github.cegredev.josi.min.OSFamily.*;
import static org.junit.jupiter.api.Assertions.*;

public class OSFamilyDetermineTests {

	private void test(OSFamily expected, String name) {
		assertEquals(expected, OSFamily.determine(name.toLowerCase(Locale.ROOT).trim()), "Determined wrong OSFamily!");
	}

	@Test
	public void testWin() {
		test(WINDOWS, "Windows XP");
		test(WINDOWS, "Windows 7");
		test(WINDOWS, "Windows 10");
		test(WINDOWS, "Windows 11");
	}

	@Test
	public void testMac() {
		test(MAC, "Mac");
		test(MAC, "MacOS X");
		test(MAC, "Mac OS X");
	}

	@Test
	public void testLinux() {
		test(LINUX, "Linux");
	}

	@Test
	public void testUnknown() {
		test(UNKNOWN, "Solaris");
		test(UNKNOWN, "does not exist");
	}

}
