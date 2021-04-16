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
 */
public enum OS {

	WIN7(WINDOWS), WIN8(WINDOWS), WIN8_1(WINDOWS), WIN10(WINDOWS), MAC(Family.MAC), UBUNTU(LINUX), RED_HAT(LINUX);

	private static final OS ACTIVE = determine(System.getProperty("os.name"));

	private final Family family;

	OS(Family family) {
		this.family = family;
	}

	static OS determine(String osName) {
		// TODO: Implement
		return WIN10;
	}

	public static <T> T pick(T windows, T mac, T linux, T other) {
		switch (ACTIVE.getFamily()) {
			case WINDOWS:
				return windows;
			case MAC:
				return mac;
			case LINUX:
				return linux;
			case OTHER:
				return other;
			default:
				throw new UnsupportedOSException(ACTIVE);
		}
	}

	public static <T> T pick(T windows, T mac, T linux) {
		switch (ACTIVE.getFamily()) {
			case WINDOWS:
				return windows;
			case MAC:
				return mac;
			case LINUX:
				return linux;
			default:
				throw new UnsupportedOSException(ACTIVE);
		}
	}

	public static <T> T pick(T windows, T mac) {
		switch (ACTIVE.getFamily()) {
			case WINDOWS:
				return windows;
			case MAC:
				return mac;
			default:
				throw new UnsupportedOSException(ACTIVE);
		}
	}

	public static OS get() {
		return ACTIVE;
	}

	public boolean isFamily(Family... families) {
		return Arrays.asList(families).contains(getFamily());
	}

	/**
	 * Throws an exception if the active operating system family is not supported.
	 *
	 * @param families An array of families that the current OS family has to be part of.
	 * @throws UnsupportedOSException If the {@link #ACTIVE} operating system family is not part of the given array.
	 */
	public void enforceFamily(Family... families) {
		if (!isFamily(families))
			throw new UnsupportedOSException(this);
	}

	public boolean is(OS... oss) {
		return Arrays.asList(oss).contains(this);
	}

	/**
	 * Throws an exception if the active operating system is not supported.
	 *
	 * @param possibleOSs An array of of OSs that the current OS has to be part of.
	 * @throws UnsupportedOSException If the {@link #ACTIVE} operating system is not part of the given array.
	 */
	public void enforce(OS... possibleOSs) {
		if (!is(possibleOSs))
			throw new UnsupportedOSException(this);
	}

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
