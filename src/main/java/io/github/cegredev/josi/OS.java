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
import java.util.function.Supplier;

import static io.github.cegredev.josi.OS.Family.*;

/**
 * A list of various operating systems, each belonging to a {@link Family}. Contains the current operating system and
 * utility methods to help deal with different operating systems.
 *
 * @author cegredev
 */
public enum OS {

	// I am aware some of these windows version might be overkill, but they were included in this answer:
	// https://stackoverflow.com/a/31110542/11213660 which is where I got most of the windows code from, so why not?
	WIN_95(WINDOWS), WIN_98(WINDOWS), WIN_XP(WINDOWS), WIN_VISTA(WINDOWS), WIN_7(WINDOWS), WIN_8(WINDOWS),
	WIN_8_1(WINDOWS), WIN_10(WINDOWS),
	/**
	 * An unknown or at least unrecognizable Windows based operating system.
	 */
	WIN_UNKNOWN(WINDOWS),

	// Specific versions of macOS. Originally named MAC OS X, it was renamed to macOS starting
	// with Sierra.
	MAC_OSX_CHEETAH(MAC), MAC_OSX_PUMA(MAC), MAC_OSX_JAGUAR(MAC),
	MAC_OSX_PANTHER(MAC), MAC_OSX_TIGER(MAC), MAC_OSX_LEOPARD(MAC),
	MAC_OSX_SNOW_LEOPARD(MAC), MAC_OSX_LION(MAC), MAC_OSX_MOUNTAIN_LION(MAC),
	MAC_OSX_MAVERICKS(MAC), MAC_OSX_YOSEMITE(MAC), MAC_OSX_EL_CAPITAN(MAC),
	MAC_OS_SIERRA(MAC), MAC_OS_HIGH_SIERRA(MAC), MAC_OS_MOJAVE(MAC),
	MAC_OS_CATALINA(MAC), MAC_OS_BIG_SUR(MAC),
	/**
	 * Any Mac based operating system.
	 */
	MAC_UNKNOWN(MAC),
	/**
	 * Any Linux based operating system.
	 */
	LINUX(Family.LINUX),
	/**
	 * An operating system that cannot be classified.
	 */
	OTHER(Family.OTHER);

