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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;

public class CurrentOS {

	private static final OperatingSystem OS = determine(System.getProperty("os.name"),
			System.getProperty("os.version"), new File("/etc/os-release"));

	static OperatingSystem determine(String name, String version, File osRelease) {
		// Locale.ROOT prevents funny locale stuff from happening
		name = name.toLowerCase(Locale.ROOT).trim();

		// Decide Windows version
		if (name.startsWith("win")) {
			// TODO: Use version here instead of name. In order to that we'd need a list of what os.version
			// is on the different Windows versions to be completely sure.
			int lastSpace = name.lastIndexOf(' ');

			if (lastSpace > -1) {
				switch (name.substring(lastSpace + 1)) {
					case "95":
						return new WinOS(WinOS.Version.WIN_95, false);
					case "98":
						return new WinOS(WinOS.Version.WIN_98, false);
					case "xp":
						return new WinOS(WinOS.Version.WIN_XP, false);
					case "vista":
						return new WinOS(WinOS.Version.WIN_VISTA, false);
					case "7":
						return new WinOS(WinOS.Version.WIN_7, false);
					case "8":
						return new WinOS(WinOS.Version.WIN_8, false);
					case "8.1":
						return new WinOS(WinOS.Version.WIN_8_1, false);
					case "10":
						return new WinOS(WinOS.Version.WIN_10, false);
					default:
						break;
				}
			}

			return new WinOS(WinOS.Version.UNKNOWN, false);
		}

		// Decide Mac version
		if (name.startsWith("mac")) {
			String[] versionSplit = version.split("\\.");

			// Get the major and minor version. If we can't read it, setting it to unknown
			// will allow it to fall through the switch and return MAC_UNKNOWN
			int major = versionSplit.length > 0 ? Integer.parseInt(versionSplit[0]) : -1;
			int minor = versionSplit.length > 1 ? Integer.parseInt(versionSplit[1]) : -1;

			return new MacOS(major, minor);
		}

		// Decide Linux version
		if (name.contains("nix") || name.contains("nux") || name.contains("aix")) {
			// If the file does not exist there is nothing more we can achieve
			if (!osRelease.exists())
				return new LinuxOS(LinuxOS.Distribution.UNKNOWN);

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
				return new LinuxOS(LinuxOS.Distribution.UNKNOWN);
			}

			// ID is the computer friendly name of the current Linux distribution
			String id = osReleaseMap.get("ID");
			// ID_LIKE is a list of space-separated IDs of parent distributions
			String idLike = osReleaseMap.get("ID_LIKE");

			LinuxOS.Distribution distro = LinuxOS.Distribution.UNKNOWN;
			if (id != null)
				distro = LinuxOS.Distribution.fromID(id);

			if (distro == LinuxOS.Distribution.UNKNOWN && idLike != null)
				for (String parentID : idLike.split(" "))
					if ((distro = LinuxOS.Distribution.fromID(parentID)) != LinuxOS.Distribution.UNKNOWN)
						return new LinuxOS(distro);

			return new LinuxOS(LinuxOS.Distribution.UNKNOWN);
		}

		// Others

		if (name.contains("sunos"))
			return new OtherOS(OtherOS.OS.SOLARIS);

		return new OtherOS(OtherOS.OS.UNKNOWN);
	}

	public static OperatingSystem get() {
		return OS;
	}

	public enum Family {

		WINDOWS, MAC, LINUX, OTHER

	}

}
