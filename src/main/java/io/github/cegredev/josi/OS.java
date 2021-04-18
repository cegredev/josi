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

import java.util.Arrays;
import java.util.Locale;

import static io.github.cegredev.josi.OS.Family.*;

/**
 * Contains a list of various operating system families and their versions, as well as the OS used on the current PC and
 * methods to help deal with different operating systems.
 *
 * @author cegredev
 */
public enum OS {

	// I am aware some of these windows version might be overkill, but they were included in this answer:
	// https://stackoverflow.com/a/31110542/11213660 which is where I got most of the windows code from, so why not?
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
		// TODO: Implement Mac and Linux in more detail

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
	 * @return The current operating system.
	 */
	public static OS current() {
		return CURRENT;
	}

	/**
	 * Picks the correct value for the operating system family.
	 *
	 * @param windows The value if the OS family is {@link Family#WINDOWS}.
	 * @param mac     The value if the OS family is {@link Family#MAC}.
	 * @param linux   The value if the OS family is {@link Family#LINUX}.
	 * @param other   The value if the OS family is {@link Family#OTHER}.
	 * @param <T>     The type of the value.
	 * @return The appropriate value for the operating system.
	 */
	public <T> T pick(T windows, T mac, T linux, T other) {
		switch (getFamily()) {
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
	 * Picks the correct value for the operating system family or throws an {@link UnsupportedOSException}.
	 *
	 * @param windows The value if the OS family is {@link Family#WINDOWS}.
	 * @param mac     The value if the OS family is {@link Family#MAC}.
	 * @param linux   The value if the OS family is {@link Family#LINUX}.
	 * @param <T>     The type of the value.
	 * @return The appropriate value for the operating system.
	 * @throws UnsupportedOSException If the OS is none of the above mentioned options.
	 */
	public <T> T pick(T windows, T mac, T linux) {
		switch (getFamily()) {
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
	 * Picks the correct value for the operating system family or a default one.
	 *
	 * @param windows  The value if the OS family is {@link Family#WINDOWS}.
	 * @param mac      The value if the OS family is {@link Family#MAC}.
	 * @param anyOther The value if the OS is neither Windows or Mac.
	 * @param <T>      The type of the value.
	 * @return The appropriate value for the operating system.
	 */
	public <T> T pickWinMacAny(T windows, T mac, T anyOther) {
		return pick(windows, mac, anyOther, anyOther);
	}

	/**
	 * Picks the correct value for the operating system family between Windows and Mac or throws an {@link
	 * UnsupportedOSException}.
	 *
	 * @param windows The value if the OS family is {@link Family#WINDOWS}.
	 * @param mac     The value if the OS family is {@link Family#MAC}.
	 * @param <T>     The type of the value.
	 * @return The appropriate value for the operating system.
	 * @throws UnsupportedOSException If the OS is none of the above mentioned options.
	 */
	public <T> T pickWinMac(T windows, T mac) {
		switch (getFamily()) {
			case WINDOWS:
				return windows;
			case MAC:
				return mac;
			default:
				throw new UnsupportedOSException();
		}
	}

	/**
	 * Picks the correct value for the operating system family or a default one.
	 *
	 * @param windows  The value if the OS family is {@link Family#WINDOWS}.
	 * @param linux    The value if the OS family is {@link Family#LINUX}.
	 * @param anyOther The value if the OS is neither Windows or Linux.
	 * @param <T>      The type of the value.
	 * @return The appropriate value for the operating system.
	 */
	public <T> T pickWinLinuxAny(T windows, T linux, T anyOther) {
		return pick(windows, anyOther, linux, anyOther);
	}

	/**
	 * Picks the correct value for the operating system family between Windows and Linux or throws an {@link
	 * UnsupportedOSException}.
	 *
	 * @param windows The value if the OS family is {@link Family#WINDOWS}.
	 * @param linux   The value if the OS family is {@link Family#LINUX}.
	 * @param <T>     The type of the value.
	 * @return The appropriate value for the operating system.
	 * @throws UnsupportedOSException If the OS is none of the above mentioned options.
	 */
	public <T> T pickWinLinux(T windows, T linux) {
		switch (getFamily()) {
			case WINDOWS:
				return windows;
			case LINUX:
				return linux;
			default:
				throw new UnsupportedOSException();
		}
	}

	/**
	 * Picks the correct value for the operating system family or a default one.
	 *
	 * @param mac      The value if the OS family is {@link Family#MAC}.
	 * @param linux    The value if the OS family is {@link Family#LINUX}.
	 * @param anyOther The value if the OS is neither Mac or Linux.
	 * @param <T>      The type of the value.
	 * @return The appropriate value for the operating system.
	 */
	public <T> T pickMacLinuxAny(T mac, T linux, T anyOther) {
		return pick(anyOther, mac, linux, anyOther);
	}

	/**
	 * Picks the correct value for the operating system family between Mac and Linux or throws an {@link
	 * UnsupportedOSException}.
	 *
	 * @param mac   The value if the OS family is {@link Family#MAC}.
	 * @param linux The value if the OS family is {@link Family#LINUX}.
	 * @param <T>   The type of the value.
	 * @return The appropriate value for the operating system.
	 * @throws UnsupportedOSException If the OS is none of the above mentioned options.
	 */
	public <T> T pickMacLinux(T mac, T linux) {
		switch (getFamily()) {
			case MAC:
				return mac;
			case LINUX:
				return linux;
			default:
				throw new UnsupportedOSException();
		}
	}

	/**
	 * Picks the correct value for the operating system family or a default one.
	 *
	 * @param windows  The value if the OS family is {@link Family#WINDOWS}.
	 * @param anyOther The value if the OS is anything other than Windows.
	 * @param <T>      The type of the value.
	 * @return The appropriate value for the operating system.
	 */
	public <T> T pickWindowsAny(T windows, T anyOther) {
		return pick(windows, anyOther, anyOther, anyOther);
	}

	/**
	 * Returns the given value if the OS family is Windows or else throws an {@link UnsupportedOSException}.
	 *
	 * @param windows The value if the OS family is {@link Family#WINDOWS}.
	 * @param <T>     The type of the value.
	 * @return The appropriate value for the operating system.
	 * @throws UnsupportedOSException If the OS is not Windows.
	 */
	public <T> T pickWindows(T windows) {
		if (getFamily() == Family.WINDOWS)
			return windows;

		throw new UnsupportedOSException();
	}

	/**
	 * Picks the correct value for the operating system family or a default one.
	 *
	 * @param mac      The value if the OS family is {@link Family#MAC}.
	 * @param anyOther The value if the OS is anything other than Mac.
	 * @param <T>      The type of the value.
	 * @return The appropriate value for the operating system.
	 */
	public <T> T pickMacAny(T mac, T anyOther) {
		return pick(anyOther, mac, anyOther, anyOther);
	}

	/**
	 * Returns the given value if the OS family is Mac or else throws an {@link UnsupportedOSException}.
	 *
	 * @param mac The value if the OS family is {@link Family#MAC}.
	 * @param <T> The type of the value.
	 * @return The appropriate value for the operating system.
	 * @throws UnsupportedOSException If the OS is not Mac.
	 */
	public <T> T pickMac(T mac) {
		if (getFamily() == Family.MAC)
			return mac;

		throw new UnsupportedOSException();
	}

	/**
	 * Picks the correct value for the operating system family or a default one.
	 *
	 * @param linux    The value if the OS family is {@link Family#LINUX}.
	 * @param anyOther The value if the OS is anything other than Linux.
	 * @param <T>      The type of the value.
	 * @return The appropriate value for the operating system.
	 */
	public <T> T pickLinuxAny(T linux, T anyOther) {
		return pick(anyOther, anyOther, linux, anyOther);
	}

	/**
	 * Returns the given value if the OS family is Linux or else throws an {@link UnsupportedOSException}.
	 *
	 * @param linux The value if the OS family is {@link Family#LINUX}.
	 * @param <T>   The type of the value.
	 * @return The appropriate value for the operating system.
	 * @throws UnsupportedOSException If the OS is not Linux.
	 */
	public <T> T pickLinux(T linux) {
		if (getFamily() == LINUX)
			return linux;

		throw new UnsupportedOSException();
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
	 * Throws an exception if this operating system is part of the given families.
	 *
	 * @param families An array of families that this OS is not allowed to be part of.
	 * @throws UnsupportedOSException If the this operating system's family is part of the given array.
	 */
	public void enforceNotFamily(Family... families) {
		if (isFamily(families))
			throw new UnsupportedOSException(this);
	}

	/**
	 * Throws an exception if this operating system is not part of the given families.
	 *
	 * @param families An array of families that this OS has t be part of.
	 * @throws UnsupportedOSException If the this operating system's family is not part of the given array.
	 */
	public void enforceFamily(Family... families) {
		if (!isFamily(families))
			throw new UnsupportedOSException(this);
	}

	/**
	 * Checks if this OS is part of the given operating systems.
	 *
	 * @param operatingSystems An array of OSs this OS will be checked against.
	 * @return If this OS is part of the given array.
	 */
	public boolean is(OS... operatingSystems) {
		return Arrays.asList(operatingSystems).contains(this);
	}

	/**
	 * Throws an exception if this operating system is part of the given array.
	 *
	 * @param operatingSystems An array of operating systems that this OS is not allowed to be part of.
	 * @throws UnsupportedOSException If this operating system is part of the given array.
	 */
	public void enforceNot(OS... operatingSystems) {
		if (is(operatingSystems))
			throw new UnsupportedOSException(this);
	}

	/**
	 * Throws an exception if this operating system is not part of the given array.
	 *
	 * @param operatingSystems An array of operating systems that this OS has to be part of.
	 * @throws UnsupportedOSException If this operating system is not part of the given array.
	 */
	public void enforce(OS... operatingSystems) {
		if (!is(operatingSystems))
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
	public enum Family {

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
