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
package main;

import io.github.cegredev.josi.OS;

/**
 * Examples of how to check and enforce the current operating system.
 *
 * @author cegredev
 */
public class CheckCurrentOperatingSystem {

	public static void checkIfOperatingSystemIsTheDesiredOne() {
		// Checks whether the OS is any of the given ones
		boolean isHated = OS.current().is(OS.WIN_8, OS.WIN_8_1);

		// Checks whether the OS is part of the given families
		boolean isUnixBased = OS.current().isFamily(OS.Family.MAC, OS.Family.LINUX);

		// Note that these operations are not static, meaning they can be executed on any OS constant, not just the
		// current one.
	}

	public static void enforceSpecificOperatingSystem() {
		// Throws an exception if the operating system is one of the given ones
		OS.current().enforceNot(OS.WIN_95, OS.WIN_95, OS.WIN_VISTA);

		// Throws an exception if the operating system family is not part of the given ones
		OS.current().enforceFamily(OS.Family.WINDOWS, OS.Family.MAC);

		// Only returns the value if the current operating system is Windows, otherwise throws an exception.
		// Useful for oneliners, where you want to assign a value and check for the OS in the same step
		boolean gamer = OS.current().pickWindows(true);
	}

}
