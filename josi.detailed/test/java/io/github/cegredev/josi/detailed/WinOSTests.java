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

import static io.github.cegredev.josi.detailed.WinOS.Release.*;
import static org.junit.jupiter.api.Assertions.*;

public class WinOSTests {

	private static void testDetermineHelper(WinOS.Release expected, String version) {
		version = version.toLowerCase(Locale.ROOT).trim();
		assertEquals(new WinOS(expected, false), OS.determine(OSFamily.WINDOWS, "windows " + version, version, null),
				"Did not determine the expected release from version \"" + version + "\"!");
	}

	@Test
	public void testDetermine() {
		testDetermineHelper(UNKNOWN, "1.0");
		testDetermineHelper(WIN_95, "95");
		testDetermineHelper(WIN_98, "98");
		testDetermineHelper(WIN_XP, "XP");
		testDetermineHelper(WIN_VISTA, "Vista");
		testDetermineHelper(WIN_7, "7");
		testDetermineHelper(WIN_8, "8");
		testDetermineHelper(WIN_8_1, "8.1");
		testDetermineHelper(WIN_10, "10");
		testDetermineHelper(WIN_11, "11");
	}

}
