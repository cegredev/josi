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

/**
 * Represents a Mac based operating system.
 *
 * @author cegredev
 */
public class MacOS extends OperatingSystem {

	public static final int UNKNOWN_MAJOR = -1;

	// The version numbers as indicated by https://en.wikipedia.org/wiki/MacOS#Release_history
	private final int major;
	private final int minor;

	public MacOS(int major, int minor) {
		this.major = major;
		this.minor = minor;
	}

	public MacOS(String plainVersion) {
		int major = UNKNOWN_MAJOR, minor = 0;

		char sep = '.';
		int index = plainVersion.indexOf(sep);
		if (index >= 1) {
			major = Integer.parseInt(plainVersion.substring(0, index));

			int prevIndex = index;
			index = plainVersion.indexOf(sep, index + 1);
			if (index > -1)
				minor = Integer.parseInt(plainVersion.substring(prevIndex + 1, index));
			else if (plainVersion.length() > prevIndex + 1)
				minor = Integer.parseInt(plainVersion.substring(prevIndex + 1));
		} else if (plainVersion.length() > 0) {
			major = Integer.parseInt(plainVersion);
		}

		// Sometimes, MacOS versions 11 and 12 (as of June 2021) are reported as 10.16/10.17
		// by System.getProperty("os.name") which is why we have to do this to achieve the official versioning
		if (major == 10) {
			int overhead = minor - 15;

			if (overhead > 0) {
				minor = 0;
				major += overhead;
			}
		}

		this.major = major;
		this.minor = minor;
	}

	public boolean isAtLeast(int otherMajor, int otherMinor) {
		int major = getMajor(), minor = getMinor();
		return major > otherMajor || (major == otherMajor && minor >= otherMinor);
	}

	public boolean isAtLeast(int otherMajor) {
		return getMajor() >= otherMajor;
	}

	public boolean equals(MacOS other) {
		return this.getMajor() == other.getMajor() && this.getMinor() == other.getMinor();
	}

	@Override
	public boolean equals(OperatingSystem other) {
		return other instanceof MacOS && this.equals((MacOS) other);
	}

	@Override
	public String toString() {
		int major = getMajor();
		return "MAC[" + (major == UNKNOWN_MAJOR ? "?" : major) + "." + getMinor() + "]";
	}

	public int getMinor() {
		return minor;
	}

	public int getMajor() {
		return major;
	}

	@Override
	public final OSFamily getFamily() {
		return OSFamily.MAC;
	}

}
