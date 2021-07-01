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

import java.util.Objects;

/**
 * Represents any operating system that is not Windows, Mac or Linux based.
 *
 * @author cegredev
 */
public class OtherOS extends OperatingSystem {

	private final Identity identity;

	public OtherOS(Identity identity) {
		this.identity = identity;
	}

	public OtherOS(String plainName) {
		this(Identity.fromString(plainName));
	}

	public Identity getIdentity() {
		return identity;
	}

	public boolean equals(OtherOS other) {
		return Objects.equals(this.getIdentity(), other.getIdentity());
	}

	@Override
	public boolean equals(OperatingSystem other) {
		return other instanceof OtherOS && this.equals((OtherOS) other);
	}

	@Override
	public final OSFamily getFamily() {
		return OSFamily.UNKNOWN;
	}

	public enum Identity {

		/**
		 * The Solaris operating system.
		 */
		SOLARIS,
		/**
		 * An operating system that cannot be classified.
		 */
		UNKNOWN;

		public static Identity fromString(String name) {
			if (name.contains("sunos"))
				return SOLARIS;

			return UNKNOWN;
		}

	}

}
