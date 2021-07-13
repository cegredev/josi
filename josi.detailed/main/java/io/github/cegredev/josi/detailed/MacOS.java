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
 * Represents a Mac based operating system using the major and minor versions of each release, as indicated by the
 * official versioning. This means versions reported in any other form (for example by {@code
 * System.getProperty("os.version")}) will be converted to match this standard.
 *
 * @author cegredev
 */
public class MacOS extends OperatingSystem {

	/**
	 * Indicates that the major version of this OS could not be determined.
	 */
	public static final int UNKNOWN_MAJOR = -1;

	// The version numbers as indicated by https://en.wikipedia.org/wiki/MacOS#Release_history
	/**
	 * The major version of this release.
	 */
	private final int major;
	/**
	 * The minor version of this release.
	 */
	private final int minor;

	/**
	 * Warning: This constructor does not correct versioning according to the official standard.
	 *
	 * @param major The major version of this release.
	 * @param minor The minor version of this release.
	 */
	// FIXME: Make this constructor protected, make all public constructors do the correction and provide some other
	//  way to bypass the correction which makes it obvious that its doing that (maybe static method "createUnsafe")
	public MacOS(int major, int minor) {
		this.major = major;
		this.minor = minor;
	}

	/**
	 * Determines {@link #getMajor()} and {@link #getMinor()} from a string and corrects them according to the official
	 * versioning.
	 *
	 * @param plainVersion A string containing the version in form of {@code System.getProperty("os.version")}, i.e.:
	 *                     "major.minor".
	 */
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

	/**
	 * @param otherMajor The major version to check against.
	 * @param otherMinor The minor version to check against.
	 * @return Whether the release represented by this object is greater or equal to the one provided.
	 */
	public boolean isAtLeast(int otherMajor, int otherMinor) {
		// TODO: Decide whether or not to return false if major is unknown
		int major = getMajor(), minor = getMinor();
		return major > otherMajor || (major == otherMajor && minor >= otherMinor);
	}

	/**
	 * @param otherMajor The major version to check against.
	 * @return Whether {@link #getMajor()} is greater or equal to the given major.
	 */
	public boolean isAtLeast(int otherMajor) {
		return getMajor() >= otherMajor;
	}

	/**
	 * @param other The instance to compare to.
	 * @return Whether both instances' {@link #getMajor()} and {@link #getMinor()} are equal.
	 */
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

	/**
	 * @return The major version of this release.
	 */
	public int getMajor() {
		return major;
	}

	/**
	 * @return The minor version of this release.
	 */
	public int getMinor() {
		return minor;
	}

	@Override
	public final OSFamily getFamily() {
		return OSFamily.MAC;
	}

}
