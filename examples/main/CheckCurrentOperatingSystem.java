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
package main;

import io.github.cegredev.josi.OS;

/**
 * Examples of how to check and enforce the current operating system.
 *
 * @author cegredev
 */
public class CheckCurrentOperatingSystem {

	public static void checkIfOperatingSystemIsTheDesiredOne() {
		// Checks whether the OS is any of the given ones
		boolean isHated = OS.current().is(OS.WIN_8, OS.WIN_8_1);

		// Checks whether the OS is part of the given families
		boolean isUnixBased = OS.current().isFamily(OS.Family.MAC, OS.Family.LINUX);
	}

	public static void enforceSpecificOperatingSystem() {
		// Throws an exception if the operating system is one of the given ones
		OS.current().enforceNot(OS.WIN_95, OS.WIN_98, OS.WIN_VISTA);

		// Throws an exception if the operating system family is not part of the given ones
		OS.current().enforceFamily(OS.Family.WINDOWS, OS.Family.MAC);

		// Only returns the value if the current operating system is Windows, otherwise throws an exception.
		// Useful for oneliners, where you want to assign a value and check for the OS in the same step
		boolean gamer = OS.current().pickWindows(true);
	}

}
