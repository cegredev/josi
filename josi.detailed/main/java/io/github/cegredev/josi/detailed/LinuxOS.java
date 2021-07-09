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
package io.github.cegredev.josi.detailed;

import io.github.cegredev.josi.min.OSFamily;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Represents a Linux based operating system.
 *
 * @author cegredev
 */
public class LinuxOS extends OperatingSystem {

	/**
	 * As loaded from "/etc/os-release".
	 */
	private final Map<String, String> osReleaseMap;

	/**
	 * The exact or next-best Linux distribution.
	 */
	private final Distribution distro;

	public LinuxOS(Map<String, String> osRelease, Distribution distro) {
		this.osReleaseMap = osRelease;
		this.distro = distro;
	}

	public LinuxOS(Distribution distro) {
		this(Map.of(), distro);
	}

	public LinuxOS(File osRelease) {
		// If the file does not exist there is nothing more we can achieve.
		if (osRelease.exists()) {
			Map<String, String> osReleaseMap = new HashMap<>();

			try (BufferedReader reader = new BufferedReader(new FileReader(osRelease))) {
				String line;
				while ((line = reader.readLine()) != null) {
					int split = line.indexOf('=');

					// Broken os-release file, but other lines might still be correct
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
				System.err.println("Something went wrong while loading \"" + osRelease.getAbsolutePath() + "\"!");
				e.printStackTrace();
			}

			this.osReleaseMap = osReleaseMap;

			// ID is the computer friendly name of the current Linux distribution
			String id = osReleaseMap.get("ID");
			// ID_LIKE is a list of space-separated IDs of parent distributions
			String idLike = osReleaseMap.get("ID_LIKE");

			Distribution distro = Distribution.UNKNOWN;
			if (id != null)
				distro = Distribution.fromID(id);

			if (distro == Distribution.UNKNOWN && idLike != null)
				for (String parentID : idLike.split(" "))
					if ((distro = Distribution.fromID(parentID)) != Distribution.UNKNOWN)
						break;

			this.distro = distro;
		} else {
			System.err.println("\"/etc/os-release\" file does not exist at \"" + osRelease.getAbsolutePath() + "\"!");

			this.osReleaseMap = Collections.unmodifiableMap(new HashMap<>());
			this.distro = Distribution.UNKNOWN;
		}
	}

	// TODO: Implementing this method for LinuxOS is going to require some research, which is why I am no doing it
	//  right now. The idea is that different distros are all based on each other and that the isAtLeast method could
	//  make use of that for LinuxOS. For example: Ubuntu is based on Debian, so `new LinuxOS(UBUNTU).isAtLeast
	//  (DEBIAN)` would return true, while `new LinuxOS(DEBIAN).isAtLeast(UBUNTU)` would return false.
//	public boolean isAtLeast(Distribution parent) {
//		return // "parent" is parent distribution of this.getDistro()
//	}

	public boolean equals(LinuxOS other) {
		// TODO: Figure out what of osReleaseMap to include here
		return Objects.equals(this.getDistro(), other.getDistro());
	}

	@Override
	public boolean equals(OperatingSystem other) {
		return other instanceof LinuxOS && this.equals((LinuxOS) other);
	}

	@Override
	public String toString() {
		return "LINUX[" + getDistro() + "]";
	}

	/**
	 * @return The exact or next-best Linux distribution.
	 */
	public Distribution getDistro() {
		return distro;
	}

	@Override
	public final OSFamily getFamily() {
		return OSFamily.LINUX;
	}

	/**
	 * @return An unmodifiable map containing all the information from the "/etc/os-release" file on Linux systems.
	 */
	public Map<String, String> getOsReleaseMap() {
		return osReleaseMap;
	}

	public enum Distribution {

		DEBIAN, UBUNTU, GENTOO, LINUX_MINT, RED_HAT_ENTERPRISE_LINUX, CENTOS, FEDORA, ARCH_LINUX,
		/**
		 * Suse/OpenSUSE and any child-distributions.
		 */
		SUSE,
		/**
		 * An unknown or at least unrecognizable Linux based operating system.
		 */
		UNKNOWN;

		public static Distribution fromID(String id) {
			switch (id) {
				// All the OSs up to (including) SUSE can be found in this GitHub repo or its forks:
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
					return UNKNOWN;
			}
		}

	}

}
