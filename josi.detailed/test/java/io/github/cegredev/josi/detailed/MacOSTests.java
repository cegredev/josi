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

import io.github.cegredev.josi.min.OSFamily;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

public class MacOSTests {

	private static void testDetermineHelper(int major, int minor, String nameExtension, String version) {
		assertEquals(new MacOS(major, minor),
				OS.determine(OSFamily.MAC, "mac" + nameExtension.toLowerCase(Locale.ROOT), version, null),
				"Did not determine the expected version(" + major + "." + minor + ")" +
						"from string \"" + version + "\"!");
	}

	private static void testDetermineHelper(int major, int minor, String version) {
		testDetermineHelper(major, minor, "", version);
	}

	@Test
	public void testDetermine() {
		testDetermineHelper(MacOS.UNKNOWN_MAJOR, 0, "");
		testDetermineHelper(1, 0, "1.0");
		testDetermineHelper(4, 0, "4.");
		testDetermineHelper(10, 0, "10");
		testDetermineHelper(10, 0, "OS X", "10.0");
		testDetermineHelper(10, 1, "OS X", "10.1.1");
		testDetermineHelper(10, 2, "OS X", "10.2.4");
		testDetermineHelper(10, 3, "10.3");
		testDetermineHelper(10, 4, "10.4");
		testDetermineHelper(10, 5, "10.5.1");
		testDetermineHelper(10, 6, "10.6");
		testDetermineHelper(10, 7, "10.7");
		testDetermineHelper(10, 8, "10.8");
		testDetermineHelper(10, 9, "10.9");
		testDetermineHelper(10, 10, "10.10");
		testDetermineHelper(10, 11, "10.11");
		testDetermineHelper(10, 12, "10.12");
		testDetermineHelper(10, 13, "10.13");
		testDetermineHelper(10, 14, "10.14");
		testDetermineHelper(10, 15, "10.15");
		testDetermineHelper(11, 0, "10.16");
		testDetermineHelper(11, 0, " OS X", "11.0");
		testDetermineHelper(11, 2, " OS X", "11.2.1");
		testDetermineHelper(12, 0, " OS X", "12.0");
		testDetermineHelper(12, 0, " OS X", "10.17");
	}

}
