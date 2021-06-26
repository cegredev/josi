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
 * Represents a Windows based operating system.
 *
 * @author cegredev
 */
public class WinOS extends OperatingSystem {

	private final boolean server;
	private final Version version;

	public WinOS(String plainName, String plainVersion, Version version, boolean server) {
		super(plainName, plainVersion, Family.WINDOWS);

		this.version = version;
		this.server = server;
	}

	public WinOS(String plainName, String plainVersion) {
		super(plainName, plainVersion, Family.WINDOWS);

		// TODO: Use version here instead of name. In order to that we'd need a list of what os.version
		// is on the different Windows versions to be completely sure.
		this.version = Version.fromString(plainName);

		// FIXME: Actually make this check sensible
		this.server = plainName.contains("server");
	}

	public Version getVersion() {
		return version;
	}

	public boolean isServer() {
		return server;
	}

	public enum Version {

		WIN_95, WIN_98, WIN_XP, WIN_VISTA, WIN_7, WIN_8, WIN_8_1, WIN_10, WIN_11, UNKNOWN;

		public static Version fromString(String name) {
			int lastSpace = name.lastIndexOf(' ');

			if (lastSpace > -1) {
				return switch (name.substring(lastSpace + 1)) {
					case "95" -> WIN_95;
					case "98" -> WIN_98;
					case "xp" -> WIN_XP;
					case "vista" -> WIN_VISTA;
					case "7" -> WIN_7;
					case "8" -> WIN_8;
					case "8.1" -> WIN_8_1;
					case "10" -> WIN_10;
					case "11" -> WIN_11;
					default -> UNKNOWN;
				};
			} else {
				return UNKNOWN;
			}
		}
	}

}
