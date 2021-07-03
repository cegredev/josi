package io.github.cegredev.josi.detailed;

import org.junit.jupiter.api.Test;

import static io.github.cegredev.josi.detailed.LinuxOS.Distribution.*;
import static org.junit.jupiter.api.Assertions.*;

public class LinuxOSTests {

	@Test
	public void testParents() {
		var os = new LinuxOS(OSDetermineTests.OS_RELEASES_PATH.resolve("centos0.txt").toFile());
		var msg = "isAtLeast did not return the correct result!";

		assertTrue(os.isAtLeast(FEDORA), msg);
		assertTrue(os.isAtLeast(CENTOS), msg);
		assertFalse(os.isAtLeast(UBUNTU), msg);
	}

}
