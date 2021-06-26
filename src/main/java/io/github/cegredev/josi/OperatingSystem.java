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

import java.util.Arrays;
import java.util.Objects;

/**
 * Represents a specific instance of an operating system with access to every last bit of information.
 *
 * @author cegredev
 */
public abstract class OperatingSystem {

	/**
	 * The name provided by something like {@code System.getProperty("os.name")}.
	 */
	private final String plainName;

	/**
	 * The version provided by something like {@code System.getProperty("os.version")}.
	 */
	private final String plainVersion;

	/**
	 * The family of the operating system.
	 */
	private final Family family;

	protected OperatingSystem(String plainName, String plainVersion, Family family) {
		this.plainName = plainName;
		this.plainVersion = plainVersion;
		this.family = family;
	}

	public boolean isFamily(Family... families) {
		for (Family family : families)
			if (family.equals(this.getFamily()))
				return true;
		return false;
	}

	public boolean equals(OperatingSystem other) {
		return Objects.equals(this.getFamily(), other.getFamily())
				&& Objects.equals(this.getPlainName(), other.getPlainName())
				&& Objects.equals(this.getPlainVersion(), other.getPlainVersion());
	}

	@Override
	public boolean equals(Object other) {
		return other instanceof OperatingSystem && this.equals((OperatingSystem) other);
	}

	public Family getFamily() {
		return family;
	}

	public String getPlainName() {
		return plainName;
	}

	public String getPlainVersion() {
		return plainVersion;
	}

	@Override
	public String toString() {
		return "OS[family=" + getFamily() + ",name=" + getPlainName() + ",version=" + getPlainVersion() + "]";
	}

	public enum Family {

		WINDOWS, MAC, LINUX, OTHER

	}
}