	/**
	 * The operating system running on the current PC.
	 */
	private static final OS CURRENT = determine(System.getProperty("os.name"), System.getProperty("os.version"));

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
	 * Tries to recognize and map an operating system to the given name. Is package-private for tests.
	 *
	 * @param name The name to determine the operating system from. Expects values in the format of {@code
	 *             System.getProperty("os.name")}.
	 * @param version The version to determine the operating system from. Expects values in the format of {@code
	 * 	              System.getProperty("os.version")}.
	 * @return An operating system matching the given name or other if the name can not be recognized.
	 */
	static OS determine(String name, String version) {
		// TODO: Implement Linux in more detail

		// Locale.ROOT prevents funny locale stuff from happening
		name = name.toLowerCase(Locale.ROOT).trim();

		// Decide Windows version
		if (name.startsWith("win")) {
			int lastSpace = name.lastIndexOf(' ');

			if (lastSpace > -1) {
				switch (name.substring(lastSpace + 1)) {
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

			return WIN_UNKNOWN;
		}

		// Decide Mac version
		if (name.startsWith("mac")) {
			String[] versionSplit = version.split("\\.");

			if (versionSplit.length < 2) {
				// If we only have the major version, we can't decide
				return MAC_UNKNOWN;
			}

			String majorMinor = versionSplit[0] + "." + versionSplit[1];

			switch (majorMinor) {
				case "10.0":
					return MAC_OSX_CHEETAH;
				case "10.1":
					return MAC_OSX_PUMA;
				case "10.2":
					return MAC_OSX_JAGUAR;
				case "10.3":
					return MAC_OSX_PANTHER;
				case "10.4":
					return MAC_OSX_TIGER;
				case "10.5":
					return MAC_OSX_LEOPARD;
				case "10.6":
					return MAC_OSX_SNOW_LEOPARD;
				case "10.7":
					return MAC_OSX_LION;
				case "10.8":
					return MAC_OSX_MOUNTAIN_LION;
				case "10.9":
					return MAC_OSX_MAVERICKS;
				case "10.10":
					return MAC_OSX_YOSEMITE;
				case "10.11":
					return MAC_OSX_EL_CAPITAN;
				case "10.12":
					return MAC_OS_SIERRA;
				case "10.13":
					return MAC_OS_HIGH_SIERRA;
				case "10.14":
					return MAC_OS_MOJAVE;
				case "10.15":
					return MAC_OS_CATALINA;
				case "10.16":
					// Even though Big Sur is macOS version 11.x,
					// the os.version system property returns 10.16
					return MAC_OS_BIG_SUR;
				default:
					break;
			}

			return MAC_UNKNOWN;
		}

		// Decide Linux version
		if (name.contains("nix") || name.contains("nux") || name.contains("aix")) {
			return LINUX;
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
	 * Picks one of the given values based on the {@link Family} of this operating system.
	 *
	 * @param windows The value if the OS family is {@link Family#WINDOWS}.
	 * @param mac     The value if the OS family is {@link Family#MAC}.
	 * @param linux   The value if the OS family is {@link Family#LINUX}.
	 * @param other   The value if the OS family is {@link Family#OTHER}.
	 * @param <T>     The type of the value.
	 * @return The value representing this operating system.
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
	 * Picks one of the given values based on the {@link Family} of this operating system or throws an {@link
	 * UnsupportedOSException} if there is no value to represent it.
	 *
	 * @param windows The value if the OS family is {@link Family#WINDOWS}.
	 * @param mac     The value if the OS family is {@link Family#MAC}.
	 * @param linux   The value if the OS family is {@link Family#LINUX}.
	 * @param <T>     The type of the value.
	 * @return The value representing this operating system.
	 * @throws UnsupportedOSException If this operating system is none of the above mentioned options.
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
	 * Picks one of the given values based on the {@link Family} of this operating system or a default one.
	 *
	 * @param windows  The value if the OS family is {@link Family#WINDOWS}.
	 * @param mac      The value if the OS family is {@link Family#MAC}.
	 * @param anyOther The value if the OS is neither Windows or Mac.
	 * @param <T>      The type of the value.
	 * @return The value representing this operating system.
	 */
	public <T> T pickWinMacAny(T windows, T mac, T anyOther) {
		return pick(windows, mac, anyOther, anyOther);
	}

	/**
	 * Picks one of the given values based on the {@link Family} of this operating system or throws an {@link
	 * UnsupportedOSException} if there is no value to represent it.
	 *
	 * @param windows The value if the OS family is {@link Family#WINDOWS}.
	 * @param mac     The value if the OS family is {@link Family#MAC}.
	 * @param <T>     The type of the value.
	 * @return The value representing this operating system.
	 * @throws UnsupportedOSException If this operating system is none of the above mentioned options.
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
	 * Picks one of the given values based on the {@link Family} of this operating system or a default one.
	 *
	 * @param windows  The value if the OS family is {@link Family#WINDOWS}.
	 * @param linux    The value if the OS family is {@link Family#LINUX}.
	 * @param anyOther The value if the OS is neither Windows or Linux.
	 * @param <T>      The type of the value.
	 * @return The value representing this operating system.
	 */
	public <T> T pickWinLinuxAny(T windows, T linux, T anyOther) {
		return pick(windows, anyOther, linux, anyOther);
	}

	/**
	 * Picks one of the given values based on the {@link Family} of this operating system or throws an {@link
	 * UnsupportedOSException} if there is no value to represent it.
	 *
	 * @param windows The value if the OS family is {@link Family#WINDOWS}.
	 * @param linux   The value if the OS family is {@link Family#LINUX}.
	 * @param <T>     The type of the value.
	 * @return The value representing this operating system.
	 * @throws UnsupportedOSException If this operating system is none of the above mentioned options.
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
	 * Picks one of the given values based on the {@link Family} of this operating system or a default one.
	 *
	 * @param mac      The value if the OS family is {@link Family#MAC}.
	 * @param linux    The value if the OS family is {@link Family#LINUX}.
	 * @param anyOther The value if the OS is neither Mac or Linux.
	 * @param <T>      The type of the value.
	 * @return The value representing this operating system.
	 */
	public <T> T pickMacLinuxAny(T mac, T linux, T anyOther) {
		return pick(anyOther, mac, linux, anyOther);
	}

	/**
	 * Picks one of the given values based on the {@link Family} of this operating system or throws an {@link
	 * UnsupportedOSException} if there is no value to represent it.
	 *
	 * @param mac   The value if the OS family is {@link Family#MAC}.
	 * @param linux The value if the OS family is {@link Family#LINUX}.
	 * @param <T>   The type of the value.
	 * @return The value representing this operating system.
	 * @throws UnsupportedOSException If this operating system is none of the above mentioned options.
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
	 * Returns the given value if the {@link Family} of this operating system is Windows, or a default one.
	 *
	 * @param windows  The value if the OS family is {@link Family#WINDOWS}.
	 * @param anyOther The value if the OS is anything other than Windows.
	 * @param <T>      The type of the value.
	 * @return The value representing this operating system.
	 */
	public <T> T pickWindowsAny(T windows, T anyOther) {
		return pick(windows, anyOther, anyOther, anyOther);
	}

	/**
	 * Returns the given value if the {@link Family} of this OS is Windows or else throws an {@link
	 * UnsupportedOSException}.
	 *
	 * @param windows The value if the OS family is {@link Family#WINDOWS}.
	 * @param <T>     The type of the value.
	 * @return The value representing this operating system.
	 * @throws UnsupportedOSException If this operating system is not Windows.
	 */
	public <T> T pickWindows(T windows) {
		if (getFamily() == Family.WINDOWS)
			return windows;

		throw new UnsupportedOSException();
	}

	/**
	 * Returns the given value if the {@link Family} of this operating system is Mac, or a default one.
	 *
	 * @param mac      The value if the OS family is {@link Family#MAC}.
	 * @param anyOther The value if the OS is anything other than Mac.
	 * @param <T>      The type of the value.
	 * @return The value representing this operating system.
	 */
	public <T> T pickMacAny(T mac, T anyOther) {
		return pick(anyOther, mac, anyOther, anyOther);
	}

	/**
	 * Returns the given value if the {@link Family} of this OS is Mac or else throws an {@link
	 * UnsupportedOSException}.
	 *
	 * @param mac The value if the OS family is {@link Family#MAC}.
	 * @param <T> The type of the value.
	 * @return The value representing this operating system.
	 * @throws UnsupportedOSException If this operating system is not Mac.
	 */
	public <T> T pickMac(T mac) {
		if (getFamily() == Family.MAC)
			return mac;

		throw new UnsupportedOSException();
	}

	/**
	 * Returns the given value if the {@link Family} of this operating system is Linux, or a default one.
	 *
	 * @param linux    The value if the OS family is {@link Family#LINUX}.
	 * @param anyOther The value if the OS is anything other than Linux.
	 * @param <T>      The type of the value.
	 * @return The value representing this operating system.
	 */
	public <T> T pickLinuxAny(T linux, T anyOther) {
		return pick(anyOther, anyOther, linux, anyOther);
	}

	/**
	 * Returns the given value if the {@link Family} of this OS is Linux or else throws an {@link
	 * UnsupportedOSException}.
	 *
	 * @param linux The value if the OS family is {@link Family#LINUX}.
	 * @param <T>   The type of the value.
	 * @return The value representing this operating system.
	 * @throws UnsupportedOSException If this operating system is not Linux.
	 */
	public <T> T pickLinux(T linux) {
		if (getFamily() == Family.LINUX)
			return linux;

		throw new UnsupportedOSException();
	}

	/**
	 * Checks if this OS is part of the given families.
	 *
	 * @param families An array of families this OS's family has to be part of.
	 * @return If this OS's family is part of the given families.
	 */
	public boolean isFamily(Family... families) {
		return Arrays.asList(families).contains(getFamily());
	}

	/**
	 * Throws an {@link UnsupportedOSException} if this OS's family is part of the given families.
	 *
	 * @param families An array of families that this OS's family is not allowed to be part of.
	 * @throws UnsupportedOSException If the this operating system's family is part of the given array.
	 */
	public void enforceNotFamily(Family... families) {
		if (isFamily(families))
			throw new UnsupportedOSException(this);
	}

	/**
	 * Throws an {@link UnsupportedOSException} if this OS is not part of the given families.
	 *
	 * @param families An array of families that this OS's family has to be part of.
	 * @throws UnsupportedOSException If this operating system's family is not part of the given array.
	 */
	public void enforceFamily(Family... families) {
		if (!isFamily(families))
			throw new UnsupportedOSException(this);
	}

	/**
	 * Checks if this OS is part of the given array.
	 *
	 * @param operatingSystems An array of operating systems this OS has to be part of.
	 * @return If this OS is part of the given array.
	 */
	public boolean is(OS... operatingSystems) {
		return Arrays.asList(operatingSystems).contains(this);
	}

	/**
	 * Throws an {@link UnsupportedOSException} if this operating system is part of the given array.
	 *
	 * @param operatingSystems An array of operating systems that this OS is not allowed to be part of.
	 * @throws UnsupportedOSException If this operating system is part of the given array.
	 */
	public void enforceNot(OS... operatingSystems) {
		if (is(operatingSystems))
			throw new UnsupportedOSException(this);
	}

	/**
	 * Throws an {@link UnsupportedOSException} if this operating system is not part of the given array.
	 *
	 * @param operatingSystems An array of operating systems that this OS has to be part of.
	 * @throws UnsupportedOSException If this operating system is not part of the given array.
	 */
	public void enforce(OS... operatingSystems) {
		if (!is(operatingSystems))
			throw new UnsupportedOSException(this);
	}

	/**
	 * @return The family this OS belongs to.
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
		WINDOWS(() -> WIN_10),
		/**
		 * The MacOS operating system family.
		 */
		MAC(() -> OS.MAC_OS_BIG_SUR),
		/**
		 * Any Linux based operating system family.
		 */
		LINUX(() -> OS.LINUX),
		/**
		 * Any other operating system family.
		 */
		OTHER(() -> OS.OTHER);

		/**
		 * Supplies an OS representing the family. This has to be a supplier, because if you just passed in the OS to
		 * the constructor you would create a circular definition, because each OS also references a family at
		 * construction, meaning that the value would always be null, because this constructor would be called before
		 * the actual OS value was set.
		 */
		private final Supplier<OS> representative;

		Family(Supplier<OS> representative) {
			this.representative = representative;
		}

		/**
		 * @return An OS representing this family.
		 */
		public OS getRepresentative() {
			return representative.get();
		}
	}
}
