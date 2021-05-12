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
			// TODO: Use version here instead of name
			int lastSpace = name.lastIndexOf(' ');

			if (lastSpace > -1) {
				switch (name.substring(lastSpace + 1)) {
					case "95":
//						return WIN_95;
						return new WinOS(WinOS.Version.WIN_95, false);
//					case "98":
//						return WIN_98;
//					case "xp":
//						return WIN_XP;
//					case "vista":
//						return WIN_VISTA;
//					case "7":
//						return WIN_7;
					case "8":
						return new WinOS(WinOS.Version.WIN_8, false);
//					case "8.1":
//						return WIN_8_1;
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

//			switch (major) {
//				case "10":
//					switch (minor) {
//						case "0":
//							return MAC_OSX_CHEETAH;
//						case "1":
//							return MAC_OSX_PUMA;
//						case "2":
//							return MAC_OSX_JAGUAR;
//						case "3":
//							return MAC_OSX_PANTHER;
//						case "4":
//							return MAC_OSX_TIGER;
//						case "5":
//							return MAC_OSX_LEOPARD;
//						case "6":
//							return MAC_OSX_SNOW_LEOPARD;
//						case "7":
//							return MAC_OSX_LION;
//						case "8":
//							return MAC_OSX_MOUNTAIN_LION;
//						case "9":
//							return MAC_OSX_MAVERICKS;
//						case "10":
//							return MAC_OSX_YOSEMITE;
//						case "11":
//							return MAC_OSX_EL_CAPITAN;
//						case "12":
//							return MAC_OS_SIERRA;
//						case "13":
//							return MAC_OS_HIGH_SIERRA;
//						case "14":
//							return MAC_OS_MOJAVE;
//						case "15":
//							return MAC_OS_CATALINA;
//						case "16":
//							// Big Sur is macOS version 11.x, but sometimes 10.16 is returned
//							return MAC_OS_BIG_SUR;
//						default:
//							return MAC_UNKNOWN;
//					}
//				case "11":
//					return MAC_OS_BIG_SUR;
//				default:
//					return MAC_UNKNOWN;
//			}
		}

		// Decide Linux version
		if (name.contains("nix") || name.contains("nux") || name.contains("aix")) {
			// If the file does not exist there is nothing more we can achieve
			if (!osRelease.exists())
				return new LinuxOS(LinuxOS.Distro.UNKNOWN);

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
				return new LinuxOS(LinuxOS.Distro.UNKNOWN);
			}

			// ID is the computer friendly name of the current Linux distribution
			String id = osReleaseMap.get("ID");
			// ID_LIKE is a list of space-separated IDs of parent distributions
			String idLike = osReleaseMap.get("ID_LIKE");

			LinuxOS.Distro distro = LinuxOS.Distro.UNKNOWN;
			if (id != null)
				distro = LinuxOS.Distro.fromID(id);

			if (distro == LinuxOS.Distro.UNKNOWN && idLike != null)
				for (String parentID : idLike.split(" "))
					if ((distro = LinuxOS.Distro.fromID(parentID)) != LinuxOS.Distro.UNKNOWN)
						return new LinuxOS(distro);

			return new LinuxOS(LinuxOS.Distro.UNKNOWN);
		}

		// Others

		if (name.contains("sunos"))
			return new OtherOS(OtherOS.OS.SOLARIS);

		return new OtherOS(OtherOS.OS.UNKNOWN);
	}

	public enum Family {

		WINDOWS, MAC, LINUX, OTHER

	}

}
