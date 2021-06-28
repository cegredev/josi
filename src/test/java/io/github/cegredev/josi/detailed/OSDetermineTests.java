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
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Locale;

import static io.github.cegredev.josi.detailed.OtherOS.Identity.*;
import static io.github.cegredev.josi.detailed.WinOS.Release.*;
import static io.github.cegredev.josi.detailed.LinuxOS.Distribution.*;
import static org.junit.jupiter.api.Assertions.*;

public class OSDetermineTests {

	private static final String MESSAGE = "Did not determine correct operating system!";
	private static final String OS_RELEASES_PATH = "src/test/resources/etc/os-releases/";

	private static final WDT[] WIN_TESTS = {new WDT(WinOS.Release.UNKNOWN, "1.0"),
			new WDT(WIN_95, "95"), new WDT(WIN_98, "98"), new WDT(WIN_XP, "XP"),
			new WDT(WIN_VISTA, "Vista"), new WDT(WIN_7, "7"), new WDT(WIN_8, "8"),
			new WDT(WIN_8_1, "8.1"), new WDT(WIN_10, "10"), new WDT(WIN_11, "11")};

	private static final MDT[] MAC_TESTS = {new MDT(MacOS.UNKNOWN_MAJOR, 0, "", ""),
			new MDT(1, 0, "", "1.0"), new MDT(4, 0, "", "4."),
			new MDT(10, 0, "", "10"), new MDT(10, 0, "OS X", "10.0"),
			new MDT(10, 1, "OS X", "10.1.1"), new MDT(10, 2, "OS X", "10.2.4"),
			new MDT(10, 3, "", "10.3"), new MDT(10, 4, "", "10.4"),
			new MDT(10, 5, "", "10.5.1"), new MDT(10, 6, "", "10.6"),
			new MDT(10, 7, "", "10.7"), new MDT(10, 8, "", "10.8"),
			new MDT(10, 9, "", "10.9"), new MDT(10, 10, "", "10.10"),
			new MDT(10, 11, "", "10.11"), new MDT(10, 12, "", "10.12"),
			new MDT(10, 13, "", "10.13"), new MDT(10, 14, "", "10.14"),
			new MDT(10, 15, "", "10.15"), new MDT(11, 0, "", "10.16"),
			new MDT(11, 0, " OS X", "11.0"), new MDT(11, 2, " OS X", "11.2.1"),
			new MDT(12, 0, " OS X", "12.0"), new MDT(12, 0, " OS X", "10.17")};


	private static final LDT[] LINUX_TESTS = {new LDT(LinuxOS.Distribution.UNKNOWN, "unknown"),
			new LDT(DEBIAN, "debian"), new LDT(UBUNTU, "ubuntu"), new LDT(GENTOO, "gentoo"), new LDT(LINUX_MINT,
			"linux_mint"), new LDT(RED_HAT_ENTERPRISE_LINUX, "rhel"), new LDT(CENTOS, "centos"), new LDT(FEDORA,
			"fedora"), new LDT(ARCH_LINUX, "arch"), new LDT(SUSE, "suse")};

	private static final ODT[] OTHER_TESTS = {new ODT(OtherOS.Identity.UNKNOWN, "does not exist", ""), new ODT(SOLARIS
			, "sunos", "")};

	@Test
	public void testWinDetermine() {
		for (WDT test : WIN_TESTS)
			test.test();
	}

	@Test
	public void testMacDetermine() {
		for (MDT test : MAC_TESTS)
			test.test();
	}

	@Test
	public void testLinuxDetermine() {
		for (LDT test : LINUX_TESTS)
			test.test();

		// Special case file does not exist
		new LDT(LinuxOS.Distribution.UNKNOWN, "does not exist").test();

		// Special case file is broken:
		assertEquals(new LinuxOS(LinuxOS.Distribution.UNKNOWN), OS.determine(OSFamily.LINUX, "Linux", "",
				new File(OS_RELEASES_PATH + "broken.txt")), MESSAGE);
	}

	@Test
	public void testOtherDetermine() {
		for (ODT test : OTHER_TESTS)
			test.test();
	}

	private static class WDT extends DetermineTest {

		private WDT(WinOS.Release expectedRelease, boolean server, String name, String version) {
			super(new WinOS(expectedRelease, server), name, version, "");
		}

		private WDT(WinOS.Release expectedRelease, boolean server, String version) {
			this(expectedRelease, server, "Windows " + version, version);
		}

		private WDT(WinOS.Release expectedRelease, String version) {
			this(expectedRelease, false, version);
		}

		@Override
		protected OSFamily getFamily() {
			return OSFamily.WINDOWS;
		}

	}

	/**
	 * MDT = Mac determine test
	 */
	private static class MDT extends DetermineTest {

		private MDT(int major, int minor, String nameExtension, String version) {
			super(new MacOS(major, minor), "Mac" + nameExtension, version, "");
		}

		@Override
		protected OSFamily getFamily() {
			return OSFamily.MAC;
		}

	}

	/**
	 * LDT = Linux determine test
	 */
	private static class LDT extends DetermineTest {

		private LDT(LinuxOS.Distribution expected, String filePrefix) {
			super(new LinuxOS(expected), "Linux", "", filePrefix);
		}

		@Override
		public void test() {
			// Ideally this would be solved by checking when the file does not exist in the loop, but... No.
			for (int i = 0; i < 10000; i++) {
				File file = new File(OS_RELEASES_PATH + osReleasePrefix + i + ".txt");
				if (i > 0 && !file.exists())
					break;

				assertEquals(expected, OS.determine(OSFamily.LINUX, name, version, file), MESSAGE);
			}
		}

		@Override
		protected OSFamily getFamily() {
			return OSFamily.LINUX;
		}

	}

	/**
	 * ODT = Other determine test
	 */
	private static class ODT extends DetermineTest {

		private ODT(OtherOS.Identity expected, String name, String version) {
			super(new OtherOS(expected), name, version, "");
		}

		private ODT(OtherOS.Identity expected, String name) {
			this(expected, name, "");
		}

		@Override
		protected OSFamily getFamily() {
			return OSFamily.UNKNOWN;
		}
	}

	private static abstract class DetermineTest {

		protected final String name, version, osReleasePrefix;
		protected final OperatingSystem expected;

		private DetermineTest(OperatingSystem expected, String name, String version, String osReleasePrefix) {
			this.expected = expected;
			this.name = name.toLowerCase(Locale.ROOT).trim();
			this.version = version;
			this.osReleasePrefix = osReleasePrefix;
		}

		protected abstract OSFamily getFamily();

		public void test() {
			assertEquals(expected, OS.determine(getFamily(), name, version, new File(osReleasePrefix)));
		}

	}

}
