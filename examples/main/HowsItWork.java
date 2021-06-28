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

import io.github.cegredev.josi.OSFamily;

public class HowsItWork {

	public static void likeThis() {
		// The io.github.cegredev.josi.OS enum is the heart of the library. It contains the current operating system:
		OSFamily os = OSFamily.current();
		// ...which can be anything from WIN_95 to WIN_10 to any Mac version to a Linux based system. This is as
		// specific as it gets for this library.

		// The more useful information is the *family* of the operating system, i.e. Windows, Mac, Linux or Other
		// You can get it like this:
		OSFamily.Family family = os.getFamily();

		// ...and the use it to execute code based on it:
		switch (family) {
			case WINDOWS:
				// ...
			case MAC:
				// ...
			case LINUX:
				// ...
			case OTHER:
				// ...
		}

		// This is pretty much all you need to know, but I strongly encourage you to take a look at the other examples
		// to explore some of the utility methods Josi has to offer!
	}

}
