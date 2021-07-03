package io.github.cegredev.josi.detailed;

import org.junit.jupiter.api.Test;

import static io.github.cegredev.josi.detailed.WinOS.Release.*;
import static org.junit.jupiter.api.Assertions.*;

public class OSIsAtLeastTests {

	private static final String MSG = "isAtLeast did not return the correct result!";

	@Test
	public void testWin() {
		var os = new WinOS(WIN_7);

		assertTrue(os.isAtLeast(WIN_7), MSG);
		assertTrue(os.isAtLeast(WIN_XP), MSG);
		assertFalse(os.isAtLeast(WIN_10), MSG);
	}

	@Test
	public void testMac() {
		var os = new MacOS(10, 14);

		assertTrue(os.isAtLeast(10, 7), MSG);
		assertTrue(os.isAtLeast(10, 14), MSG);
		assertFalse(os.isAtLeast(10, 15), MSG);

		assertTrue(os.isAtLeast(9), MSG);
		assertTrue(os.isAtLeast(10), MSG);
		assertFalse(os.isAtLeast(11), MSG);
	}

}
