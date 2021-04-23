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

/**
 * Tests concerning the different pick-themed utility methods in {@link OS}.
 */
public class OSPickTests {

	private static final OS.Family[] ALL_FAMILIES = OS.Family.values();

	/**
	 * @return The remaining families not covered by the given array.
	 */
	private static OS.Family[] getRemainingFamilies(OS.Family... covered) {
		List<OS.Family> list = new ArrayList<>(Arrays.asList(ALL_FAMILIES));
		list.removeAll(Arrays.asList(covered));
		return list.toArray(new OS.Family[0]);
	}

	private static void pickHelper(Function<OS, Integer> test, OS.Family... families) {
		for (int i = 0; i < families.length; i++)
			assertEquals(i, test.apply(families[i].getRepresentative()),
					"OS did not pick correct value!");

		for (OS.Family family : getRemainingFamilies(families))
			assertThrows(UnsupportedOSException.class, () -> test.apply(family.getRepresentative()),
					"Did not throw exception even though there was no value for the given OS!");
	}

	private static void pickAnyHelper(Function<OS, Integer> test, int... expectedValues) {
		// A little safety measurement to ensure that no one writes a broken test by accident
		if (expectedValues.length != 4)
			throw new AssertionError("BAD TEST! The expectedValues array has to have exactly 4 elements!");

		OS.Family[] families = OS.Family.values();

		for (int i = 0; i < families.length; i++) {
			assertEquals(expectedValues[i], test.apply(families[i].getRepresentative()),
					"OS did not pick correct value!");
		}
	}

	@Test
	public void testPick() {
		// This is extremely, extremely stupid, but it works. Basically, the values in the pick methods
		// represent the indices of the operating system in the family array that should return that value.
		// Additionally, the method has to throw an exception if it is called on one of the not present OSs
		// which is why we give it that changing array of families.

		pickHelper(os -> os.pick(0, 1, 2, 3), WINDOWS, MAC, LINUX, OTHER);
		pickHelper(os -> os.pick(0, 1, 2), WINDOWS, MAC, LINUX);
		pickHelper(os -> os.pickWinMac(0, 1), WINDOWS, MAC);
		pickHelper(os -> os.pickWinLinux(0, 1), WINDOWS, LINUX);
		pickHelper(os -> os.pickMacLinux(0, 1), MAC, LINUX);
		pickHelper(os -> os.pickWindows(0), WINDOWS);
		pickHelper(os -> os.pickMac(0), MAC);
		pickHelper(os -> os.pickLinux(0), LINUX);
	}

	@Test
	public void testPickAny() {
		// Again, readability is horrible here, but it works. This time, the values in the pick methods don't
		// represent indices, but could be any random value. Instead, the helper will iterate over all families
		// in the OS.Family enum and call the method on it. The returned value of that call is checked against
		// the expected value at the current iteration index, meaning the first value in the array is what the
		// test expects a Windows OS to return, the second a Mac one, the third a Linux one an the fourth any other.

		pickAnyHelper(os -> os.pickWinMacAny(0, 1, 2), 0, 1, 2, 2);
		pickAnyHelper(os -> os.pickWinLinuxAny(0, 1, 2), 0, 2, 1, 2);
		pickAnyHelper(os -> os.pickMacLinuxAny(0, 1, 2), 2, 0, 1, 2);
		pickAnyHelper(os -> os.pickWindowsAny(0, 1), 0, 1, 1, 1);
		pickAnyHelper(os -> os.pickMacAny(0, 1), 1, 0, 1, 1);
		pickAnyHelper(os -> os.pickLinuxAny(0, 1), 1, 1, 0, 1);
	}
}
