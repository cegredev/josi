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
 * Represents any operating system that is not Windows, Mac or Linux based.
 *
 * @author cegredev
 */
public class OtherOS extends OperatingSystem {

	private final OS os;

	public OtherOS(String plainName, String plainVersion, OS os) {
		super(plainName, plainVersion, Family.OTHER);

		this.os = os;
	}

	public OtherOS(String plainName, String plainVersion) {
		this(plainName, plainVersion, OS.fromString(plainName));
	}

	public OS getOS() {
		return os;
	}

	public enum OS {

		/**
		 * The Solaris operating system.
		 */
		SOLARIS,
		/**
		 * An operating system that cannot be classified.
		 */
		UNKNOWN;

		public static OS fromString(String name) {
			if (name.contains("sunos"))
				return SOLARIS;

			return UNKNOWN;
		}

	}

}
