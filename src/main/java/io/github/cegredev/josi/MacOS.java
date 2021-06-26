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

/**
 * Represents a Mac based operating system.
 *
 * @author cegredev
 */
public class MacOS extends OperatingSystem {

	private final int major;
	private final int minor;

	public MacOS(String plainName, String plainVersion, int major, int minor) {
		super(plainName, plainVersion, Family.MAC);

		this.major = major;
		this.minor = minor;
	}

	public MacOS(String plainName, String plainVersion) {
		super(plainName, plainVersion, Family.MAC);

		int major = -1, minor = -1;

		char sep = '.';
		int index = plainVersion.indexOf(sep);
		if (index >= 1) {
			major = Integer.parseInt(plainVersion.substring(0, index));

			int prevIndex = index;
			index = plainVersion.indexOf(sep, index + 1);
			if (index >= 1) {
				minor = Integer.parseInt(plainVersion.substring(prevIndex + 1, index));
			}
		}

		this.major = major;
		this.minor = minor;
	}

	public int getMinor() {
		return minor;
	}

	public int getMajor() {
		return major;
	}

}
