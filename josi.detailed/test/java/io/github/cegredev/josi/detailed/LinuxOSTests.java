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

import io.github.cegredev.josi.detailed.LinuxOS.Distribution;
import io.github.cegredev.josi.min.OSFamily;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;

import static io.github.cegredev.josi.detailed.LinuxOS.Distribution.*;
import static org.junit.jupiter.api.Assertions.*;

public class LinuxOSTests {

	private static final Path OS_RELEASES_PATH;

	static {
		// This determines the path of the .../test/resources/etc/os-releases directory, *because*:
		// The working directories are different when running tests via an IntelliJ config or "mvn test"
		// and since for development the IntelliJ-way is just much nicer, but for CI the maven goal is
		// used, we have to cover both. It's ugly, it's hacky, it's bound to break, but here it is.
		var modulePrefix = Path.of("josi.detailed");
		var path = Path.of("test/resources/etc/os-releases");
		if (!Path.of("").toAbsolutePath().endsWith(modulePrefix))
			path = modulePrefix.resolve(path);
		OS_RELEASES_PATH = path.toAbsolutePath();
	}

//	@Test
//	public void testParents() {
//		var os = new LinuxOS(OSDetermineTests.OS_RELEASES_PATH.resolve("centos0.txt").toFile());
//		var msg = "isAtLeast did not return the correct result!";
//
//		assertTrue(os.isAtLeast(FEDORA), msg);
//		assertTrue(os.isAtLeast(CENTOS), msg);
//		assertFalse(os.isAtLeast(UBUNTU), msg);
//	}

	private static void testDetermineHelper(Distribution expected, String filePrefix) {
		var expectedOS = new LinuxOS(expected);

		// Ideally this would be solved by checking when the file does not exist in the loop, but... No.
		for (int i = 0; i < 10000; i++) {
			File file = OS_RELEASES_PATH.resolve(filePrefix + i + ".txt").toFile();
			if (i > 0 && !file.exists())
				break;

			assertEquals(expectedOS, OS.determine(OSFamily.LINUX, "linux", "", file),
					"Did not determine the correct distribution from \"" + file + i + "\"!");
		}
	}

	@Test
	public void testDetermineHelper() {
		testDetermineHelper(UNKNOWN, "unknown");
		testDetermineHelper(DEBIAN, "debian");
		testDetermineHelper(UBUNTU, "ubuntu");
		testDetermineHelper(GENTOO, "gentoo");
		testDetermineHelper(LINUX_MINT, "linux_mint");
		testDetermineHelper(RED_HAT_ENTERPRISE_LINUX, "rhel");
		testDetermineHelper(CENTOS, "centos");
		testDetermineHelper(FEDORA, "fedora");
		testDetermineHelper(ARCH_LINUX, "arch");
		testDetermineHelper(SUSE, "suse");

		// Special case file does not exist
		testDetermineHelper(UNKNOWN, "does not exist");

		// Special case file is broken
		testDetermineHelper(UNKNOWN, "broken");
	}

}
