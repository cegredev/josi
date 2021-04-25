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

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests concerning the recognition of operating system names.
 */
public class OSNameTests {

	private static final String FAIL_MESSAGE = "Did not determine the correct OS for the given name.";

	// Relative path, yay! Let's hope this doesn't break.
	private static final File ETC_OSRELEASE = new File("src/test/resources/etc/os-release");

	/**
	 * Used to separate enum name, os name and os version in the arrays below.
	 */
	private static final String SEPARATOR = ":";

	// The problem with this is that it relies on the naming of all the OSs in the OS enum to check the result of
	// the determine method. So if any of those names get changed, you'll have to modify them here as well.
	private static final String[] windowsNames = {"UNKNOWN:Windows 1.0", "95:Windows 95", "98:Windows 98",
			"XP:Windows XP", "VISTA:Windows Vista", "7:Windows 7", "8:Windows 8", "8_1:Windows 8.1", "10:Windows 10"};

	private static final String[] macNames = {"UNKNOWN:Mac: ", "UNKNOWN:Mac:10", "OSX_CHEETAH:Mac OS X:10.0",
			"OSX_PUMA:Mac OS X:10.1.1", "OSX_JAGUAR:Mac:10.2.4", "OSX_PANTHER:Mac:10.3", "OSX_TIGER:Mac:10.4",
			"OSX_LEOPARD:Mac:10.5.1", "OSX_SNOW_LEOPARD:Mac:10.6", "OSX_LION:Mac:10.7", "OSX_MOUNTAIN_LION:Mac:10.8",
			"OSX_MAVERICKS:Mac:10.9", "OSX_YOSEMITE:Mac:10.10", "OSX_EL_CAPITAN:Mac:10.11", "OS_SIERRA:Mac:10.12",
			"OS_HIGH_SIERRA:Mac:10.13", "OS_MOJAVE:Mac:10.14.", "OS_CATALINA:Mac:10.15", "OS_BIG_SUR:Mac:10.16"};

	private static final String[] linuxNames = {"LINUX_UNKNOWN:Linux: ", "DEBIAN:debian:debian",
			"UBUNTU:ubuntu:debian", "CENTOS:centos:rhel fedora", "FEDORA:fedora: ", "ARCH_LINUX:arch: ",
			"GENTOO:gentoo: ", "SUSE:\"opensuse\":\"suse\"", "SUSE:\"opensuse-leap\":\"suse opensuse\"", "SUSE" +
			":\"opensuse-tumbleweed\":\"suse opensuse\"", "SUSE:\"opensuse-tumbleweed-kubic\":\"suse opensuse\"",
			"LINUX_MINT:linuxmint:ubuntu", "RED_HAT_ENTERPRISE_LINUX:rhel:fedora"};

	private static final String[] otherNames = {"UNKNOWN:does not exist", "SOLARIS:sunos"};

	@Test
	public void testWindowsDetermine() {
		for (String enumAndName : windowsNames) {
			String[] split = enumAndName.split(SEPARATOR);

			assertEquals(OS.valueOf("WIN_" + split[0]), OS.determine(split[1], "", ETC_OSRELEASE), FAIL_MESSAGE);
		}
	}

	@Test
	public void testMacDetermine() {
		for (String enumNameAndVersion : macNames) {
			String[] split = enumNameAndVersion.split(SEPARATOR);

			assertEquals(OS.valueOf("MAC_" + split[0]), OS.determine(split[1], split[2], ETC_OSRELEASE), FAIL_MESSAGE);
		}
	}

	@Test
	public void testLinuxDetermine() throws IOException {
		for (String enumIDAndIDLIKE : linuxNames) {
			String[] split = enumIDAndIDLIKE.split(SEPARATOR);

			// Oh boy, a test dependent on an IO interaction. Sadly this is the easiest way for me to implement it
			// without changing the structure of the determine method even more and thereby making it more confusing.
			// Let's hope this doesn't cause any problems.
			String content = "ID=" + split[1] + System.lineSeparator() + "ID_LIKE=" + split[2];
			Files.write(ETC_OSRELEASE.toPath(), content.getBytes(), StandardOpenOption.TRUNCATE_EXISTING,
					StandardOpenOption.WRITE);

			assertEquals(OS.valueOf(split[0]), OS.determine("Linux", "", ETC_OSRELEASE), FAIL_MESSAGE);
		}
	}

	@Test
	public void testOtherDetermine() {
		for (String enumAndName : otherNames) {
			String[] split = enumAndName.split(SEPARATOR);

			assertEquals(OS.valueOf(split[0]), OS.determine(split[1], "", ETC_OSRELEASE), FAIL_MESSAGE);
		}
	}

}
