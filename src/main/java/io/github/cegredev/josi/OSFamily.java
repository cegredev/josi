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

import java.util.Arrays;
import java.util.Locale;

/**
 * A list of various operating system families. Contains the current operating system family and utility methods to help
 * deal with different ones.
 *
 * @author cegredev
 */
public enum OSFamily {

	WINDOWS, MAC, LINUX, UNKNOWN;

	/**
	 * The name of this operating system as reported by {@code System.getProperty("os.name")} in lowercase.
	 */
	// Locale.ROOT prevents funny locale stuff from happening
	public static final String NAME_LOWER = System.getProperty("os.name").toLowerCase(Locale.ROOT).trim();

	/**
	 * The operating system family running on the current PC.
	 */
	private static final OSFamily CURRENT = determine(NAME_LOWER);

	/**
	 * Tries to recognize and map a given name and version to the right OS. Is package-private for tests.
	 *
	 * @param name The name to determine the family from. Expects values in the format of {@code
	 *             System.getProperty("os.name")}.
	 * @return A family matching the given name or other if the name can not be recognized.
	 */
	static OSFamily determine(String name) {
		if (name.startsWith("win"))
			return WINDOWS;

		if (name.startsWith("mac"))
			return MAC;

		if (name.contains("nix") || name.contains("nux") || name.contains("aix"))
			return LINUX;

		return UNKNOWN;
	}

	/**
	 * @return The current family.
	 */
	public static OSFamily current() {
		return CURRENT;
	}

	/**
	 * Picks one of the given values based on this family.
	 *
	 * @param windows The value if this family is {@link #WINDOWS}.
	 * @param mac     The value if this family is {@link #MAC}.
	 * @param linux   The value if this family is {@link #LINUX}.
	 * @param other   The value if this family is {@link #UNKNOWN}.
	 * @param <T>     The type of the value.
	 * @return The value representing this family.
	 */
	public <T> T pick(T windows, T mac, T linux, T other) {
		switch (this) {
			case WINDOWS:
				return windows;
			case MAC:
				return mac;
			case LINUX:
				return linux;
			default:
				return other;
		}
	}

	/**
	 * Picks one of the given values based on this family or throws an {@link UnsupportedOSFamilyException} if there is
	 * no value to represent it.
	 *
	 * @param windows The value if this family is {@link #WINDOWS}.
	 * @param mac     The value if this family is {@link #MAC}.
	 * @param linux   The value if this family is {@link #LINUX}.
	 * @param <T>     The type of the value.
	 * @return The value representing this family.
	 * @throws UnsupportedOSFamilyException If this family is none of the above mentioned options.
	 */
	public <T> T pick(T windows, T mac, T linux) {
		switch (this) {
			case WINDOWS:
				return windows;
			case MAC:
				return mac;
			case LINUX:
				return linux;
			default:
				throw new UnsupportedOSFamilyException(this);
		}
	}

	/**
	 * Picks one of the given values based on the this family or a default one.
	 *
	 * @param windows  The value if this family is {@link #WINDOWS}.
	 * @param mac      The value if this family is {@link #MAC}.
	 * @param anyOther The value if this family is neither Windows or Mac.
	 * @param <T>      The type of the value.
	 * @return The value representing this family.
	 */
	public <T> T pickWinMacAny(T windows, T mac, T anyOther) {
		return pick(windows, mac, anyOther, anyOther);
	}

	/**
	 * Picks one of the given values based on this family or throws an {@link UnsupportedOSFamilyException} if there is
	 * no value to represent it.
	 *
	 * @param windows The value if this family is {@link #WINDOWS}.
	 * @param mac     The value if this family is {@link #MAC}.
	 * @param <T>     The type of the value.
	 * @return The value representing this family.
	 * @throws UnsupportedOSFamilyException If this family is none of the above mentioned options.
	 */
	public <T> T pickWinMac(T windows, T mac) {
		switch (this) {
			case WINDOWS:
				return windows;
			case MAC:
				return mac;
			default:
				throw new UnsupportedOSFamilyException(this);
		}
	}

	/**
	 * Picks one of the given values based on this family or a default one.
	 *
	 * @param windows  The value if this family is {@link #WINDOWS}.
	 * @param linux    The value if this family is {@link #LINUX}.
	 * @param anyOther The value if this family is neither Windows or Linux.
	 * @param <T>      The type of the value.
	 * @return The value representing this family.
	 */
	public <T> T pickWinLinuxAny(T windows, T linux, T anyOther) {
		return pick(windows, anyOther, linux, anyOther);
	}

	/**
	 * Picks one of the given values based on the family or throws an {@link UnsupportedOSFamilyException} if there is
	 * no value to represent it.
	 *
	 * @param windows The value if this family is {@link #WINDOWS}.
	 * @param linux   The value if this family is {@link #LINUX}.
	 * @param <T>     The type of the value.
	 * @return The value representing this family.
	 * @throws UnsupportedOSFamilyException If this family is none of the above mentioned options.
	 */
	public <T> T pickWinLinux(T windows, T linux) {
		switch (this) {
			case WINDOWS:
				return windows;
			case LINUX:
				return linux;
			default:
				throw new UnsupportedOSFamilyException(this);
		}
	}

