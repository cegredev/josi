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

import static io.github.cegredev.jos.OS.Family.*;

/**
 * Contains a list of various operating system families and their versions, as well as the OS used on the current PC and
 * methods to help deal with different operating systems.
 *
 * @author cegredev
 */
public enum OS {

	WIN7(WINDOWS), WIN8(WINDOWS), WIN8_1(WINDOWS), WIN10(WINDOWS), MAC(Family.MAC), UBUNTU(LINUX), RED_HAT(LINUX);

	/**
	 * The current operating system on the running pc.
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
		// TODO: Implement
		return WIN10;
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

		WINDOWS, MAC, LINUX, OTHER

	}
}
