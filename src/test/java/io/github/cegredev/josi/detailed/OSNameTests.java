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
 * Tests concerning the recognition of operating systems based on the given information (like name, version, or
 * /etc/os-release).
 */
public class OSNameTests {

	// TODO: Commented out for now, but reimplement for OperatingSystem tests

//	private static final String FAIL_MESSAGE = "Did not determine the correct OS for the given name.";
//
//	private static final WDT[] WIN_TESTS = {new WDT(WIN_UNKNOWN, "Windows 1.0"), new WDT(WIN_95, "Windows 95"),
//			new WDT(WIN_98, "Windows 98"), new WDT(WIN_XP, "Windows XP"), new WDT(WIN_VISTA, "Windows Vista"),
//			new WDT(WIN_7, "Windows 7"), new WDT(WIN_8, "Windows 8"), new WDT(WIN_8_1, "Windows 8.1"),
//			new WDT(WIN_10, "Windows 10")};
//
//	private static final MDT[] MAC_TESTS = {new MDT(MAC_UNKNOWN, "Mac", " "), new MDT(MAC_UNKNOWN, "Mac", "1.0"),
//			new MDT(MAC_UNKNOWN, "Mac", "10"), new MDT(MAC_OSX_CHEETAH, "MacOS X", "10.0"),
//			new MDT(MAC_OSX_PUMA, "MacOS X", "10.1.1"), new MDT(MAC_OSX_JAGUAR, "MacOS X", "10.2.4"),
//			new MDT(MAC_OSX_PANTHER, "Mac", "10.3"), new MDT(MAC_OSX_TIGER, "Mac", "10.4"),
//			new MDT(MAC_OSX_LEOPARD, "Mac", "10.5.1"), new MDT(MAC_OSX_SNOW_LEOPARD, "Mac", "10.6"),
//			new MDT(MAC_OSX_LION, "Mac", "10.7"), new MDT(MAC_OSX_MOUNTAIN_LION, "Mac", "10.8"),
//			new MDT(MAC_OSX_MAVERICKS, "Mac", "10.9"), new MDT(MAC_OSX_YOSEMITE, "Mac", "10.10"),
//			new MDT(MAC_OSX_EL_CAPITAN, "Mac", "10.11"), new MDT(MAC_OS_SIERRA, "Mac", "10.12"),
//			new MDT(MAC_OS_HIGH_SIERRA, "Mac", "10.13"), new MDT(MAC_OS_MOJAVE, "Mac", "10.14"),
//			new MDT(MAC_OS_CATALINA, "Mac", "10.15"), new MDT(MAC_OS_BIG_SUR, "Mac", "10.16"),
//			new MDT(MAC_OS_BIG_SUR, "Mac OS X", "11.0"), new MDT(MAC_OS_BIG_SUR, "Mac OS X", "11.2.1"),
//			new MDT(MAC_OS_MONTEREY, "Mac OS X", "12.0"), new MDT(MAC_OS_MONTEREY, "Mac OS X", "10.17")};
//
//	private static final LDT[] LINUX_TESTS = {new LDT(LINUX_UNKNOWN, "unknown"), new LDT(DEBIAN, "debian"),
//			new LDT(UBUNTU, "ubuntu"), new LDT(GENTOO, "gentoo"), new LDT(LINUX_MINT, "linux_mint"),
//			new LDT(RED_HAT_ENTERPRISE_LINUX, "rhel"), new LDT(CENTOS, "centos"), new LDT(FEDORA, "fedora"),
//			new LDT(ARCH_LINUX, "arch"), new LDT(SUSE, "suse")};
//
//	private static final ODT[] OTHER_TESTS = {new ODT(UNKNOWN, "does not exist", ""), new ODT(SOLARIS, "sunos", "")};
//
//	@Test
//	public void testWindowsDetermine() {
//		for (WDT test : WIN_TESTS)
//			assertEquals(test.expected, OSFamily.determine(test.name, "", null), FAIL_MESSAGE);
//	}
//
//	@Test
//	public void testMacDetermine() {
//		for (MDT test : MAC_TESTS)
//			assertEquals(test.expected, OSFamily.determine(test.name, test.version, null), FAIL_MESSAGE);
//	}
//
//	@Test
//	public void testLinuxDetermine() {
//		String basePath = "src/test/resources/etc/os-releases/";
//
//		for (LDT test : LINUX_TESTS) {
//			for (int i = 0; i < 10000; i++) {
//				File file = new File(basePath + test.fileName + i + ".txt");
//				if (!file.exists())
//					break;
//
//				assertEquals(test.expected, OSFamily.determine("Linux", "", file), FAIL_MESSAGE);
//			}
//		}
//
//		// Special case file does not exist
//		assertEquals(LINUX_UNKNOWN, OSFamily.determine("Linux", "",
//				new File("a file that does not exist")), FAIL_MESSAGE);
//
//		// Special case file is broken:
//		assertEquals(LINUX_UNKNOWN, OSFamily.determine("Linux", "", new File(basePath + "broken.txt")),
//				FAIL_MESSAGE);
//	}
//
//	@Test
//	public void testOtherDetermine() {
//		for (ODT test : OTHER_TESTS)
//			assertEquals(test.expected, OSFamily.determine(test.name, test.version, null), FAIL_MESSAGE);
//	}

	/**
	 * WDT = Windows determine test
	 */
	private static class WDT extends DetermineTest {

		private final String name;

		private WDT(OSFamily expected, String name) {
			super(expected);

			this.name = name;
		}
	}

	/**
	 * MDT = Mac determine test
	 */
	private static class MDT extends DetermineTest {

		private final String name, version;

		private MDT(OSFamily expected, String name, String version) {
			super(expected);

			this.name = name;
			this.version = version;
		}
	}

	/**
	 * LDT = Linux determine test
	 */
	private static class LDT extends DetermineTest {

		private final String fileName;

		private LDT(OSFamily expected, String name) {
			super(expected);

			this.fileName = name;
		}

	}

	/**
	 * ODT = Other determine test
	 */
	private static class ODT extends DetermineTest {

		private final String name, version;

		private ODT(OSFamily expected, String name, String version) {
			super(expected);

			this.name = name;
			this.version = version;
		}
	}

	private static class DetermineTest {

		protected final OSFamily expected;

		private DetermineTest(OSFamily expected) {
			this.expected = expected;
		}
	}

}
