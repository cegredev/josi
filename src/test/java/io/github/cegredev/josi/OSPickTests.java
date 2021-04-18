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
