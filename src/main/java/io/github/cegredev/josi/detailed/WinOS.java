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
 * Represents a Windows based operating system.
 *
 * @author cegredev
 */
public class WinOS extends OperatingSystem {

	private final boolean server;
	private final Release release;

	public WinOS(Release release, boolean server) {
		this.release = release;
		this.server = server;
	}

	public WinOS(String plainName) {
		// TODO: Use version here instead of name. In order to that we'd need a list of what os.version
		// is on the different Windows versions to be completely sure.
		this.release = Release.fromString(plainName);

		// FIXME: Actually make this check sensible
		this.server = plainName.contains("server");
	}

	/**
	 * @param other The {@link WinOS} to check against.
	 * @return Whether the operating systems are equal to each other.
	 */
	public boolean equals(WinOS other) {
		return this.isServer() == other.isServer()
				&& Objects.equals(this.getRelease(), other.getRelease());
	}

	@Override
	public boolean equals(OperatingSystem other) {
		return other instanceof WinOS && this.equals((WinOS) other);
	}

	@Override
	public String toString() {
		return "WINDOWS[" + getRelease() + (isServer() ? "+server" : "") + "]";
	}

	public Release getRelease() {
		return release;
	}

	public boolean isServer() {
		return server;
	}

	@Override
	public final OSFamily getFamily() {
		return OSFamily.WINDOWS;
	}

	/**
	 * A list of possible Windows releases/versions.
	 */
	public enum Release {

		WIN_95, WIN_98, WIN_XP, WIN_VISTA, WIN_7, WIN_8, WIN_8_1, WIN_10, WIN_11, UNKNOWN;

		public static Release fromString(String name) {
			int lastSpace = name.lastIndexOf(' ');

			if (lastSpace > -1) {
				switch (name.substring(lastSpace + 1)) {
					case "95":
						return WIN_95;
					case "98":
						return WIN_98;
					case "xp":
						return WIN_XP;
					case "vista":
						return WIN_VISTA;
					case "7":
						return WIN_7;
					case "8":
						return WIN_8;
					case "8.1":
						return WIN_8_1;
					case "10":
						return WIN_10;
					case "11":
						return WIN_11;
				}
			}

			return UNKNOWN;
		}
	}

}
