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
 * Examples of how to pick values or execute code based on the current operating system.
 *
 * @author cegredev
 */
public class DoStuffBasedOnOperatingSystem {

	public static void getCurrentOperatingSystem() {
		// Gets the current operating system
		OS os = OS.current();

		// Gets the current operating system family
		OS.Family family = os.getFamily();

		System.out.println("Current operating system: " + os + " which is part of the " + family + "family.");
	}

	public static void chooseFromAllOperatingSystemFamilies() {
		// Chooses the correct value based on the current operating system:
		// On Windows: "Windows", on Mac: "Mac", on Linux: "Linux", on other: "Other"
		String currentOS = OS.current().pick("Windows", "Mac", "Linux", "Other");

		System.out.println("The current operating system is " + currentOS);
	}

	public static void chooseFromLimitedOperatingSystemFamilies() {
		// Chooses the correct value between Mac, Linux or anything else, for example:
		// On Windows: "other", on Mac: "creative", on Linux: "techy", on other: "other"
		String character = OS.current().pickMacLinuxAny("creative", "techy", "other");

		// Chooses the correct value between Linux and anything else, for example:
		// On Linux: false, on Windows, Mac or anything else: true
		boolean filthyCasual = OS.current().pickLinuxAny(false, true);

		// There is one of these methods for every single combination, all following the same naming scheme,
		// so you can probably even guess their names with looking at the... d o c u m e n t a t i o n .
	}

	public static void executeBasedOnOperatingSystem() {
		// There are basically two ways to achieve this. The first and simplest is the following:
		// Since we often don't care about which exact version of an operating system we are running, we are just using
		// the *family* of the current OS, which can be one of the below values.
		switch (OS.current().getFamily()) {
			case WINDOWS:
				System.out.println("Windows!");
				break;
			case MAC:
				System.out.println("Mac!");
				break;
			case LINUX:
				System.out.println("Linux!");
				break;
			case OTHER:
				System.out.println("Other!");
				break;
		}

		// If you need more control, you can of course switch on the OS itself as well. However, this is more
		// error-prone as there are lots more enum constants for the OS datatype, so you should use the family
		// whenever possible.
		switch (OS.current()) {
			case WIN_95:
				System.out.println("Upgrade your PC, my god!");
				break;
			case WIN_8:
			case WIN_8_1:
				System.out.println("You are... special!");
				break;
			case WIN_7:
			case WIN_10:
				System.out.println("You are awesome!");
				break;
		}
	}

}
