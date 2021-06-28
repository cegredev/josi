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

import java.io.File;

/**
 * Contains the current {@link OperatingSystem}.
 *
 * @author cegredev
 */
public final class OS {

	private static final OperatingSystem OS = determine(OSFamily.current(), OSFamily.NAME_LOWER,
			System.getProperty("os.version"), new File("/etc/os-release"));

	/**
	 * No need to instantiate this class.
	 */
	private OS() {
	}

	/**
	 * Determines the exact operating system. Package-private for tests.
	 *
	 * @param family    The family of the operating system (will either be {@link OSFamily#current()} or something else
	 *                  for testing purposes).
	 * @param name      The name to determine the operating system from. Expects values in the format of {@code
	 *                  System.getProperty("os.name")}.
	 * @param version   The version to determine the operating system from. Expects values in the format of {@code
	 *                  System.getProperty("os.version")}.
	 * @param osRelease "/etc/os-release" on Linux systems: contains important information about the OS.
	 * @return An operating system matching the given values.
	 */
	static OperatingSystem determine(OSFamily family, String name, String version, File osRelease) {
		switch (family) {
			case WINDOWS:
				return new WinOS(name);
			case MAC:
				return new MacOS(version);
			case LINUX:
				return new LinuxOS(osRelease);
			default:
				return new OtherOS(name);
		}
	}

	public static OperatingSystem get() {
		return OS;
	}

}