	/**
	 * Picks one of the given values based on this family or a default one.
	 *
	 * @param mac      The value if this family is {@link #MAC}.
	 * @param linux    The value if this family is {@link #LINUX}.
	 * @param anyOther The value if this family is neither Mac or Linux.
	 * @param <T>      The type of the value.
	 * @return The value representing this family.
	 */
	public <T> T pickMacLinuxAny(T mac, T linux, T anyOther) {
		return pick(anyOther, mac, linux, anyOther);
	}

	/**
	 * Picks one of the given values based on this family or throws an {@link UnsupportedOSFamilyException} if there is
	 * no value to represent it.
	 *
	 * @param mac   The value if this family is {@link #MAC}.
	 * @param linux The value if this family is {@link #LINUX}.
	 * @param <T>   The type of the value.
	 * @return The value representing this family.
	 * @throws UnsupportedOSFamilyException If this family is none of the above mentioned options.
	 */
	public <T> T pickMacLinux(T mac, T linux) {
		switch (this) {
			case MAC:
				return mac;
			case LINUX:
				return linux;
			default:
				throw new UnsupportedOSFamilyException(this);
		}
	}

	/**
	 * Returns the given value if this family is Windows, or a default one.
	 *
	 * @param windows  The value if this family is {@link #WINDOWS}.
	 * @param anyOther The value if this family is anything other than Windows.
	 * @param <T>      The type of the value.
	 * @return The value representing this family.
	 */
	public <T> T pickWindowsAny(T windows, T anyOther) {
		return pick(windows, anyOther, anyOther, anyOther);
	}

	/**
	 * Returns the given value if this family is Windows or else throws an {@link UnsupportedOSFamilyException}.
	 *
	 * @param windows The value if this family is {@link #WINDOWS}.
	 * @param <T>     The type of the value.
	 * @return The value representing this family.
	 * @throws UnsupportedOSFamilyException If this family is not Windows.
	 */
	public <T> T pickWindows(T windows) {
		if (this == WINDOWS)
			return windows;

		throw new UnsupportedOSFamilyException(this);
	}

	/**
	 * Returns the given value if this family is Mac, or a default one.
	 *
	 * @param mac      The value if this family is {@link #MAC}.
	 * @param anyOther The value if this family is anything other than Mac.
	 * @param <T>      The type of the value.
	 * @return The value representing this family.
	 */
	public <T> T pickMacAny(T mac, T anyOther) {
		return pick(anyOther, mac, anyOther, anyOther);
	}

	/**
	 * Returns the given value if this family is Mac or else throws an {@link UnsupportedOSFamilyException}.
	 *
	 * @param mac The value if this family is {@link #MAC}.
	 * @param <T> The type of the value.
	 * @return The value representing this family.
	 * @throws UnsupportedOSFamilyException If this family is not Mac.
	 */
	public <T> T pickMac(T mac) {
		if (this == MAC)
			return mac;

		throw new UnsupportedOSFamilyException(this);
	}

	/**
	 * Returns the given value if this family is Linux, or a default one.
	 *
	 * @param linux    The value if this family is {@link #LINUX}.
	 * @param anyOther The value if this family is anything other than Linux.
	 * @param <T>      The type of the value.
	 * @return The value representing this family.
	 */
	public <T> T pickLinuxAny(T linux, T anyOther) {
		return pick(anyOther, anyOther, linux, anyOther);
	}

	/**
	 * Returns the given value if this family is Linux or else throws an {@link UnsupportedOSFamilyException}.
	 *
	 * @param linux The value if this family is {@link #LINUX}.
	 * @param <T>   The type of the value.
	 * @return The value representing this family.
	 * @throws UnsupportedOSFamilyException If this family is not Linux.
	 */
	public <T> T pickLinux(T linux) {
		if (this == LINUX)
			return linux;

		throw new UnsupportedOSFamilyException(this);
	}

	/**
	 * Checks if this OS is part of the given array.
	 *
	 * @param operatingSystems An array of families this OS has to be part of.
	 * @return If this OS is part of the given array.
	 */
	public boolean is(OSFamily... operatingSystems) {
		return Arrays.asList(operatingSystems).contains(this);
	}

	/**
	 * Throws an {@link UnsupportedOSFamilyException} if this family is part of the given array.
	 *
	 * @param operatingSystems An array of families that this one is not allowed to be part of.
	 * @throws UnsupportedOSFamilyException If this family is part of the given array.
	 */
	public void enforceNot(OSFamily... operatingSystems) {
		if (is(operatingSystems))
			throw new UnsupportedOSFamilyException(this);
	}

	/**
	 * Throws an {@link UnsupportedOSFamilyException} if this family is not part of the given array.
	 *
	 * @param operatingSystems An array of families that this one has to be part of.
	 * @throws UnsupportedOSFamilyException If this family is not part of the given array.
	 */
	public void enforce(OSFamily... operatingSystems) {
		if (!is(operatingSystems))
			throw new UnsupportedOSFamilyException(this);
	}

}
