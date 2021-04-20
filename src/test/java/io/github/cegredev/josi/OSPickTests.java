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
import static io.github.cegredev.josi.OS.Family.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class OSPickTests {

	private static OS.Family[] getRemainingFamilies(OS.Family... covered) {
		List<OS.Family> list = new ArrayList<>(Arrays.asList(OS.Family.values()));
		list.removeAll(Arrays.asList(covered));
		return list.toArray(new OS.Family[0]);
	}

	private static void testPickHelper(Function<OS, Integer> test, OS.Family... families) {
		for (int i = 0; i < families.length; i++)
			assertEquals(i, test.apply(families[i].getRepresentative()),
					"OS did not pick correct value!");

		for (OS.Family family : getRemainingFamilies(families))
			assertThrows(UnsupportedOSException.class, () -> test.apply(family.getRepresentative()));
	}

	private static void testPickAnyHelper(Function<OS, Integer> test, int... expectedValues) {
		OS.Family[] families = OS.Family.values();

		for (int i = 0; i < families.length; i++) {
			assertEquals(expectedValues[i], test.apply(families[i].getRepresentative()),
					"OS did not pick correct value!");
		}
	}

	@Test
	public void testPick() {
		final OS.Family[] all = OS.Family.values();

		testPickHelper(os -> os.pick(0, 1, 2, 3), all);
		testPickHelper(os -> os.pick(0, 1, 2), WINDOWS, MAC, LINUX);
		testPickAnyHelper(os -> os.pickWinMacAny(0, 1, 2), 0, 1, 2, 2);
		testPickAnyHelper(os -> os.pickWinLinuxAny(0, 1, 2), 0, 2, 1, 2);
		testPickAnyHelper(os -> os.pickMacLinuxAny(0, 1, 2), 2, 0, 1, 2);
		testPickHelper(os -> os.pickWinMac(0, 1), WINDOWS, MAC);
		testPickHelper(os -> os.pickWinLinux(0, 1), WINDOWS, LINUX);
		testPickHelper(os -> os.pickMacLinux(0, 1), MAC, LINUX);
		testPickAnyHelper(os -> os.pickWindowsAny(0, 1), 0, 1, 1, 1);
		testPickAnyHelper(os -> os.pickMacAny(0, 1), 1, 0, 1, 1);
		testPickAnyHelper(os -> os.pickLinuxAny(0, 1), 1, 1, 0, 1);
		testPickHelper(os -> os.pickWindows(0), WINDOWS);
		testPickHelper(os -> os.pickMac(0), MAC);
		testPickHelper(os -> os.pickLinux(0), LINUX);
	}
}
