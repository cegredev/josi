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

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
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

	// I'm aware some of these windows version might be overkill, but they were included in this answer:
	// https://stackoverflow.com/a/31110542/11213660 which is where I got most of the windows code from, so why not?
	// TODO: Add these: WIN_ME(WINDOWS), WIN_2000(WINDOWS)
	WIN_95(WINDOWS), WIN_98(WINDOWS), WIN_XP(WINDOWS), WIN_VISTA(WINDOWS),
	WIN_7(WINDOWS), WIN_8(WINDOWS), WIN_8_1(WINDOWS), WIN_10(WINDOWS),
	/**
	 * An unknown or at least unrecognizable Windows based operating system.
	 */
	WIN_UNKNOWN(WINDOWS),

	// Specific versions of macOS. Originally named MAC OS X, it was renamed to macOS starting
	// with Sierra. The operating systems represented here were mostly chosen based on this list:
	// https://en.wikipedia.org/wiki/MacOS_version_history#Releases
	MAC_OSX_CHEETAH(MAC), MAC_OSX_PUMA(MAC), MAC_OSX_JAGUAR(MAC), MAC_OSX_PANTHER(MAC),
	MAC_OSX_TIGER(MAC), MAC_OSX_LEOPARD(MAC), MAC_OSX_SNOW_LEOPARD(MAC), MAC_OSX_LION(MAC),
	MAC_OSX_MOUNTAIN_LION(MAC), MAC_OSX_MAVERICKS(MAC), MAC_OSX_YOSEMITE(MAC), MAC_OSX_EL_CAPITAN(MAC),
	MAC_OS_SIERRA(MAC), MAC_OS_HIGH_SIERRA(MAC), MAC_OS_MOJAVE(MAC), MAC_OS_CATALINA(MAC), MAC_OS_BIG_SUR(MAC),
	/**
	 * An unknown or at least unrecognizable Mac based operating system.
	 */
	MAC_UNKNOWN(MAC),

	// This selection of Linux based operating system was made mostly based on this article:
	// https://www.tecmint.com/linux-distro-for-power-users/ As with any of the other families,
	// if you feel an important one is missing, feel free to add it yourself!
	DEBIAN(LINUX), UBUNTU(LINUX), GENTOO(LINUX), LINUX_MINT(LINUX), RED_HAT_ENTERPRISE_LINUX(LINUX),
	CENTOS(LINUX), FEDORA(LINUX), ARCH_LINUX(LINUX),
	/**
	 * Suse/OpenSUSE and any child-distributions.
	 */
	SUSE(LINUX),
	/**
	 * An unknown or at least unrecognizable Linux based operating system.
	 */
	LINUX_UNKNOWN(LINUX),
	/**
	 * The Solaris operating system.
	 */
	SOLARIS(OTHER),
	/**
	 * An operating system that cannot be classified.
	 */
	UNKNOWN(OTHER);

	/**
	 * The operating system running on the current PC.
	 */
	private static final OS CURRENT = determine(System.getProperty("os.name"), System.getProperty("os.version"),
			new File("/etc/os-release"));

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
	 * Tries to recognize and map a given name and version to the right OS. Is package-private for tests.
	 *
	 * @param name      The name to determine the operating system from. Expects values in the format of {@code
	 *                  System.getProperty("os.name")}.
	 * @param version   The version to determine the operating system from. Expects values in the format of {@code
	 *                  System.getProperty("os.version")}.
	 * @param osRelease Usually contains OS information on Linux based systems, although it doesn't have to. Only set
	 *                  manually for tests.
	 * @return An operating system matching the given name or other if the name can not be recognized.
	 */
	static OS determine(String name, String version, File osRelease) {
		// Locale.ROOT prevents funny locale stuff from happening
		name = name.toLowerCase(Locale.ROOT).trim();

		// Decide Windows version
		if (name.startsWith("win")) {
			// TODO: Use version here instead of name
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

			// Get the major and minor version. If we can't read it, setting it to unknown
			// will allow it to fall through the switch and return MAC_UNKNOWN
			String major = versionSplit.length > 0 ? versionSplit[0] : "Unknown";
			String minor = versionSplit.length > 1 ? versionSplit[1] : "Unknown";

			switch (major) {
				case "10":
					switch (minor) {
						case "0":
							return MAC_OSX_CHEETAH;
						case "1":
							return MAC_OSX_PUMA;
						case "2":
							return MAC_OSX_JAGUAR;
						case "3":
							return MAC_OSX_PANTHER;
						case "4":
							return MAC_OSX_TIGER;
						case "5":
							return MAC_OSX_LEOPARD;
						case "6":
							return MAC_OSX_SNOW_LEOPARD;
						case "7":
							return MAC_OSX_LION;
						case "8":
							return MAC_OSX_MOUNTAIN_LION;
						case "9":
							return MAC_OSX_MAVERICKS;
						case "10":
							return MAC_OSX_YOSEMITE;
						case "11":
							return MAC_OSX_EL_CAPITAN;
						case "12":
							return MAC_OS_SIERRA;
						case "13":
							return MAC_OS_HIGH_SIERRA;
						case "14":
							return MAC_OS_MOJAVE;
						case "15":
							return MAC_OS_CATALINA;
						case "16":
							// Big Sur is macOS version 11.x, but sometimes 10.16 is returned
							return MAC_OS_BIG_SUR;
						default:
							return MAC_UNKNOWN;
					}
				case "11":
					return MAC_OS_BIG_SUR;
				default:
					return MAC_UNKNOWN;
			}
		}

		// Decide Linux version
		if (name.contains("nix") || name.contains("nux") || name.contains("aix")) {
			// If the file does not exist there is nothing more we can achieve
			if (!osRelease.exists())
				return LINUX_UNKNOWN;

			HashMap<String, String> osReleaseMap = new HashMap<>();

			try (BufferedReader reader = new BufferedReader(new FileReader(osRelease))) {
				String line;
				while ((line = reader.readLine()) != null) {
					int split = line.indexOf('=');

					// Broken etc/os-release file, but other lines might still be correct
					if (split <= -1)
						continue;

					String key = line.substring(0, split), value = line.substring(split + 1);
					// Some distros put the value in quotation marks, some don't.
					// To achieve the consistency needed for a switch statement, we remove them here.
					if (value.startsWith("\""))
						value = value.substring(1);
					if (value.endsWith("\""))
						value = value.substring(0, value.length() - 1);

					osReleaseMap.put(key, value);
				}
			} catch (IOException e) {
				System.err.println("Something went wrong while loading /etc/os-release!");
				e.printStackTrace();
				return LINUX_UNKNOWN;
			}

			// ID is the computer friendly name of the current Linux distribution
			String id = osReleaseMap.get("ID");
			// ID_LIKE is a list of space-separated IDs of parent distributions
			String idLike = osReleaseMap.get("ID_LIKE");

			OS distro = LINUX_UNKNOWN;
			if (id != null)
				distro = determineLinuxDistro(id);

			if (distro == LINUX_UNKNOWN && idLike != null)
				for (String parentID : idLike.split(" "))
					if ((distro = determineLinuxDistro(parentID)) != LINUX_UNKNOWN)
						return distro;

			return distro;
		}

		// Others

		if (name.contains("sunos"))
			return SOLARIS;

		return UNKNOWN;
	}

	/**
	 * Tries to detect a Linux distribution based on the given ID.
	 *
	 * @param id The ID or any ID_LIKE of the current Linux distribution.
	 * @return An OS matching the given ID or {@link #LINUX_UNKNOWN} if it cannot be identified.
	 */
	private static OS determineLinuxDistro(String id) {
		switch (id) {
			// All the OSs up to (inclusive) SUSE can be found in this GitHub repo or its forks:
			// https://gist.github.com/natefoo/814c5bf936922dad97ff
			// Additional links for validation were also added to some

			// https://blog.thewatertower.org/2020/01/07/stash-of-etc-os-release-files/
			case "debian":
				return DEBIAN;
			case "ubuntu":
				return UBUNTU;
			case "centos":
				return CENTOS;
			case "fedora":
				return FEDORA;
			case "arch":
				return ARCH_LINUX;
			// https://gitweb.gentoo.org/proj/baselayout.git/tree/etc.Linux/os-release
			// https://gist.github.com/Wuodan/52d9761a77331ca3b8d044a50b910f52
			case "gentoo":
				return GENTOO;
			// https://en.opensuse.org/SDB:SUSE_and_openSUSE_Products_Version_Outputs
			case "suse":
			case "opensuse":
				return SUSE;

			// https://itsfoss.com/check-linux-mint-version/
			case "linuxmint":
				return LINUX_MINT;
			// https://www.cyberciti.biz/faq/what-version-of-redhat-linux-am-i-running/
			case "rhel":
				return RED_HAT_ENTERPRISE_LINUX;
			default:
				return LINUX_UNKNOWN;
		}
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
			default:
				return other;
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
				throw new UnsupportedOSException(this);
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
				throw new UnsupportedOSException(this);
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
				throw new UnsupportedOSException(this);
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
				throw new UnsupportedOSException(this);
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

		throw new UnsupportedOSException(this);
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

		throw new UnsupportedOSException(this);
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

		throw new UnsupportedOSException(this);
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
		LINUX(() -> OS.UBUNTU),
		/**
		 * Any other operating system family.
		 */
		OTHER(() -> OS.UNKNOWN);

		/**
		 * Supplies an OS representing the family. You can't just pass the enum value itself because it would still be
		 * {@code null} at the time these families are constructed, since the OS constructors themselves reference
		 * them.
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
