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
package io.github.cegredev.jos;

import java.util.Arrays;
import java.util.Locale;

import static io.github.cegredev.jos.OS.Family.*;

/**
 * Contains a list of various operating system families and their versions, as well as the OS used on the current PC and
 * methods to help deal with different operating systems.
 *
 * @author cegredev
 */
public enum OS {

	// I am well aware that some of these windows version might be overkill, but they were included in this answer:
	// https://stackoverflow.com/a/31110542/11213660 which is where I got most of the windows code from
	WIN_95(WINDOWS), WIN_98(WINDOWS), WIN_XP(WINDOWS), WIN_VISTA(WINDOWS), WIN_7(WINDOWS), WIN_8(WINDOWS),
	WIN_8_1(WINDOWS), WIN_10(WINDOWS), WIN_ANY(WINDOWS),
	MAC_ANY(Family.MAC),
	LINUX_ANY(LINUX),
	OTHER(Family.OTHER);

	/**
	 * The current operating system on the running PC.
	 */
	private static final OS CURRENT = determine(System.getProperty("os.name"));

	/**
	 * The family the operating system belongs to.
	 */
	private final Family family;

	/**
	 * @param family The family the operating system belongs to.
	 */
	OS(Family family) {
		this.family = family;
	}

	/**
	 * @param osName The name to determine the operating system from. Expects values in the format of {@code
	 *               System.getProperty("os.name")}.
	 * @return An operating system matching the given name.
	 */
	static OS determine(String osName) {
		// TODO: Implement with more detail

		// Locale.ROOT prevents funny locale stuff from happening
		osName = osName.toLowerCase(Locale.ROOT).trim();

		// Decide Windows version
		if (osName.startsWith("win")) {
			int lastSpace = osName.lastIndexOf(' ');

			if (lastSpace > -1) {
				switch (osName.substring(lastSpace + 1)) {
					case "95":
						return WIN_95;
					case "98":
						return WIN_98;
					case "xp":
						return WIN_XP;
					case "vista":
						return WIN_VISTA;
					case "7":
						return WIN_7;
					case "8":
						return WIN_8;
					case "8.1":
						return WIN_8_1;
					case "10":
						return WIN_10;
					default:
						break;
				}
			}

			return WIN_ANY;
		}

		// Decide Mac version
		if (osName.startsWith("mac")) {
			return MAC_ANY;
		}

		// Decide Linux version
		if (osName.contains("nix") || osName.contains("nux") || osName.contains("aix")) {
			return LINUX_ANY;
		}

		return OTHER;
	}

	/**
	 * Picks the correct value for the current operating system family.
	 *
	 * @param windows The value if the current OS family is {@link Family#WINDOWS}.
	 * @param mac     The value if the current OS family is {@link Family#MAC}.
	 * @param linux   The value if the current OS family is {@link Family#LINUX}.
	 * @param other   The value if the current OS family is {@link Family#OTHER}.
	 * @param <T>     The type of the value.
	 * @return The appropriate value for the current operating system.
	 */
	public static <T> T pick(T windows, T mac, T linux, T other) {
		switch (CURRENT.getFamily()) {
			case WINDOWS:
				return windows;
			case MAC:
				return mac;
			case LINUX:
				return linux;
			case OTHER:
				return other;
			default: // This should never happen
				throw new UnsupportedOSException();
		}
	}

	/**
	 * Picks the correct value for the current operating system family or throws a {@link UnsupportedOSException}.
	 *
	 * @param windows The value if the current OS family is {@link Family#WINDOWS}.
	 * @param mac     The value if the current OS family is {@link Family#MAC}.
	 * @param linux   The value if the current OS family is {@link Family#LINUX}.
	 * @param <T>     The type of the value.
	 * @return The appropriate value for the current operating system.
	 * @throws UnsupportedOSException If the current OS is none of the above mentioned options.
	 */
	public static <T> T pick(T windows, T mac, T linux) {
		switch (CURRENT.getFamily()) {
			case WINDOWS:
				return windows;
			case MAC:
				return mac;
			case LINUX:
				return linux;
			default:
				throw new UnsupportedOSException();
		}
	}

	/**
	 * Picks the correct value for the current operating system family or throws a {@link UnsupportedOSException}.
	 *
	 * @param windows The value if the current OS family is {@link Family#WINDOWS}.
	 * @param mac     The value if the current OS family is {@link Family#MAC}.
	 * @param <T>     The type of the value.
	 * @return The appropriate value for the current operating system.
	 * @throws UnsupportedOSException If the current OS is none of the above mentioned options.
	 */
	public static <T> T pick(T windows, T mac) {
		switch (CURRENT.getFamily()) {
			case WINDOWS:
				return windows;
			case MAC:
				return mac;
			default:
				throw new UnsupportedOSException();
		}
	}

	/**
	 * @return The current operating system.
	 */
	public static OS get() {
		return CURRENT;
	}

	/**
	 * Checks if this OS is part of the given families.
	 *
	 * @param families An array of families this OS's family will be checked against.
	 * @return If this OS is part of the given families.
	 */
	public boolean isFamily(Family... families) {
		return Arrays.asList(families).contains(getFamily());
	}

	/**
	 * Throws an exception if the active operating system family is not supported.
	 *
	 * @param families An array of families that the current OS family has to be part of.
	 * @throws UnsupportedOSException If the {@link #CURRENT} operating system family is not part of the given array.
	 */
	public void enforceFamily(Family... families) {
		if (!isFamily(families))
			throw new UnsupportedOSException(this);
	}

	/**
	 * Checks if this OS is part of the given operating systems.
	 *
	 * @param oss An array of OSs this OS will be checked against.
	 * @return If this OS is part of the given array.
	 */
	public boolean is(OS... oss) {
		return Arrays.asList(oss).contains(this);
	}

	/**
	 * Throws an exception if the active operating system is not supported.
	 *
	 * @param possibleOSs An array of of OSs that the current OS has to be part of.
	 * @throws UnsupportedOSException If the {@link #CURRENT} operating system is not part of the given array.
	 */
	public void enforce(OS... possibleOSs) {
		if (!is(possibleOSs))
			throw new UnsupportedOSException(this);
	}

	/**
	 * @return The family this OS is a part of.
	 */
	public Family getFamily() {
		return family;
	}

	/**
	 * Families of operating systems, like Windows, Mac and Linux.
	 *
	 * @author cegredev
	 */
	enum Family {

		/**
		 * The Windows operating system family.
		 */
		WINDOWS,
		/**
		 * The MacOS operating system family.
		 */
		MAC,
		/**
		 * Any Linux based operating system family.
		 */
		LINUX,
		/**
		 * Any other operating system family.
		 */
		OTHER

	}
}
