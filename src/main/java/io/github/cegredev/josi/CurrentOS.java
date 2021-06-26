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

import java.io.File;
import java.util.Locale;

/**
 * Contains the current {@link OperatingSystem}.
 *
 * @author cegredev
 */
public final class CurrentOS {

	private static final OperatingSystem OS = determine(System.getProperty("os.name"),
			System.getProperty("os.version"), new File("/etc/os-release"));

	/**
	 * No need to instantiate this class.
	 */
	private CurrentOS() {
	}

	static OperatingSystem determine(String name, String version, File osRelease) {
		// Locale.ROOT prevents funny locale stuff from happening during toLowerCase
		name = name.toLowerCase(Locale.ROOT).trim();

		if (name.startsWith("win")) {
			return new WinOS(name, version);
		}

		if (name.startsWith("mac")) {
			return new MacOS(name, version);
		}

		if (name.contains("nix") || name.contains("nux") || name.contains("aix")) {
			return new LinuxOS(name, version, osRelease);
		}

		// Others
		if (name.contains("sunos"))
			return new OtherOS(name, version, OtherOS.OS.SOLARIS);

		return new OtherOS(name, version, OtherOS.OS.UNKNOWN);
	}

	public static OperatingSystem get() {
		return OS;
	}

}
